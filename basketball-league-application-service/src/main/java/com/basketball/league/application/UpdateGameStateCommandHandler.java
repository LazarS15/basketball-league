package com.basketball.league.application;

import com.basketball.league.application.databasecommands.GetEntityCommandHandler;
import com.basketball.league.application.dto.create.response.GameStateChangerResponse;
import com.basketball.league.application.mapper.BasketballLeagueApplicationMapper;
import com.basketball.league.application.ports.output.repository.GameRepository;
import com.basketball.league.application.ports.output.repository.PlayerStatsRepository;
import com.basketball.league.domain.BasketballLeagueDomainService;
import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.PlayerStats;
import com.basketball.league.domain.valueobject.GameId;
import com.basketball.league.domain.valueobject.PlayerId;
import com.basketball.league.domain.valueobject.PlayerStatsId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateGameStateCommandHandler {

    private final GetEntityCommandHandler getEntityCommandHandler;
    private final PlayerStatsRepository playerStatsRepository;
    private final GameRepository gameRepository;
    private final TableOrderingCommandHandler tableOrderingCommandHandler;
    private final BasketballLeagueApplicationMapper basketballLeagueApplicationMapper;
    private final BasketballLeagueDomainService basketballLeagueDomainService;

    public static final int ZERO_POINTS = 0;
    private static final int MAX_PLAYER_POINTS = 25;

    @Transactional
    public GameStateChangerResponse updateGameState(UUID gameId) {
        Game gameResult = getEntityCommandHandler.getGameById(gameId);
        initializePlayerStatsIfNotExists(gameResult);
        if (gameResult.isPlayed()) {
            tableOrderingCommandHandler.updateTableDetails(gameResult, true);
            Game game = resetGameResult(gameResult);
            log.info("Game with game id: {}", game.getId().getValue() + "is restarted!");
            return basketballLeagueApplicationMapper.gameToGameStateChangerResponse(game);
        }
        List<PlayerStats> homeTeamPlayersStats = setPlayerStats(gameResult.getHomeTeamId().getValue(),
                gameResult.getId().getValue());
        List<PlayerStats> guestTeamPlayersStats = setPlayerStats(gameResult.getGuestTeamId().getValue(),
                gameResult.getId().getValue());
        Game game = updateGameDetails(gameResult, homeTeamPlayersStats, guestTeamPlayersStats);
        log.info("Game with game id: {} is played with result: {}:{}!", game.getId().getValue(),
                game.getHomeTeamPoints(), game.getGuestTeamPoints());
        tableOrderingCommandHandler.updateTableDetails(game, false);
        return basketballLeagueApplicationMapper.gameToGameStateChangerResponse(game);
    }

    @Transactional
    private Game updateGameDetails(Game game, List<PlayerStats> homeTeamPlayerStats, List<PlayerStats> guestTeamPlayersStats) {
        boolean isPlayed = true;
        int homeTeamResult = calculateTeamResult(homeTeamPlayerStats);
        int guestTeamResult = calculateTeamResult(guestTeamPlayersStats);
        List<PlayerStats> gamesPlayerStats = Stream.of(homeTeamPlayerStats, guestTeamPlayersStats)
                .flatMap(List::stream).toList();
        game.changeResultAndSetIsPlayed(homeTeamResult, guestTeamResult, isPlayed);
        basketballLeagueDomainService.validateGameResult(game, gamesPlayerStats);
        return gameRepository.updateHomeTeamResultAndGuestTeamResultAndIsPlayed(game);
    }
    @Transactional
    private Game resetGameResult(Game game) {
        boolean isPlayed = false;
        List<PlayerStats> playerStats = getEntityCommandHandler.
                getPlayerStatsByGameId(game.getId().getValue());
        playerStats.forEach(playerStat -> playerStat.setPlayerPoints(0));
        List<PlayerStats> updatedPlayerStats =
                playerStats.stream().map(playerStatsRepository::updatePlayerPoints).toList();
        game.changeResultAndSetIsPlayed(ZERO_POINTS, ZERO_POINTS, isPlayed);
        basketballLeagueDomainService.validateGameResult(game, updatedPlayerStats);
        return gameRepository.updateHomeTeamResultAndGuestTeamResultAndIsPlayed(game);
    }
    private List<PlayerStats> setPlayerStats(Long teamId, UUID gameId) {
        Random random = new Random();
        List<Player> players = getEntityCommandHandler.getPlayersByTeamId(teamId);
        List<PlayerStats> playerStats = players.stream().map(player ->
                getEntityCommandHandler.getPlayerStatsByGameIdAndPlayerId(gameId, player.getId().getValue()))
                .toList();
        playerStats.forEach(playerStat -> playerStat.setPlayerPoints(random.nextInt(MAX_PLAYER_POINTS)));
        return playerStats.stream().map(playerStatsRepository::updatePlayerPoints).toList();
    }

    private int calculateTeamResult(List<PlayerStats> playerStatsList) {
        return playerStatsList.stream().mapToInt(PlayerStats::getPlayerPoints).sum();
    }

    private void initializePlayerStatsIfNotExists(Game game) {
        List<Player> homeTeamPlayers = getEntityCommandHandler.getPlayersByTeamId(game.getHomeTeamId().getValue());
        List<Player> guestTeamPlayers = getEntityCommandHandler.getPlayersByTeamId(game.getGuestTeamId().getValue());
        List<Long> playerIds = new ArrayList<>();
        homeTeamPlayers.forEach(player -> playerIds.add(player.getId().getValue()));
        guestTeamPlayers.forEach(player -> playerIds.add(player.getId().getValue()));

        playerIds.forEach(playerId -> {
            if (playerStatsRepository.findByGameIdAndPlayerId(game.getId().getValue(), playerId).isEmpty()) {
                PlayerStats playerStats = buildPlayerStats(game.getId().getValue(), playerId);
                playerStatsRepository.save(playerStats);
                log.info("Created new PlayerStats with id: {}", playerStats.getId().getValue());
            }
        });
    }

    private PlayerStats buildPlayerStats(UUID gameId, Long playerId) {
        return PlayerStats.builder()
                .id(new PlayerStatsId(UUID.randomUUID()))
                .gameId(new GameId(gameId))
                .playerId(new PlayerId(playerId))
                .playerPoints(0)
                .build();
    }
}
