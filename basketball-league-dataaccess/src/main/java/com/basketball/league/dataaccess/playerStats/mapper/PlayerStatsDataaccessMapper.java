package com.basketball.league.dataaccess.playerStats.mapper;

import com.basketball.league.dataaccess.playerStats.entity.PlayerStatsEntity;
import com.basketball.league.domain.entity.PlayerStats;
import com.basketball.league.domain.valueobject.GameId;
import com.basketball.league.domain.valueobject.PlayerId;
import com.basketball.league.domain.valueobject.PlayerStatsId;
import org.springframework.stereotype.Component;

@Component
public class PlayerStatsDataaccessMapper {
    public PlayerStats playerStatsEntityToPlayerStats(PlayerStatsEntity playerStatsEntity) {
        return PlayerStats.builder()
                .id(new PlayerStatsId(playerStatsEntity.getId()))
                .gameId(new GameId(playerStatsEntity.getGameId()))
                .playerId(new PlayerId(playerStatsEntity.getPlayerId()))
                .playerPoints(playerStatsEntity.getPlayerPoints())
                .build();
    }
    public PlayerStatsEntity playerStatsToPlayerStatsEntity(PlayerStats playerStats) {
        return PlayerStatsEntity.builder()
                .id(playerStats.getId().getValue())
                .gameId(playerStats.getGameId().getValue())
                .playerId(playerStats.getPlayerId().getValue())
                .playerPoints(playerStats.getPlayerPoints())
                .build();
    }
}
