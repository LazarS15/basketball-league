package com.basketball.league.application.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class RegisterRequest {
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    @Email(message = "Email must be valid!")
    private final String email;
    @NotNull
    @Size(min = 7, max = 99)
    private final String password;
    @NotNull
    private final String role;
}
