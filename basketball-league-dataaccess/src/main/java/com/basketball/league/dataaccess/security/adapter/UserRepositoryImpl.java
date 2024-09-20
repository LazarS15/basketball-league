package com.basketball.league.dataaccess.security.adapter;

import com.basketball.league.application.ports.output.repository.security.UserRepository;
import com.basketball.league.application.security.user.User;
import com.basketball.league.dataaccess.security.mapper.SecurityDataAccessMapper;
import com.basketball.league.dataaccess.security.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final SecurityDataAccessMapper dataAccessMapper;
    @Override
    public Optional<User> getByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(dataAccessMapper::userEntityToUser);
    }

    @Override
    public User save(User user) {
        return dataAccessMapper.userEntityToUser(userJpaRepository.save(dataAccessMapper.userToUserEntity(user)));
    }
}
