package com.basketball.league.domain.entity;

import com.basketball.league.domain.entity.base.BaseEntity;
import com.basketball.league.domain.valueobject.GameId;
import com.basketball.league.domain.valueobject.PlayerId;
import com.basketball.league.domain.valueobject.PlayerStatsId;


public class PlayerStats extends BaseEntity<PlayerStatsId> {
    private final GameId gameId;
    private final PlayerId playerId;
    private int playerPoints;

    private PlayerStats(Builder builder) {
        super.setId(builder.playerStatsId);
        gameId = builder.gameId;
        playerId = builder.playerId;
        playerPoints = builder.playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public static Builder builder() {
        return new Builder();
    }

    public GameId getGameId() {
        return gameId;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public static final class Builder {
        private PlayerStatsId playerStatsId;
        private GameId gameId;
        private PlayerId playerId;
        private int playerPoints;

        private Builder() {
        }


        public Builder id(PlayerStatsId val) {
            playerStatsId = val;
            return this;
        }

        public Builder gameId(GameId val) {
            gameId = val;
            return this;
        }

        public Builder playerId(PlayerId val) {
            playerId = val;
            return this;
        }

        public Builder playerPoints(int val) {
            playerPoints = val;
            return this;
        }

        public PlayerStats build() {
            return new PlayerStats(this);
        }
    }
}
