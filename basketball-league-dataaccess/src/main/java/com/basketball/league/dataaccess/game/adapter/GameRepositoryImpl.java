package com.basketball.league.dataaccess.game.adapter;

import com.basketball.league.application.exception.notFound.GameNotFoundException;
import com.basketball.league.application.ports.output.repository.GameRepository;
import com.basketball.league.dataaccess.game.entity.GameEntity;
import com.basketball.league.dataaccess.game.mapper.GameDataAccessMapper;
import com.basketball.league.dataaccess.game.repository.GameJpaRepository;
import com.basketball.league.domain.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final GameJpaRepository gameJpaRepository;
    private final GameDataAccessMapper gameDataAccessMapper;

    @Override
    public Optional<Game> findById(UUID gameId) {
        return gameJpaRepository.findById(gameId).map(gameDataAccessMapper::gameEntityToGame);
    }

    @Override
    public List<Game> getAll() {
        return gameJpaRepository.findAll().stream().map(gameDataAccessMapper::gameEntityToGame).toList();
    }

    @Override
    public Game save(Game game) {
        return gameDataAccessMapper.gameEntityToGame(gameJpaRepository.save(gameDataAccessMapper.gameToGameEntity(game)));
    }

    @Override
    public Game updateHomeTeamResultAndGuestTeamResultAndIsPlayed(Game game) {
        Optional<GameEntity> gameResult = gameJpaRepository.findById(game.getId().getValue());
        if (gameResult.isPresent()) {
            GameEntity gameEntity = gameResult.get();
            gameEntity.setHomeTeamPoints(game.getHomeTeamPoints());
            gameEntity.setGuestTeamPoints(game.getGuestTeamPoints());
            gameEntity.setPlayed(game.isPlayed());
            return save(gameDataAccessMapper.gameEntityToGame(gameEntity));
        }else {
            throw new GameNotFoundException("Could not find game with id: " + game.getId().getValue());
        }
    }
}
