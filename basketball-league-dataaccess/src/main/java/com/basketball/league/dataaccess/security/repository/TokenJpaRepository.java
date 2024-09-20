package com.basketball.league.dataaccess.security.repository;

import com.basketball.league.dataaccess.security.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findAllValidTokenByUserId(Long id);
    Optional<TokenEntity> findByToken(String token);
}
