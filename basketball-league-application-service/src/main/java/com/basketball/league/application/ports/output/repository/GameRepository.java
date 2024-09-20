package com.basketball.league.application.ports.output.repository;

import com.basketball.league.domain.entity.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
    Optional<Game> findById(UUID gameId);
    List<Game> getAll();
    Game save(Game game);
    Game updateHomeTeamResultAndGuestTeamResultAndIsPlayed(Game game);
}

