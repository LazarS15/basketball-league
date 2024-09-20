package com.basketball.league.application;

import com.basketball.league.application.config.BeanTestConfiguration;
import com.basketball.league.application.dto.create.command.CreateGameCommand;
import com.basketball.league.application.dto.create.response.CreateGameResponse;
import com.basketball.league.application.fileReader.FileReader;
import com.basketball.league.application.ports.output.repository.GameRepository;
import com.basketball.league.application.ports.output.repository.PlayerRepository;
import com.basketball.league.application.ports.output.repository.PlayerStatsRepository;
import com.basketball.league.application.ports.output.repository.TeamRepository;
import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.PlayerStats;
import com.basketball.league.domain.entity.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BeanTestConfiguration.class)
class CreateGameCommandHandlerTest {
    @Autowired
    private CreateGameCommandHandler createGameCommandHandler;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;


    private CreateGameCommand createGameCommand ;
    private int ROUND = 1;
    private final long HOME_TEAM_ID = 1;
    private final long GUEST_TEAM_ID = 2;

    private List<Team> homeGuestTeamList;
    private List<Player> homeTeamPlayers;
    private List<Player> guestTeamPlayers;

    @BeforeAll
    public void initialization() {
        createGameCommand = CreateGameCommand.builder()
                .round(ROUND)
                .homeTeamId(HOME_TEAM_ID)
                .guestTeamId(GUEST_TEAM_ID)
                .build();

        homeGuestTeamList = FileReader.readTeamsFromFile("src/test/resources/teams-testdata.txt");
        Team homeTeam = homeGuestTeamList.getFirst();
        Team guestTeam = homeGuestTeamList.get(1);

        homeTeamPlayers = FileReader.readPlayersFromFile("src/test/resources/players-testdata.txt",HOME_TEAM_ID);
        guestTeamPlayers = FileReader.readPlayersFromFile("src/test/resources/players-testdata.txt",GUEST_TEAM_ID);

        when(gameRepository.getAll()).thenReturn(new ArrayList<>());
        when(gameRepository.save(any(Game.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(teamRepository.findById(HOME_TEAM_ID)).thenReturn(Optional.of(homeTeam));
        when(teamRepository.findById(GUEST_TEAM_ID)).thenReturn(Optional.of(guestTeam));
        when(playerRepository.getByTeamId(HOME_TEAM_ID)).thenReturn(Optional.of(homeTeamPlayers));
        when(playerRepository.getByTeamId(GUEST_TEAM_ID)).thenReturn(Optional.of(guestTeamPlayers));
        when(playerStatsRepository.save(any(PlayerStats.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testCreateGameCommand_WhenGameDoesntExist() {
        CreateGameResponse createGameResponse = createGameCommandHandler.createGame(createGameCommand);

        assertEquals("Game with id: " + createGameResponse.getGame().getId().getValue() + " is created!",
                createGameResponse.getMessage());
    }
}