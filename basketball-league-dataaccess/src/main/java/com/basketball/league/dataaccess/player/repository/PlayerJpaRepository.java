package com.basketball.league.dataaccess.player.repository;

import com.basketball.league.dataaccess.player.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<List<PlayerEntity>> findByTeamId(Long teamId);
}
