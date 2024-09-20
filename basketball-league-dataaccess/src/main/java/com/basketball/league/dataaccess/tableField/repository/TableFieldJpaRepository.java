package com.basketball.league.dataaccess.tableField.repository;

import com.basketball.league.dataaccess.tableField.entity.TableFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableFieldJpaRepository extends JpaRepository<TableFieldEntity, Long> {
    Optional<TableFieldEntity> findByTeamId(Long teamId);
}
