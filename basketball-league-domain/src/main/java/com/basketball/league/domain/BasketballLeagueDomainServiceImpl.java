package com.basketball.league.domain;

import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.PlayerStats;
import com.basketball.league.domain.entity.Team;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BasketballLeagueDomainServiceImpl implements BasketballLeagueDomainService{

    @Override
    public void validatePlayer(Player player) {
        player.validatePlayer();
    }

    @Override
    public void validateGameReadyTeam(Team team, List<Player> players) {
        team.validateTeam(players);
        log.info("Team with teamId: {} has been validated to play the game!", team.getId().getValue());
    }

    @Override
    public void validateInitializedGame(Game game) {
        game.validateInitializedGame();
        log.info("Game with gameId: {} has been validated and initialized!", game.getId().getValue());
    }

    @Override
    public void validateGameResult(Game game, List<PlayerStats> playerStats) {
        game.validateResult(playerStats);
        log.info("Game result with gameId: {} has been validated!", game.getId().getValue());
    }
}
