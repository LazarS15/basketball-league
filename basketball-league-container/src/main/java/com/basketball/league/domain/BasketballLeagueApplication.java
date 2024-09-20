package com.basketball.league.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.basketball.league.dataaccess")
@EntityScan(basePackages = "com.basketball.league.dataaccess")
@SpringBootApplication(scanBasePackages = "com.basketball.league")
public class BasketballLeagueApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasketballLeagueApplication.class, args);
    }
}
