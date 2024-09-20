package com.basketball.league.domain;

import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.Player;
import com.basketball.league.domain.entity.PlayerStats;
import com.basketball.league.domain.entity.Team;

import java.util.List;

public interface BasketballLeagueDomainService {
    void validatePlayer(Player player);
    void validateGameReadyTeam(Team team, List<Player> players);
    void validateInitializedGame(Game game);
    void validateGameResult(Game game, List<PlayerStats> playerStats);
}
