package com.basketball.league.dataaccess.tableField.adapter;

import com.basketball.league.application.exception.notFound.TableFieldNotFoundException;
import com.basketball.league.application.ports.output.repository.TableFieldRepository;
import com.basketball.league.dataaccess.tableField.entity.TableFieldEntity;
import com.basketball.league.dataaccess.tableField.mapper.TableFieldDataAccessMapper;
import com.basketball.league.dataaccess.tableField.repository.TableFieldJpaRepository;
import com.basketball.league.domain.entity.TableField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TableFieldRepositoryImpl implements TableFieldRepository {

    private final TableFieldDataAccessMapper tableFieldDataAccessMapper;
    private final TableFieldJpaRepository tableFieldJpaRepository;

    @Override
    public TableField save(TableField tableField) {
        return tableFieldDataAccessMapper.tableFieldEntityToTableField(
                tableFieldJpaRepository.save(tableFieldDataAccessMapper.tableFieldToTableFieldEntity(tableField)));
    }

    @Override
    public Optional<TableField> findByTeamId(Long teamId) {
        return tableFieldJpaRepository.findByTeamId(teamId)
                .map(tableFieldDataAccessMapper::tableFieldEntityToTableField);
    }

    @Override
    public TableField updateWinsAndLosesAndPlusMinusAndPoints(TableField tableField) {
        Optional<TableFieldEntity> tableFieldResult = tableFieldJpaRepository.findById(tableField.getId().getValue());
        if (tableFieldResult.isPresent()) {
            TableFieldEntity tableFieldEntity = tableFieldResult.get();
            tableFieldEntity.setWins(tableField.getWins());
            tableFieldEntity.setLosses(tableField.getLosses());
            tableFieldEntity.setPlusMinus(tableField.getPlusMinus());
            tableFieldEntity.setPoints(tableField.getPoints());
            return save(tableFieldDataAccessMapper.tableFieldEntityToTableField(tableFieldEntity));
        } else {
            throw new TableFieldNotFoundException("Could not find TableField with id: " + tableField.getId());
        }
    }

}
