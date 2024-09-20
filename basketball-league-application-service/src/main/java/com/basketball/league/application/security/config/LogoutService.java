package com.basketball.league.application.security.config;

import com.basketball.league.application.exception.notFound.TokenNotFoundException;
import com.basketball.league.application.security.token.Token;
import com.basketball.league.application.ports.output.repository.security.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String jwt = authHeader.substring(7);
        Token storedToken = tokenRepository.getByToken(jwt)
                .orElseThrow(() -> new TokenNotFoundException("Could not find token: " + jwt));
        if (storedToken != null) {
            log.info("Logging out user with email: {}", storedToken.getUser().getEmail());
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
