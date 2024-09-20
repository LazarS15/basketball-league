package com.basketball.league.application.security.token;

import com.basketball.league.application.security.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private Long id;
    private String token;
    private TokenType tokenType;
    private boolean revoked;
    private boolean expired;
    public User user;
}
