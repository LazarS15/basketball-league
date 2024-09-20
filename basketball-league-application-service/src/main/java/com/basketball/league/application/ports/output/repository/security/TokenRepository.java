package com.basketball.league.application.ports.output.repository.security;

import com.basketball.league.application.security.token.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    Optional<Token> getByToken(String token);
    List<Token> getAllValidTokenByUser(Long id);
    Token save(Token token);
}
