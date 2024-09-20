package com.basketball.league.domain.entity;

import com.basketball.league.domain.entity.base.BaseEntity;
import com.basketball.league.domain.valueobject.TableFieldId;
import com.basketball.league.domain.valueobject.TeamId;

public class TableField extends BaseEntity<TableFieldId> {
    private final TeamId teamId;
    private int wins;
    private int losses;
    private int plusMinus;
    private int points;

    private TableField(Builder builder) {
        super.setId(builder.tableFieldId);
        teamId = builder.teamId;
        wins = builder.wins;
        losses = builder.losses;
        plusMinus = builder.plusMinus;
        points = builder.points;
    }

    public void setTableFieldDetails(int wins, int loses, int plusMinus, int points) {
        this.wins = wins;
        this.losses = loses;
        this.plusMinus = plusMinus;
        this.points = points;
    }

    public static Builder builder() {
        return new Builder();
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getPlusMinus() {
        return plusMinus;
    }

    public int getPoints() {
        return points;
    }

    public static final class Builder {
        private TeamId teamId;
        private int wins;
        private int losses;
        private int plusMinus;
        private int points;
        private TableFieldId tableFieldId;

        private Builder() {
        }
        public Builder id(TableFieldId val) {
            tableFieldId = val;
            return this;
        }

        public Builder teamId(TeamId val) {
            teamId = val;
            return this;
        }

        public Builder wins(int val) {
            wins = val;
            return this;
        }

        public Builder losses(int val) {
            losses = val;
            return this;
        }

        public Builder plusMinus(int val) {
            plusMinus = val;
            return this;
        }

        public Builder points(int val) {
            points = val;
            return this;
        }

        public TableField build() {
            return new TableField(this);
        }
    }
}
