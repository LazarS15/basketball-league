package com.basketball.league.dataaccess.playerStats.repository;

import com.basketball.league.dataaccess.playerStats.entity.PlayerStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerStatsJpaRepository extends JpaRepository<PlayerStatsEntity, UUID> {
    Optional<PlayerStatsEntity> findByGameIdAndPlayerId(UUID gameId, Long playerId);
    Optional<List<PlayerStatsEntity>> findByPlayerId (Long playerId);
    Optional<List<PlayerStatsEntity>> findByGameId(UUID gameId);
}
