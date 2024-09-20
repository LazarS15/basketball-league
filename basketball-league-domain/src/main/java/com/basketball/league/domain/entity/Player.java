package com.basketball.league.domain.entity;

import com.basketball.league.domain.entity.base.BaseEntity;
import com.basketball.league.domain.exception.BasketballLeagueDomainException;
import com.basketball.league.domain.valueobject.PlayerId;
import com.basketball.league.domain.valueobject.TeamId;

import java.time.LocalDate;

public class Player extends BaseEntity<PlayerId> {
    private final String firstName;
    private final String lastName;
    private final int jerseyNumber;
    private final TeamId teamId;
    private final String imagePath;

    private final LocalDate birthDate;

    public void validatePlayer() {
        validateBirthDate();
        validateJerseyNumber();
    }

    private void validateBirthDate() {
        LocalDate maxDate = LocalDate.of(1975,1,1);
        LocalDate minDate = LocalDate.of(2010,1,1);
        if (maxDate.isAfter(this.birthDate)) {
            throw new BasketballLeagueDomainException("Players birth date is out of range for this league!");
        } else if (minDate.isBefore(this.birthDate)) {
            throw new BasketballLeagueDomainException("Player is too young for this league!");
        }
    }

    private void validateJerseyNumber() {
        if (this.jerseyNumber >= 100 || this.jerseyNumber < 0) {
            throw new BasketballLeagueDomainException("Jersey number must be in range of 0 to 99!");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    private Player(Builder builder) {
        super.setId(builder.playerId);
        firstName = builder.firstName;
        lastName = builder.lastName;
        jerseyNumber = builder.jerseyNumber;
        teamId = builder.teamId;
        imagePath = builder.imagePath;
        birthDate = builder.birthDate;
    }


    public static final class Builder {
        private PlayerId playerId;
        private String firstName;
        private String lastName;
        private int jerseyNumber;
        private TeamId teamId;
        private String imagePath;
        private LocalDate birthDate;

        private Builder() {
        }

        public Builder id(PlayerId val) {
            playerId = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder jerseyNumber(int val) {
            jerseyNumber = val;
            return this;
        }

        public Builder teamId(TeamId val) {
            teamId = val;
            return this;
        }

        public Builder imagePath(String val) {
            imagePath = val;
            return this;
        }

        public Builder birthDate(LocalDate val) {
            birthDate = val;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
