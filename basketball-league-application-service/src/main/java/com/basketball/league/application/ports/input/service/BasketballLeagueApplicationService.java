package com.basketball.league.application.ports.input.service;

import com.basketball.league.application.dto.create.command.CreateGameCommand;
import com.basketball.league.application.dto.create.command.CreatePlayerCommand;
import com.basketball.league.application.dto.create.command.CreateTeamCommand;
import com.basketball.league.application.dto.create.response.CreateGameResponse;
import com.basketball.league.application.dto.create.response.CreatePlayerResponse;
import com.basketball.league.application.dto.create.response.CreateTeamResponse;
import com.basketball.league.application.dto.create.response.GameStateChangerResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface BasketballLeagueApplicationService {
    CreatePlayerResponse createPlayer(@Valid CreatePlayerCommand createPlayerCommand);
    CreateTeamResponse createTeam(@Valid CreateTeamCommand createTeamCommand);
    CreateGameResponse createGame(@Valid CreateGameCommand createGameCommand);
    GameStateChangerResponse updateGameState(UUID gameId);
}
