package com.basketball.league.dataaccess.team.adapter;

import com.basketball.league.application.ports.output.repository.TeamRepository;
import com.basketball.league.dataaccess.team.mapper.TeamDataAccessMapper;
import com.basketball.league.dataaccess.team.repository.TeamJpaRepository;
import com.basketball.league.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamJpaRepository teamJpaRepository;
    private final TeamDataAccessMapper teamDataAccessMapper;

    @Override
    public Optional<Team> findById(Long teamId) {
        return teamJpaRepository.findById(teamId).map(teamDataAccessMapper::teamEntityToTeam);
    }

    @Override
    public List<Team> getAll() {
        return teamJpaRepository.findAll().stream().map(teamDataAccessMapper::teamEntityToTeam).toList();
    }

    @Override
    public Team save(Team team) {
        return teamDataAccessMapper.teamEntityToTeam(teamJpaRepository
                .save(teamDataAccessMapper.teamToTeamEntity(team)));
    }
}
