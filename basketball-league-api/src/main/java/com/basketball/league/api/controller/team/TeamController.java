package com.basketball.league.api.controller.team;

import com.basketball.league.application.dto.create.command.CreateTeamCommand;
import com.basketball.league.application.dto.create.response.CreateTeamResponse;
import com.basketball.league.application.exception.notFound.TeamNotFoundException;
import com.basketball.league.application.ports.input.service.BasketballLeagueApplicationService;
import com.basketball.league.dataaccess.team.entity.TeamEntity;
import com.basketball.league.dataaccess.team.repository.TeamJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping(value = "api/v1/teams")
public class TeamController {
    private final BasketballLeagueApplicationService basketballLeagueApplicationService;
    private final TeamJpaRepository teamJpaRepository;

    public TeamController(BasketballLeagueApplicationService basketballLeagueApplicationService,
                          TeamJpaRepository teamJpaRepository) {
        this.basketballLeagueApplicationService = basketballLeagueApplicationService;
        this.teamJpaRepository = teamJpaRepository;
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateTeamResponse> createTeam(@RequestBody CreateTeamCommand createTeamCommand) {
        log.info("Creating team with name: {}", createTeamCommand.getName());
        CreateTeamResponse createTeamResponse = basketballLeagueApplicationService.createTeam(createTeamCommand);
        log.info("Team created with id: {}", createTeamResponse.getTeam().getId().getValue());
        return ResponseEntity.ok(createTeamResponse);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<TeamEntity> getTeamById(@PathVariable Long id) {
        Optional<TeamEntity> teamEntityOptional = teamJpaRepository.findById(id);
        if (teamEntityOptional.isPresent()) {
            return ResponseEntity.ok(teamEntityOptional.get());
        } else {
            throw new TeamNotFoundException("Team not found with id: " + id);
        }
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<TeamEntity>> getTeams() {
        return ResponseEntity.ok(teamJpaRepository.findAll());
    }
}
