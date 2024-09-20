package com.basketball.league.application.ports.output.repository.security;

import com.basketball.league.application.security.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getByEmail(String email);

    User save(User user);
}
