package com.basketball.league.domain.entity;

import com.basketball.league.domain.entity.base.BaseEntity;
import com.basketball.league.domain.exception.BasketballLeagueDomainException;
import com.basketball.league.domain.valueobject.TeamId;

import java.util.List;

public class Team extends BaseEntity<TeamId> {
    private final String name;
    private final String imagePath;
    private final String logoPath;
    private final String hall;
    private Team(Builder builder) {
        super.setId(builder.teamId);
        name = builder.name;
        imagePath = builder.imagePath;
        logoPath = builder.logoPath;
        hall = builder.hall;
    }
    public void validateTeam(List<Player> players) {
        if (players.size() < 7) {
            throw new BasketballLeagueDomainException("Team with id " + this.getId().getValue() +
                    " does not have enough players to play the games!");
        }
    }
    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getHall() {
        return hall;
    }

    public static final class Builder {
        private TeamId teamId;
        private String name;
        private String imagePath;
        private String logoPath;
        private String hall;

        private Builder() {
        }

        public Builder id(TeamId val) {
            teamId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder imagePath(String val) {
            imagePath = val;
            return this;
        }

        public Builder logoPath(String val) {
            logoPath = val;
            return this;
        }

        public Builder hall(String val) {
            hall = val;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }
}
