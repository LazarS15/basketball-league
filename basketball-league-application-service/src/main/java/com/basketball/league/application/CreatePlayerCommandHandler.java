package com.basketball.league.application;

import com.basketball.league.application.databasecommands.GetEntityCommandHandler;
import com.basketball.league.application.dto.create.command.CreatePlayerCommand;
import com.basketball.league.application.dto.create.response.CreatePlayerResponse;
import com.basketball.league.application.exception.notFound.TeamNotFoundException;
import com.basketball.league.application.mapper.BasketballLeagueApplicationMapper;
import com.basketball.league.application.ports.output.repository.PlayerRepository;
import com.basketball.league.domain.BasketballLeagueDomainService;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePlayerCommandHandler {

    private final GetEntityCommandHandler getEntityCommandHandler;
    private final BasketballLeagueApplicationMapper basketballLeagueApplicationMapper;
    private final PlayerRepository playerRepository;
    private final BasketballLeagueDomainService basketballLeagueDomainService;


    @Transactional
    public CreatePlayerResponse createPlayer(CreatePlayerCommand createPlayerCommand) {
        List<Team> teams = getEntityCommandHandler.getAllTeams();
        Team team = validateTeam(createPlayerCommand, teams);
        Player player = basketballLeagueApplicationMapper.createPlayerCommandToPlayer(createPlayerCommand, team);
        basketballLeagueDomainService.validatePlayer(player);
        Player savedPlayer = playerRepository.save(player);
        log.info("Player with id: {} is saved", savedPlayer.getId().getValue());
        return basketballLeagueApplicationMapper.playerToCreatePlayerResponse(savedPlayer);
    }
    private Team validateTeam(CreatePlayerCommand createPlayerCommand, List<Team> teams) {
        return teams.stream()
                .filter(team -> team.getName().equals(createPlayerCommand.getTeamName()))
                .findFirst()
                .orElseThrow(() ->
                        new TeamNotFoundException("Team not found with name: " + createPlayerCommand.getTeamName()));
    }
}

