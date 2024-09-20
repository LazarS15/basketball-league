package com.basketball.league.api.controller.playerStats;

import com.basketball.league.application.exception.notFound.PlayerStatsNotFoundException;
import com.basketball.league.dataaccess.playerStats.entity.PlayerStatsEntity;
import com.basketball.league.dataaccess.playerStats.repository.PlayerStatsJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/playerstats")
public class PlayerStatsController {
    private final PlayerStatsJpaRepository playerStatsJpaRepository;

    public PlayerStatsController(PlayerStatsJpaRepository playerStatsJpaRepository) {
        this.playerStatsJpaRepository = playerStatsJpaRepository;
    }

    @GetMapping("/gameId/{gameId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<PlayerStatsEntity>> getByGameId(@PathVariable UUID gameId) {
        Optional<List<PlayerStatsEntity>> playerStatsEntities = playerStatsJpaRepository.findByGameId(gameId);
        if (!playerStatsEntities.get().isEmpty()) {
            return ResponseEntity.ok(playerStatsEntities.get());
        } else {
            throw new PlayerStatsNotFoundException("PlayerStats not found with playerId: " + gameId);
        }
    }

    @GetMapping("/playerId/{playerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<PlayerStatsEntity>> getByPlayerId(@PathVariable Long playerId) {
        Optional<List<PlayerStatsEntity>> playerStatsEntities = playerStatsJpaRepository.findByPlayerId(playerId);
        if (!playerStatsEntities.get().isEmpty()) {
            return ResponseEntity.ok(playerStatsEntities.get());
        } else {
            throw new PlayerStatsNotFoundException("PlayerStats not found with playerId: " + playerId);
        }
    }
}
