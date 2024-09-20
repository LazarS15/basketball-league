package com.basketball.league.application.mapper;

import com.basketball.league.application.dto.create.command.CreateGameCommand;
import com.basketball.league.application.dto.create.command.CreatePlayerCommand;
import com.basketball.league.application.dto.create.command.CreateTeamCommand;
import com.basketball.league.application.dto.create.response.CreateGameResponse;
import com.basketball.league.application.dto.create.response.CreatePlayerResponse;
import com.basketball.league.application.dto.create.response.CreateTeamResponse;
import com.basketball.league.application.dto.create.response.GameStateChangerResponse;
import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.Team;
import com.basketball.league.domain.valueobject.GameId;
import com.basketball.league.domain.valueobject.TeamId;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class BasketballLeagueApplicationMapper {

    public Team createTeamCommandToTeam(CreateTeamCommand createTeamCommand) {
        return Team.builder()
                .name(createTeamCommand.getName())
                .imagePath(createTeamCommand.getImagePath())
                .logoPath(createTeamCommand.getLogoPath())
                .hall(createTeamCommand.getHall())
                .build();
    }
    public CreateTeamResponse teamToCreateTeamResponse(Team team) {
        return CreateTeamResponse.builder()
                .team(team)
                .message("Team with id: " + team.getId().getValue() + " is created!")
                .build();
    }
    public Player createPlayerCommandToPlayer(CreatePlayerCommand createPlayerCommand, Team team) {
        return Player.builder()
                .firstName(createPlayerCommand.getFirstName())
                .lastName(createPlayerCommand.getLastName())
                .jerseyNumber(createPlayerCommand.getJerseyNumber())
                .teamId(team.getId())
                .imagePath(createPlayerCommand.getImagePath())
                .birthDate(LocalDate.parse(createPlayerCommand.getBirthDate()))
                .build();
    }
    public CreatePlayerResponse playerToCreatePlayerResponse(Player player) {
        return CreatePlayerResponse.builder()
                .player(player)
                .message("Player with id: " + player.getId().getValue() + " is created!")
                .build();
    }
    public GameStateChangerResponse gameToGameStateChangerResponse(Game game) {
        return GameStateChangerResponse.builder()
                .game(game)
                .message("Game with id: " + game.getId().getValue() + " is updated")
                .build();
    }

    public Game createGameCommandToGame(CreateGameCommand createGameCommand) {
        return Game.builder()
                .id(new GameId(UUID.randomUUID()))
                .round(createGameCommand.getRound())
                .homeTeamId(new TeamId(createGameCommand.getHomeTeamId()))
                .homeTeamPoints(0)
                .guestTeamPoints(0)
                .guestTeamId(new TeamId(createGameCommand.getGuestTeamId()))
                .isPlayed(false)
                .build();
    }

    public CreateGameResponse gameToCreateGameResponse(Game game) {
        return CreateGameResponse.builder()
                .game(game)
                .message("Game with id: " + game.getId().getValue() + " is created!")
                .build();
    }
}
