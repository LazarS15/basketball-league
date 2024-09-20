package com.basketball.league.application.ports.output.repository;

import com.basketball.league.domain.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Optional<Team> findById(Long teamId);
    List<Team> getAll();
    Team save(Team team);
}
