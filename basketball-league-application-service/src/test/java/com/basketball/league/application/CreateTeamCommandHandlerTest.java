package com.basketball.league.application;

import com.basketball.league.application.config.BeanTestConfiguration;
import com.basketball.league.application.dto.create.command.CreateTeamCommand;
import com.basketball.league.application.dto.create.response.CreateTeamResponse;
import com.basketball.league.application.ports.output.repository.TableFieldRepository;
import com.basketball.league.application.ports.output.repository.TeamRepository;
import com.basketball.league.domain.entity.TableField;
import com.basketball.league.domain.entity.Team;
import com.basketball.league.domain.valueobject.TeamId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BeanTestConfiguration.class)
class CreateTeamCommandHandlerTest {
    @Autowired
    private CreateTeamCommandHandler createTeamCommandHandler;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TableFieldRepository tableFieldRepository;

    private final CreateTeamCommand createTeamCommand = CreateTeamCommand.builder()
            .name("Denver Nuggets")
            .imagePath("image/path")
            .logoPath("logo/path")
            .hall("hall1")
            .build();

    @Test
    public void testCreateTeamCommandHandler() {
        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> Team.builder()
                .id(new TeamId(1L))
                .name(createTeamCommand.getName())
                .imagePath(createTeamCommand.getImagePath())
                .logoPath(createTeamCommand.getLogoPath())
                .hall(createTeamCommand.getHall())
                .build());
        when(tableFieldRepository.save(any(TableField.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CreateTeamResponse createTeamResponse = createTeamCommandHandler.createTeam(createTeamCommand);
        assertEquals("Team with id: " + createTeamResponse.getTeam().getId().getValue() + " is created!",
                createTeamResponse.getMessage());
    }

}