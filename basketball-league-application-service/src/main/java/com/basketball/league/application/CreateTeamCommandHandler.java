package com.basketball.league.application;

import com.basketball.league.application.dto.create.command.CreateTeamCommand;
import com.basketball.league.application.dto.create.response.CreateTeamResponse;
import com.basketball.league.application.mapper.BasketballLeagueApplicationMapper;
import com.basketball.league.application.ports.output.repository.TeamRepository;
import com.basketball.league.domain.entity.TableField;
import com.basketball.league.domain.entity.Team;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class CreateTeamCommandHandler {

    private final TableOrderingCommandHandler tableOrderingCommandHandler;
    private final BasketballLeagueApplicationMapper basketballLeagueApplicationMapper;
    private final TeamRepository teamRepository;

    @Transactional
    public CreateTeamResponse createTeam(CreateTeamCommand createTeamCommand) {
        Team team = basketballLeagueApplicationMapper.createTeamCommandToTeam(createTeamCommand);
        Team savedTeam = teamRepository.save(team);
        TableField tableField = tableOrderingCommandHandler.initializeTableField(savedTeam.getId());
        log.info("TableField initialized with id: {}", tableField.getId().getValue());
        return basketballLeagueApplicationMapper.teamToCreateTeamResponse(savedTeam);
    }
}
