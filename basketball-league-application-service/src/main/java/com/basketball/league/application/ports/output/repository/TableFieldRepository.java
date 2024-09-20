package com.basketball.league.application.ports.output.repository;

import com.basketball.league.domain.entity.TableField;

import java.util.Optional;

public interface TableFieldRepository {
    TableField save(TableField tableField);
    Optional<TableField> findByTeamId(Long teamId);
    TableField updateWinsAndLosesAndPlusMinusAndPoints(TableField tableField);
}
