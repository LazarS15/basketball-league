package com.basketball.league.application.ports.input.service;

import com.basketball.league.application.dto.security.ChangePasswordRequest;
import jakarta.validation.Valid;

import java.security.Principal;

public interface UserService {
    void changePassword(@Valid ChangePasswordRequest request, Principal connectedUser);
}
