package com.basketball.league.dataaccess.team.repository;

import com.basketball.league.dataaccess.team.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {
}
