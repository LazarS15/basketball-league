package com.basketball.league.application.config;

import com.basketball.league.application.ports.output.repository.*;
import com.basketball.league.application.ports.output.repository.security.TokenRepository;
import com.basketball.league.application.ports.output.repository.security.UserRepository;
import com.basketball.league.application.security.config.JwtService;
import com.basketball.league.domain.BasketballLeagueDomainService;
import com.basketball.league.domain.BasketballLeagueDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.basketball.league")
public class BeanTestConfiguration {

    @Bean
    public BasketballLeagueDomainService basketballLeagueDomainService() {
        return new BasketballLeagueDomainServiceImpl();
    }

    @Bean
    public PlayerRepository playerRepository() {
        return Mockito.mock(PlayerRepository.class);
    }

    @Bean
    public TeamRepository teamRepository() {
        return Mockito.mock(TeamRepository.class);
    }

    @Bean
    public TableFieldRepository tableFieldRepository() {
        return Mockito.mock(TableFieldRepository.class);
    }

    @Bean
    public PlayerStatsRepository playerStatsRepository() {
        return Mockito.mock(PlayerStatsRepository.class);
    }

    @Bean
    public GameRepository gameRepository() {
        return Mockito.mock(GameRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public TokenRepository tokenRepository() {
        return Mockito.mock(TokenRepository.class);
    }
    @MockBean
    private JwtService jwtService;

}
