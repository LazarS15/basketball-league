package com.basketball.league.dataaccess.playerStats.adapter;

import com.basketball.league.application.exception.notFound.PlayerStatsNotFoundException;
import com.basketball.league.application.ports.output.repository.PlayerStatsRepository;
import com.basketball.league.dataaccess.playerStats.entity.PlayerStatsEntity;
import com.basketball.league.dataaccess.playerStats.mapper.PlayerStatsDataaccessMapper;
import com.basketball.league.dataaccess.playerStats.repository.PlayerStatsJpaRepository;
import com.basketball.league.domain.entity.PlayerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PlayerStatsRepositoryImpl implements PlayerStatsRepository {

    private final PlayerStatsDataaccessMapper playerStatsDataaccessMapper;
    private final PlayerStatsJpaRepository playerStatsJpaRepository;

    @Override
    public PlayerStats save(PlayerStats playerStats) {
        return playerStatsDataaccessMapper.playerStatsEntityToPlayerStats(
                playerStatsJpaRepository.save(playerStatsDataaccessMapper.playerStatsToPlayerStatsEntity(playerStats)));
    }

    @Override
    public Optional<PlayerStats> findByGameIdAndPlayerId(UUID gameId, Long playerId) {
        return playerStatsJpaRepository.findByGameIdAndPlayerId(gameId, playerId)
                .map(playerStatsDataaccessMapper::playerStatsEntityToPlayerStats);
    }

    @Override
    public PlayerStats updatePlayerPoints(PlayerStats playerStats) {
        Optional<PlayerStatsEntity> playerStatsEntityResult = playerStatsJpaRepository
                .findById(playerStats.getId().getValue());
        if (playerStatsEntityResult.isPresent()) {
            PlayerStatsEntity playerStatsEntity = playerStatsEntityResult.get();
            playerStatsEntity.setPlayerPoints(playerStats.getPlayerPoints());
            return save(playerStatsDataaccessMapper.playerStatsEntityToPlayerStats(playerStatsEntity));
        }else {
            throw new PlayerStatsNotFoundException("Could not find PlayerStats with id: " +
                    playerStats.getId().getValue());
        }
    }

    @Override
    public Optional<List<PlayerStats>> findByGameId(UUID gameId) {
        return playerStatsJpaRepository.findByGameId(gameId)
                .map(playerStatsEntities -> playerStatsEntities.stream()
                        .map(playerStatsDataaccessMapper::playerStatsEntityToPlayerStats).toList());
    }
}
