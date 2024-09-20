package com.basketball.league.api.controller.tableField;

import com.basketball.league.dataaccess.tableField.entity.TableFieldEntity;
import com.basketball.league.dataaccess.tableField.repository.TableFieldJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/tablefields")
public class TableFieldController {
    private final TableFieldJpaRepository tableFieldJpaRepository;

    public TableFieldController(TableFieldJpaRepository tableFieldJpaRepository) {
        this.tableFieldJpaRepository = tableFieldJpaRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<TableFieldEntity>> getAllSortedTableFields() {
        Sort sortByPointsAndPlusMinus = Sort.by(
                Sort.Order.desc("points"),
                Sort.Order.desc("plusMinus")
        );
        List<TableFieldEntity> sortedEntities = tableFieldJpaRepository.findAll(sortByPointsAndPlusMinus);
        return ResponseEntity.ok(sortedEntities);
    }
}
