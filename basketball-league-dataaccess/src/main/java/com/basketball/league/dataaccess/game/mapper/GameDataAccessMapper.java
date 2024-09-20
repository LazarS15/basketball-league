package com.basketball.league.dataaccess.game.mapper;

import com.basketball.league.dataaccess.game.entity.GameEntity;
import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.valueobject.GameId;
import com.basketball.league.domain.valueobject.TeamId;
import org.springframework.stereotype.Component;

@Component
public class GameDataAccessMapper {
    public Game gameEntityToGame(GameEntity gameEntity) {
        return Game.builder()
                .id(new GameId(gameEntity.getId()))
                .round(gameEntity.getRound())
                .homeTeamId(new TeamId(gameEntity.getHomeTeamId()))
                .homeTeamPoints(gameEntity.getHomeTeamPoints())
                .guestTeamPoints(gameEntity.getGuestTeamPoints())
                .guestTeamId(new TeamId(gameEntity.getGuestTeamId()))
                .isPlayed(gameEntity.isPlayed())
                .build();
    }

    public GameEntity gameToGameEntity(Game game) {
        return GameEntity.builder()
                .id(game.getId().getValue())
                .round(game.getRound())
                .homeTeamId(game.getHomeTeamId().getValue())
                .homeTeamPoints(game.getHomeTeamPoints())
                .guestTeamPoints(game.getGuestTeamPoints())
                .guestTeamId(game.getGuestTeamId().getValue())
                .isPlayed(game.isPlayed())
                .build();
    }
}
