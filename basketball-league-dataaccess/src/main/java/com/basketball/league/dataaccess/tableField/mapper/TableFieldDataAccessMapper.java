package com.basketball.league.dataaccess.tableField.mapper;

import com.basketball.league.dataaccess.tableField.entity.TableFieldEntity;
import com.basketball.league.domain.entity.TableField;
import com.basketball.league.domain.valueobject.TableFieldId;
import com.basketball.league.domain.valueobject.TeamId;
import org.springframework.stereotype.Component;

@Component
public class TableFieldDataAccessMapper {
    public TableField tableFieldEntityToTableField(TableFieldEntity tableFieldEntity) {
        return TableField.builder()
                .id(new TableFieldId(tableFieldEntity.getId()))
                .teamId(new TeamId(tableFieldEntity.getTeamId()))
                .wins(tableFieldEntity.getWins())
                .losses(tableFieldEntity.getLosses())
                .plusMinus(tableFieldEntity.getPlusMinus())
                .points(tableFieldEntity.getPoints())
                .build();
    }

    public TableFieldEntity tableFieldToTableFieldEntity(TableField tableField) {
        return TableFieldEntity.builder()
                .id(tableField.getId().getValue())
                .teamId(tableField.getTeamId().getValue())
                .wins(tableField.getWins())
                .losses(tableField.getLosses())
                .plusMinus(tableField.getPlusMinus())
                .points(tableField.getPoints())
                .build();
    }
}
