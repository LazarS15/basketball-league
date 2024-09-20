package com.basketball.league.dataaccess.team.mapper;

import com.basketball.league.dataaccess.team.entity.TeamEntity;
import com.basketball.league.domain.entity.Team;
import com.basketball.league.domain.valueobject.TeamId;
import org.springframework.stereotype.Component;

@Component
public class TeamDataAccessMapper {
    public Team teamEntityToTeam(TeamEntity teamEntity) {
        return Team.builder()
                .id(new TeamId(teamEntity.getId()))
                .name(teamEntity.getName())
                .imagePath(teamEntity.getImagePath())
                .logoPath(teamEntity.getLogoPath())
                .hall(teamEntity.getHall())
                .build();
    }
    public TeamEntity teamToTeamEntity(Team team) {
        return TeamEntity.builder()
                .name(team.getName())
                .imagePath(team.getImagePath())
                .logoPath(team.getLogoPath())
                .hall(team.getHall())
                .build();
    }
}
