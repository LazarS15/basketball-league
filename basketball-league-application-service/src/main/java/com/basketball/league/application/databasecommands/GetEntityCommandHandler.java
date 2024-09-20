package com.basketball.league.application.databasecommands;

import com.basketball.league.application.exception.notFound.*;
import com.basketball.league.application.ports.output.repository.*;
import com.basketball.league.application.ports.output.repository.security.UserRepository;
import com.basketball.league.application.security.user.User;
import com.basketball.league.domain.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetEntityCommandHandler {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TableFieldRepository tableFieldRepository;
    private final PlayerStatsRepository playerStatsRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Team getTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> {
                    log.error("Could not find team with id: {}", teamId);
                    return new TeamNotFoundException("Could not find team with id: " + teamId);
                });
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayersByTeamId(Long teamId) {
        return playerRepository.getByTeamId(teamId)
                .orElseThrow(() -> {
                    log.error("Could not find List of Players using TeamId: {}",teamId);
                    return new PlayerNotFoundException("Could not find List of Players using TeamId: " + teamId);
                });
    }
    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return teamRepository.getAll();
    }

    @Transactional(readOnly = true)
    public PlayerStats getPlayerStatsByGameIdAndPlayerId(UUID gameId, Long playerId) {
        return playerStatsRepository.findByGameIdAndPlayerId(gameId, playerId)
                .orElseThrow(() -> {
                    log.error("Could not find PlayerStats with GameId: {} and PlayerId: {}", gameId, playerId);
                    return new PlayerStatsNotFoundException("Could not find PlayerStats with GameId: " + gameId +
                            " and PlayerId: " + playerId);
                });
    }
    @Transactional(readOnly = true)
    public List<PlayerStats> getPlayerStatsByGameId(UUID gameId) {
        return playerStatsRepository.findByGameId(gameId)
                .orElseThrow(() -> {
                    log.error("Could not find List of PlayerStats with GameId: {}", gameId);
                    return new PlayerStatsNotFoundException("Could not find List of PlayerStats with GameId: " + gameId);
                });
    }

    @Transactional(readOnly = true)
    public Game getGameById(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    log.error("Could not find game with id: {}", gameId);
                    return new GameNotFoundException("Could not find game with id: " + gameId);
                });
    }

    @Transactional(readOnly = true)
    public TableField getTableFieldByTeamId(Long teamId) {
        return tableFieldRepository.findByTeamId(teamId)
                .orElseThrow(() -> {
                    log.error("Can not find tableField with teamId {}", teamId.toString());
                    return new TableFieldNotFoundException("Can not find tableField with teamId " + teamId);
                });
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email)
                .orElseThrow(() -> {
                    log.error("Can not find user with email: {}", email);
                    return new UsernameNotFoundException("Can not find user with email: " + email);
                });
    }
}
