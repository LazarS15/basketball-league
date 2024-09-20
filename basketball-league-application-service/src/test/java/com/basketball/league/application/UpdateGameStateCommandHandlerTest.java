package com.basketball.league.application;

import com.basketball.league.application.config.BeanTestConfiguration;
import com.basketball.league.application.dto.create.response.GameStateChangerResponse;
import com.basketball.league.application.fileReader.FileReader;
import com.basketball.league.application.ports.output.repository.*;
import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.PlayerStats;
import com.basketball.league.domain.entity.TableField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BeanTestConfiguration.class)
class UpdateGameStateCommandHandlerTest {
    @Autowired
    private UpdateGameStateCommandHandler updateGameStateCommandHandler;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
    @Autowired
    private TableFieldRepository tableFieldRepository;

    private final UUID GAME_ID = UUID.fromString("ba10e89a-4491-4cbc-8acb-8b8b985b38ac");
    private final Long HOME_TEAM_ID = 1L;
    private final Long GUEST_TEAM_ID = 2L;

    private Game unplayedGame;
    private Game playedGame;
    private List<PlayerStats> playerStatsWithPlayerPointsGreaterThanZero;
    private List<PlayerStats> playerStatsWithPlayerPointsEqualToZero;
    private List<Player> homeTeamPlayers;
    private List<Player> guestTeamPlayers;
    private TableField homeTeamTableField;
    private TableField guestTeamTableField;

    @BeforeAll
    public void initialization() {
        unplayedGame = FileReader.readGameFromFile("src/test/resources/game-testdata.txt", false);
        playedGame = FileReader.readGameFromFile("src/test/resources/game-testdata.txt", true);

        homeTeamPlayers = FileReader.readPlayersFromFile("src/test/resources/players-testdata.txt",
                HOME_TEAM_ID);
        guestTeamPlayers = FileReader.readPlayersFromFile("src/test/resources/players-testdata.txt",
                GUEST_TEAM_ID);
        
        playerStatsWithPlayerPointsGreaterThanZero = FileReader
                .readPlayerStatsFromFile("src/test/resources/playerStats-testdata.txt",
                        true);
        playerStatsWithPlayerPointsEqualToZero =
                FileReader.readPlayerStatsFromFile("src/test/resources/playerStats-testdata.txt",
                        false);

        when(playerRepository.getByTeamId(HOME_TEAM_ID)).thenReturn(Optional.of(homeTeamPlayers));
        when(playerRepository.getByTeamId(GUEST_TEAM_ID)).thenReturn(Optional.of(guestTeamPlayers));
    }

    @Test
    public void testUpdateGameStateAndTableFields_WhenGameIsPlayed() {
        homeTeamTableField = FileReader
                .readTableFieldFromFile("src/test/resources/tableField-forPlayedGame-testdata.txt",
                        HOME_TEAM_ID);
        guestTeamTableField = FileReader
                .readTableFieldFromFile("src/test/resources/tableField-forPlayedGame-testdata.txt",
                        GUEST_TEAM_ID);
        when(tableFieldRepository.findByTeamId(HOME_TEAM_ID)).thenReturn(Optional.of(homeTeamTableField));
        when(tableFieldRepository.findByTeamId(GUEST_TEAM_ID)).thenReturn(Optional.of(guestTeamTableField));

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(playedGame));
        when(gameRepository.updateHomeTeamResultAndGuestTeamResultAndIsPlayed(playedGame)).thenReturn(playedGame);

        when(playerStatsRepository.findByGameIdAndPlayerId(eq(GAME_ID), anyLong()))
                .thenAnswer(invocation -> {
                    Long playerId = invocation.getArgument(1);
                    return Optional.of(playerStatsWithPlayerPointsGreaterThanZero.stream()
                            .filter(stats -> stats.getPlayerId().getValue().equals(playerId)));
                });
        when(playerStatsRepository.findByGameId(GAME_ID))
                .thenReturn(Optional.of(playerStatsWithPlayerPointsGreaterThanZero));
        when(playerStatsRepository.findByGameId(GAME_ID))
                .thenReturn(Optional.of(playerStatsWithPlayerPointsEqualToZero));
        when(playerStatsRepository.updatePlayerPoints(any(PlayerStats.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        GameStateChangerResponse gameStateChangerResponse = updateGameStateCommandHandler.updateGameState(GAME_ID);

        assertEquals("Game with id: " + GAME_ID + " is updated", gameStateChangerResponse.getMessage());
        assertEquals(0, gameStateChangerResponse.getGame().getHomeTeamPoints());
        assertEquals(0, gameStateChangerResponse.getGame().getGuestTeamPoints());
        assertFalse(gameStateChangerResponse.getGame().isPlayed());

        assertEquals(0, homeTeamTableField.getPlusMinus() +
                homeTeamTableField.getPoints() +
                homeTeamTableField.getLosses());
        assertEquals(0, guestTeamTableField.getPlusMinus() +
                guestTeamTableField.getPoints() +
                guestTeamTableField.getLosses());
    }

    @Test
    public void testUpdateGameStateAndTableFields_WhenGameIsNotPlayed() {
        homeTeamTableField = FileReader
                .readTableFieldFromFile("src/test/resources/tableField-forNotPlayedGame-testdata.txt",
                        HOME_TEAM_ID);
        guestTeamTableField = FileReader
                .readTableFieldFromFile("src/test/resources/tableField-forNotPlayedGame-testdata.txt",
                        GUEST_TEAM_ID);
        when(tableFieldRepository.findByTeamId(HOME_TEAM_ID)).thenReturn(Optional.of(homeTeamTableField));
        when(tableFieldRepository.findByTeamId(GUEST_TEAM_ID)).thenReturn(Optional.of(guestTeamTableField));
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(unplayedGame));
        when(gameRepository.updateHomeTeamResultAndGuestTeamResultAndIsPlayed(unplayedGame)).thenReturn(unplayedGame);

        when(playerStatsRepository.findByGameIdAndPlayerId(eq(GAME_ID), anyLong()))
                .thenAnswer(invocation -> {
                    Long playerId = invocation.getArgument(1);
                    return Optional.of(playerStatsWithPlayerPointsGreaterThanZero.stream()
                                    .filter(stats -> stats.getPlayerId().getValue().equals(playerId)))
                            .flatMap(Stream::findFirst);
                });
        when(playerStatsRepository.findByGameId(GAME_ID))
                .thenReturn(Optional.of(playerStatsWithPlayerPointsEqualToZero));
        when(playerStatsRepository.updatePlayerPoints(any(PlayerStats.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        GameStateChangerResponse gameStateChangerResponse = updateGameStateCommandHandler.updateGameState(GAME_ID);

        assertEquals("Game with id: " + GAME_ID + " is updated", gameStateChangerResponse.getMessage());
        assertTrue(gameStateChangerResponse.getGame().getHomeTeamPoints() > 0);
        assertTrue(gameStateChangerResponse.getGame().getGuestTeamPoints() > 0);
        assertTrue(gameStateChangerResponse.getGame().isPlayed());

        assertTrue(homeTeamTableField.getPlusMinus() > 0 || guestTeamTableField.getPlusMinus() > 0);
    }
}