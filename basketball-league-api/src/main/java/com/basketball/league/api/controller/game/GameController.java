package com.basketball.league.api.controller.game;

import com.basketball.league.application.dto.create.command.CreateGameCommand;
import com.basketball.league.application.dto.create.response.CreateGameResponse;
import com.basketball.league.application.dto.create.response.GameStateChangerResponse;
import com.basketball.league.application.exception.notFound.GameNotFoundException;
import com.basketball.league.application.ports.input.service.BasketballLeagueApplicationService;
import com.basketball.league.dataaccess.game.entity.GameEntity;
import com.basketball.league.dataaccess.game.repository.GameJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final BasketballLeagueApplicationService basketballLeagueApplicationService;
    private final GameJpaRepository gameJpaRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody CreateGameCommand createGameCommand) {
        log.info("Creating game with homeTeam id: {} and guestTeam id: {}", createGameCommand.getHomeTeamId(),
                createGameCommand.getGuestTeamId());
        CreateGameResponse createGameResponse = basketballLeagueApplicationService.createGame(createGameCommand);
        log.info("Game created with id: {}", createGameResponse.getGame().getId().getValue());
        return ResponseEntity.ok(createGameResponse);
    }

    @PutMapping("/{gameId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GameStateChangerResponse> updateGame(@PathVariable UUID gameId) {
        log.info("Updating game with id: {}", gameId);
        GameStateChangerResponse gameStateChangerResponse = basketballLeagueApplicationService
                .updateGameState(gameId);
        log.info("Game with id: {} is updated!", gameStateChangerResponse.getGame().getId().getValue());
        return ResponseEntity.ok(gameStateChangerResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<GameEntity> getById(@PathVariable UUID id) {
        Optional<GameEntity> gameEntityOptional = gameJpaRepository.findById(id);
        if (gameEntityOptional.isPresent()) {
            GameEntity gameEntity = gameEntityOptional.get();
            return ResponseEntity.ok(gameEntity);
        } else {
            throw new GameNotFoundException("Game not found with ID: " + id);
        }
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<GameEntity>> getAll() {
        return ResponseEntity.ok(gameJpaRepository.findAll());
    }
}
