package com.basketball.league.api.controller.player;

import com.basketball.league.application.dto.create.command.CreatePlayerCommand;
import com.basketball.league.application.dto.create.response.CreatePlayerResponse;
import com.basketball.league.application.exception.notFound.PlayerNotFoundException;
import com.basketball.league.application.exception.notFound.PlayerStatsNotFoundException;
import com.basketball.league.application.ports.input.service.BasketballLeagueApplicationService;
import com.basketball.league.dataaccess.player.entity.PlayerEntity;
import com.basketball.league.dataaccess.player.repository.PlayerJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/players")
public class PlayerController {
    private final BasketballLeagueApplicationService basketballLeagueApplicationService;
    private final PlayerJpaRepository playerJpaRepository;

    public PlayerController(BasketballLeagueApplicationService basketballLeagueApplicationService,
                            PlayerJpaRepository playerJpaRepository) {
        this.basketballLeagueApplicationService = basketballLeagueApplicationService;
        this.playerJpaRepository = playerJpaRepository;
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreatePlayerResponse> createPlayer(@RequestBody CreatePlayerCommand createPlayerCommand) {
        log.info("Creating player with firstName: {} and lastName: {}", createPlayerCommand.getFirstName(),
                createPlayerCommand.getLastName());
        CreatePlayerResponse createPlayerResponse = basketballLeagueApplicationService.createPlayer(createPlayerCommand);
        log.info("Player created with id: {}", createPlayerResponse.getPlayer().getId().getValue());
        return ResponseEntity.ok(createPlayerResponse);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<PlayerEntity> getById(@PathVariable Long id) {
        Optional<PlayerEntity> playerOptional = playerJpaRepository.findById(id);
        if (playerOptional.isPresent()) {
            PlayerEntity playerEntity = playerOptional.get();
            return ResponseEntity.ok(playerEntity);
        } else {
            throw new PlayerStatsNotFoundException("Player is not found with id: " + id);
        }
    }
    @GetMapping("/team/{teamId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<PlayerEntity>> getAllByTeamId(@PathVariable Long teamId) {
        Optional<List<PlayerEntity>> playerEntityList = playerJpaRepository.findByTeamId(teamId);
        if (!playerEntityList.get().isEmpty()) {
            return ResponseEntity.ok(playerEntityList.get());
        } else {
            throw new PlayerNotFoundException("Team with id: " + teamId + " has no players or doesn't exist");
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<PlayerEntity>> getAll() {
        return ResponseEntity.ok(playerJpaRepository.findAll());
    }
}
