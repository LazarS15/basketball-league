package com.basketball.league.dataaccess.security.mapper;

import com.basketball.league.application.security.token.Token;
import com.basketball.league.application.security.token.TokenType;
import com.basketball.league.application.security.user.Role;
import com.basketball.league.application.security.user.User;
import com.basketball.league.dataaccess.security.entity.TokenEntity;
import com.basketball.league.dataaccess.security.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SecurityDataAccessMapper {
    public User userEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(Role.valueOf(userEntity.getRole()))
                .build();

    }

    public UserEntity userToUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();

    }

    public Token tokenEntityToToken(TokenEntity token) {
        return Token.builder()
                .id(token.getId())
                .token(token.getToken())
                .tokenType(TokenType.valueOf(token.getTokenType()))
                .expired(token.isExpired())
                .revoked(token.isRevoked())
                .user(userEntityToUser(token.getUser()))
                .build();
    }

    public TokenEntity tokenToTokenEntity(Token token) {
        return TokenEntity.builder()
                .id(token.getId())
                .token(token.getToken())
                .tokenType(token.getTokenType().toString())
                .expired(token.isExpired())
                .revoked(token.isRevoked())
                .user(userToUserEntity(token.user))
                .build();
    }
}
