package com.basketball.league.dataaccess.player.adapter;

import com.basketball.league.application.ports.output.repository.PlayerRepository;
import com.basketball.league.dataaccess.player.mapper.PlayerDataAccessMapper;
import com.basketball.league.dataaccess.player.repository.PlayerJpaRepository;
import com.basketball.league.domain.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerDataAccessMapper playerDataAccessMapper;

    @Override
    public List<Player> getAll() {
        return playerJpaRepository.findAll().stream().map(playerDataAccessMapper::playerEntityToPlayer).toList();
    }

    @Override
    public Optional<List<Player>> getByTeamId(Long teamId) {
        return playerJpaRepository.findByTeamId(teamId)
                .map(playerEntities -> playerEntities.stream()
                        .map(playerDataAccessMapper::playerEntityToPlayer).toList());
    }

    @Override
    public Player save(Player player) {
        return playerDataAccessMapper.playerEntityToPlayer(
                playerJpaRepository.save(playerDataAccessMapper.playerToPlayerEntity(player)));
    }
}
