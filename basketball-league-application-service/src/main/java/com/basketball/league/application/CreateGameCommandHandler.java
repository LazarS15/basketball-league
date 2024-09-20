package com.basketball.league.application;

import com.basketball.league.application.databasecommands.GetEntityCommandHandler;
import com.basketball.league.application.dto.create.command.CreateGameCommand;
import com.basketball.league.application.dto.create.response.CreateGameResponse;
import com.basketball.league.application.exception.alreadyExists.GameAlreadyExistsException;
import com.basketball.league.application.mapper.BasketballLeagueApplicationMapper;
import com.basketball.league.application.ports.output.repository.GameRepository;
import com.basketball.league.application.ports.output.repository.PlayerStatsRepository;
import com.basketball.league.domain.BasketballLeagueDomainService;
import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.PlayerStats;
import com.basketball.league.domain.entity.Team;
import com.basketball.league.domain.valueobject.GameId;
import com.basketball.league.domain.valueobject.PlayerStatsId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateGameCommandHandler {

    private final GetEntityCommandHandler getEntityCommandHandler;
    private final GameRepository gameRepository;
    private final BasketballLeagueApplicationMapper basketballLeagueApplicationMapper;
    private final PlayerStatsRepository playerStatsRepository;
    private final BasketballLeagueDomainService basketballLeagueDomainService;

    @Transactional
    public CreateGameResponse createGame(CreateGameCommand createGameCommand) {
        validateIfGameExists(createGameCommand.getHomeTeamId(), createGameCommand.getGuestTeamId());
        ensureGameReadyTeam(createGameCommand);
        Game game = basketballLeagueApplicationMapper.createGameCommandToGame(createGameCommand);
        basketballLeagueDomainService.validateInitializedGame(game);
        gameRepository.save(game);
        log.info("Game is saved with id: {}", game.getId().getValue());
        List<PlayerStats> playerStats = createPlayerStats(game);
        basketballLeagueDomainService.validateGameResult(game, playerStats);
        return basketballLeagueApplicationMapper.gameToCreateGameResponse(game);
    }
    @Transactional
    private List<PlayerStats> createPlayerStats(Game game) {
        List<Player> homeTeamPlayers = getEntityCommandHandler.
                getPlayersByTeamId(game.getHomeTeamId().getValue());
        List<Player> guestTeamPlayers = getEntityCommandHandler.
                getPlayersByTeamId(game.getGuestTeamId().getValue());

        List<PlayerStats> homeTeamPlayerStats = initializePlayerStats(game.getId(), homeTeamPlayers);
        List<PlayerStats> guestTeamPlayerStats = initializePlayerStats(game.getId(), guestTeamPlayers);

        List<PlayerStats> playerStats = Stream.of(homeTeamPlayerStats, guestTeamPlayerStats)
                .flatMap(List::stream)
                .toList();
        log.info("PlayerStats for is initialized for gameId: {}", game.getId().getValue());
        return playerStats.stream().map(playerStatsRepository::save).toList();
    }
    private List<PlayerStats> initializePlayerStats(GameId gameId, List<Player> players) {
        return players.stream().map(player -> PlayerStats.builder()
                .id(new PlayerStatsId(UUID.randomUUID()))
                .gameId(gameId)
                .playerId(player.getId())
                .playerPoints(0)
                .build()).toList();
    }
    private void ensureGameReadyTeam(CreateGameCommand createGameCommand) {
        Team homeTeam = getEntityCommandHandler.getTeam(createGameCommand.getHomeTeamId());
        Team guestTeam = getEntityCommandHandler.getTeam(createGameCommand.getGuestTeamId());
        List<Player> homeTeamPlayers = getEntityCommandHandler.
                getPlayersByTeamId(createGameCommand.getHomeTeamId());
        List<Player> guestTeamPlayers = getEntityCommandHandler.
                getPlayersByTeamId(createGameCommand.getGuestTeamId());
        basketballLeagueDomainService.validateGameReadyTeam(homeTeam, homeTeamPlayers);
        basketballLeagueDomainService.validateGameReadyTeam(guestTeam, guestTeamPlayers);
    }

    private void validateIfGameExists(Long homeTeamId, Long guestTeamId) {
        List<Game> games = gameRepository.getAll();
        if (games.isEmpty()) {
            return;
        }
        games.forEach(game -> {
            if (game.getHomeTeamId().getValue().equals(homeTeamId) &&
                    game.getGuestTeamId().getValue().equals(guestTeamId)) {
                log.error("Game is already saved in data base with id: {}", game.getId().getValue());
                throw new GameAlreadyExistsException("Game is already saved in data base with id: " +
                        game.getId().getValue() + "!");
            }
        });
    }
}
