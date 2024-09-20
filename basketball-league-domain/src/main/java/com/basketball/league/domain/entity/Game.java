package com.basketball.league.domain.entity;

import com.basketball.league.domain.entity.base.AgregateRoot;
import com.basketball.league.domain.exception.BasketballLeagueDomainException;
import com.basketball.league.domain.valueobject.GameId;
import com.basketball.league.domain.valueobject.TeamId;

import java.util.List;

public class Game extends AgregateRoot<GameId> {
    private final int round;
    private final TeamId homeTeamId;
    private int homeTeamPoints;
    private int guestTeamPoints;
    private final TeamId guestTeamId;
    private boolean isPlayed;

    public void changeResultAndSetIsPlayed(int homeTeamPoints, int guestTeamPoints, boolean isPlayed) {
        this.homeTeamPoints = homeTeamPoints;
        this.guestTeamPoints = guestTeamPoints;
        this.isPlayed = isPlayed;
    }
    public void validateInitializedGame() {
        if (homeTeamId.getValue().equals(guestTeamId.getValue())) {
            throw new BasketballLeagueDomainException("Team can not play versus itself!");
        }
    }

    public void validateResult(List<PlayerStats> playerStats) {
        int allPointsTogether = 0;
        for (PlayerStats playerStat : playerStats) {
            allPointsTogether += playerStat.getPlayerPoints();
        }

        if (allPointsTogether != (homeTeamPoints + guestTeamPoints)) {
            throw new BasketballLeagueDomainException("The result of this game is not valid!");
        }

        if (homeTeamPoints == guestTeamPoints && isPlayed) {
            throw new BasketballLeagueDomainException("Result can not be draw!");
        }
    }

    private Game(Builder builder) {
        super.setId(builder.gameId);
        round = builder.round;
        homeTeamId = builder.homeTeamId;
        homeTeamPoints = builder.homeTeamPoints;
        guestTeamPoints = builder.guestTeamPoints;
        guestTeamId = builder.guestTeamId;
        isPlayed = builder.isPlayed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getRound() {
        return round;
    }

    public TeamId getHomeTeamId() {
        return homeTeamId;
    }

    public int getHomeTeamPoints() {
        return homeTeamPoints;
    }

    public int getGuestTeamPoints() {
        return guestTeamPoints;
    }

    public TeamId getGuestTeamId() {
        return guestTeamId;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public static final class Builder {
        private GameId gameId;
        private int round;
        private TeamId homeTeamId;
        private int homeTeamPoints;
        private int guestTeamPoints;
        private TeamId guestTeamId;
        private boolean isPlayed;

        private Builder() {
        }

        public Builder id(GameId val) {
            gameId = val;
            return this;
        }

        public Builder round(int val) {
            round = val;
            return this;
        }

        public Builder homeTeamId(TeamId val) {
            homeTeamId = val;
            return this;
        }

        public Builder homeTeamPoints(int val) {
            homeTeamPoints = val;
            return this;
        }

        public Builder guestTeamPoints(int val) {
            guestTeamPoints = val;
            return this;
        }

        public Builder guestTeamId(TeamId val) {
            guestTeamId = val;
            return this;
        }

        public Builder isPlayed(boolean val) {
            isPlayed = val;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
