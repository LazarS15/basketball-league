package com.basketball.league.application;

import com.basketball.league.application.config.BeanTestConfiguration;
import com.basketball.league.application.dto.create.command.CreatePlayerCommand;
import com.basketball.league.application.dto.create.response.CreatePlayerResponse;
import com.basketball.league.application.fileReader.FileReader;
import com.basketball.league.application.ports.output.repository.PlayerRepository;
import com.basketball.league.application.ports.output.repository.TeamRepository;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.Team;
import com.basketball.league.domain.exception.BasketballLeagueDomainException;
import com.basketball.league.domain.valueobject.PlayerId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BeanTestConfiguration.class)
class CreatePlayerCommandHandlerTest {
    @Autowired
    private CreatePlayerCommandHandler createPlayerCommandHandler;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    private CreatePlayerCommand createPlayerCommandWithBirthdayDateInRange;
    private CreatePlayerCommand createPlayerCommandWithBirthdayDateOutOfRange;
    private List<Team> teams;

    @BeforeAll
    public void initialization() {
        createPlayerCommandWithBirthdayDateInRange = CreatePlayerCommand.builder()
                .firstName("Jack")
                .lastName("Daniels")
                .birthDate("2000-04-03")
                .imagePath("image/path")
                .jerseyNumber(22)
                .teamName("Los Angeles Lakers")
                .build();

        createPlayerCommandWithBirthdayDateOutOfRange = CreatePlayerCommand.builder()
                .firstName("Jack")
                .lastName("Daniels")
                .birthDate("2012-04-03")
                .imagePath("image/path")
                .jerseyNumber(22)
                .teamName("Los Angeles Lakers")
                .build();

        teams = FileReader.readTeamsFromFile("src/test/resources/teams-testdata.txt");
        when(teamRepository.getAll()).thenReturn(teams);
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testCreatePlayerCommandHandler_WhenTeamExists() {
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> {
            Player player = invocation.getArgument(0);
            return Player.builder()
                    .id(new PlayerId(1L))
                    .firstName(createPlayerCommandWithBirthdayDateInRange.getFirstName())
                    .lastName(createPlayerCommandWithBirthdayDateInRange.getLastName())
                    .birthDate(LocalDate.parse(createPlayerCommandWithBirthdayDateOutOfRange.getBirthDate()))
                    .imagePath(createPlayerCommandWithBirthdayDateInRange.getImagePath())
                    .jerseyNumber(createPlayerCommandWithBirthdayDateInRange.getJerseyNumber())
                    .teamId(player.getTeamId())
                    .build();});

        CreatePlayerResponse createPlayerResponse = createPlayerCommandHandler
                .createPlayer(createPlayerCommandWithBirthdayDateInRange);
        assertEquals("Player with id: " + createPlayerResponse.getPlayer().getId().getValue() + " is created!",
                createPlayerResponse.getMessage());
    }

    @Test
    public void testCreatePlayerCommandHandler_WhenSecondPlayerHasSameFirstNameLastNameAndBirthdayDate() {
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> {
            Player player = invocation.getArgument(0);
            return Player.builder()
                    .id(new PlayerId(1L))
                    .firstName(createPlayerCommandWithBirthdayDateOutOfRange.getFirstName())
                    .lastName(createPlayerCommandWithBirthdayDateOutOfRange.getLastName())
                    .birthDate(LocalDate.parse(createPlayerCommandWithBirthdayDateOutOfRange.getBirthDate()))
                    .imagePath(createPlayerCommandWithBirthdayDateOutOfRange.getImagePath())
                    .jerseyNumber(createPlayerCommandWithBirthdayDateOutOfRange.getJerseyNumber())
                    .teamId(player.getTeamId())
                    .build();});
        BasketballLeagueDomainException exception = assertThrows(BasketballLeagueDomainException.class,
                () -> createPlayerCommandHandler.createPlayer(createPlayerCommandWithBirthdayDateOutOfRange));

        assertEquals("Player is too young for this league!", exception.getMessage());
    }
}