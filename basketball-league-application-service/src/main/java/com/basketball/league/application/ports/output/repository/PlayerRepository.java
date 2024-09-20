package com.basketball.league.application.ports.output.repository;

import com.basketball.league.domain.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    List<Player> getAll();
    Optional<List<Player>> getByTeamId(Long teamId);
    Player save(Player player);
}
