package com.basketball.league.application.dto.security;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    @NotNull
    @Size(min = 7, max = 99)
    private String currentPassword;
    @NotNull
    @Size(min = 7, max = 99)
    private String newPassword;
    @NotNull
    private String confirmationPassword;
}
