package com.basketball.league.application;

import com.basketball.league.application.dto.create.command.CreateGameCommand;
import com.basketball.league.application.dto.create.command.CreatePlayerCommand;
import com.basketball.league.application.dto.create.command.CreateTeamCommand;
import com.basketball.league.application.dto.create.response.CreateGameResponse;
import com.basketball.league.application.dto.create.response.CreatePlayerResponse;
import com.basketball.league.application.dto.create.response.CreateTeamResponse;
import com.basketball.league.application.dto.create.response.GameStateChangerResponse;
import com.basketball.league.application.ports.input.service.BasketballLeagueApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
@RequiredArgsConstructor
public class BasketballLeagueApplicationServiceImpl implements BasketballLeagueApplicationService {

    private final CreatePlayerCommandHandler createPlayerCommandHandler;
    private final CreateTeamCommandHandler createTeamCommandHandler;
    private final CreateGameCommandHandler createGameCommandHandler;
    private final UpdateGameStateCommandHandler updateGameStateCommandHandler;

    @Override
    public CreatePlayerResponse createPlayer(CreatePlayerCommand createPlayerCommand) {
        return createPlayerCommandHandler.createPlayer(createPlayerCommand);
    }

    @Override
    public CreateTeamResponse createTeam(CreateTeamCommand createTeamCommand) {
        return createTeamCommandHandler.createTeam(createTeamCommand);
    }

    @Override
    public CreateGameResponse createGame(CreateGameCommand createGameCommand) {
        return createGameCommandHandler.createGame(createGameCommand);
    }

    @Override
    public GameStateChangerResponse updateGameState(UUID gameId) {
        return updateGameStateCommandHandler.updateGameState(gameId);
    }
}
