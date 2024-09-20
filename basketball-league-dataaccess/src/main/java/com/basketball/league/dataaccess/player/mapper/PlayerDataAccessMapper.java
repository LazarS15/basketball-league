package com.basketball.league.dataaccess.player.mapper;

import com.basketball.league.dataaccess.player.entity.PlayerEntity;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.valueobject.PlayerId;
import com.basketball.league.domain.valueobject.TeamId;
import org.springframework.stereotype.Component;

@Component
public class PlayerDataAccessMapper {
    public Player playerEntityToPlayer(PlayerEntity playerEntity) {
        return Player.builder()
                .id(new PlayerId(playerEntity.getId()))
                .firstName(playerEntity.getFirstName())
                .lastName(playerEntity.getLastName())
                .jerseyNumber(playerEntity.getJerseyNumber())
                .teamId(new TeamId(playerEntity.getTeamId()))
                .birthDate(playerEntity.getBirthDate())
                .imagePath(playerEntity.getImagePath())
                .build();
    }
    public PlayerEntity playerToPlayerEntity(Player player) {
        return PlayerEntity.builder()
                .id(player.getId() == null ? null : player.getId().getValue())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .jerseyNumber(player.getJerseyNumber())
                .teamId(player.getTeamId().getValue())
                .birthDate(player.getBirthDate())
                .imagePath(player.getImagePath())
                .build();
    }
}
