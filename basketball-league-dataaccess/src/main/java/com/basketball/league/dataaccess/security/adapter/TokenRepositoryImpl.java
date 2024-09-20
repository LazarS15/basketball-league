package com.basketball.league.dataaccess.security.adapter;

import com.basketball.league.application.ports.output.repository.security.TokenRepository;
import com.basketball.league.application.security.token.Token;
import com.basketball.league.dataaccess.security.mapper.SecurityDataAccessMapper;
import com.basketball.league.dataaccess.security.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;
    private final SecurityDataAccessMapper dataAccessMapper;

    @Override
    public Optional<Token> getByToken(String token) {
        return tokenJpaRepository.findByToken(token).map(dataAccessMapper::tokenEntityToToken);
    }

    @Override
    public List<Token> getAllValidTokenByUser(Long id) {
        return tokenJpaRepository.findAllValidTokenByUserId(id).stream()
                .map(dataAccessMapper::tokenEntityToToken).toList();
    }

    @Override
    public Token save(Token token) {
        return dataAccessMapper.tokenEntityToToken(tokenJpaRepository
                .save(dataAccessMapper.tokenToTokenEntity(token)));
    }
}
