package com.basketball.league.dataaccess.game.repository;

import com.basketball.league.dataaccess.game.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameJpaRepository extends JpaRepository<GameEntity, UUID> {
}
