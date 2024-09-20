package com.basketball.league.application.dto.security;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthenticationRequest {
    @NotNull
    private final String email;
    @NotNull
    private final String password;
}
