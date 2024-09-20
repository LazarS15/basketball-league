package com.basketball.league.application.ports.output.repository;

import com.basketball.league.domain.entity.PlayerStats;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerStatsRepository {
    PlayerStats save(PlayerStats playerStats);
    Optional<PlayerStats> findByGameIdAndPlayerId(UUID gameId, Long playerId);

    PlayerStats updatePlayerPoints(PlayerStats playerStats);

    Optional<List<PlayerStats>> findByGameId(UUID gameId);
}
