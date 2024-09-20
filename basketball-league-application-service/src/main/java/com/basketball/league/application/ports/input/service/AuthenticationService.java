package com.basketball.league.application.ports.input.service;

import com.basketball.league.application.dto.security.AuthenticationRequest;
import com.basketball.league.application.dto.security.AuthenticationResponse;
import com.basketball.league.application.dto.security.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(@Valid RegisterRequest request);

    AuthenticationResponse authenticate(@Valid AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
