package com.basketball.league.application;

import com.basketball.league.application.databasecommands.GetEntityCommandHandler;
import com.basketball.league.application.ports.output.repository.TableFieldRepository;
import com.basketball.league.domain.entity.Game;
import com.basketball.league.domain.entity.TableField;
import com.basketball.league.domain.valueobject.TableFieldId;
import com.basketball.league.domain.valueobject.TeamId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TableOrderingCommandHandler {

    private final TableFieldRepository tableFieldRepository;
    private final GetEntityCommandHandler getEntityCommandHandler;
    private static final int WINNER_POINTS = 2;
    private static final int LOOSER_POINTS = 1;


    public void updateTableDetails(Game game, boolean isGameRestarted) {
        TeamId winnerId = game.getHomeTeamPoints() > game.getGuestTeamPoints() ? game.getHomeTeamId() : game.getGuestTeamId();
        TeamId looserId = game.getHomeTeamPoints() < game.getGuestTeamPoints() ? game.getHomeTeamId() : game.getGuestTeamId();
        int pointDifference = getPlusMinus(game, winnerId);

        updateTableField(winnerId.getValue(), pointDifference,true, isGameRestarted);
        updateTableField(looserId.getValue(), pointDifference, false, isGameRestarted);
    }

    @Transactional
    private void updateTableField(Long teamId, int gamePointsDifference, boolean isWinner,
                                  boolean isGameRestarted) {
        TableField tableField = getEntityCommandHandler.getTableFieldByTeamId(teamId);
        int wins = tableField.getWins();
        int looses = tableField.getLosses();
        int plusMinus = tableField.getPlusMinus();
        int points = tableField.getPoints();

        if (isWinner && !isGameRestarted) {
            tableField.setTableFieldDetails(wins + 1,
                    looses,
                    plusMinus + gamePointsDifference,
                    points + WINNER_POINTS);
        } else if (!isWinner && !isGameRestarted) {
            tableField.setTableFieldDetails(wins,
                    looses + 1,
                    plusMinus - gamePointsDifference,
                    points + LOOSER_POINTS);
        } else if (isWinner && isGameRestarted) {
            tableField.setTableFieldDetails(wins - 1,
                    looses,
                    plusMinus - gamePointsDifference,
                    points - WINNER_POINTS);
        } else if (!isWinner && isGameRestarted) {
            tableField.setTableFieldDetails(wins,
                    looses - 1,
                    plusMinus + gamePointsDifference,
                    points - LOOSER_POINTS);
        }

        log.info("TableFieldId: {}, wins: {}, losses: {} and plusMinus: {}", tableField.getId().getValue(),
                tableField.getWins(),
                tableField.getLosses(),
                tableField.getPlusMinus());
        tableFieldRepository.updateWinsAndLosesAndPlusMinusAndPoints(tableField);
    }

    public TableField initializeTableField(TeamId teamId) {
        TableField tableField = TableField.builder()
                .id(new TableFieldId(teamId.getValue()))
                .teamId(teamId)
                .wins(0)
                .losses(0)
                .plusMinus(0)
                .points(0)
                .build();
        return tableFieldRepository.save(tableField);
    }
    private int getPlusMinus(Game game, TeamId winnerId) {
        if (game.getHomeTeamId() == winnerId) {
            return game.getHomeTeamPoints() - game.getGuestTeamPoints();
        }
        return game.getGuestTeamPoints() - game.getHomeTeamPoints();
    }
}
