package com.basketball.league.dataaccess.game.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "games", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"homeTeamId", "guestTeamId"})
})
public class GameEntity {
    @Id
    private UUID id;
    private int round;
    private Long homeTeamId;
    private int homeTeamPoints;
    private int guestTeamPoints;
    private Long guestTeamId;
    private boolean isPlayed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
