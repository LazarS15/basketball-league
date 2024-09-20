package com.basketball.league.application.security.service;

import com.basketball.league.application.dto.security.ChangePasswordRequest;
import com.basketball.league.application.ports.input.service.UserService;
import com.basketball.league.application.ports.output.repository.security.UserRepository;
import com.basketball.league.application.exception.password.NewPasswordMismatchException;
import com.basketball.league.application.exception.password.WrongCurrentPasswordException;
import com.basketball.league.application.security.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        log.info("Changing password for user with UserName: {}", user.getEmail());

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new WrongCurrentPasswordException("Wrong current password!");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new NewPasswordMismatchException("Passwords are not matching!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Password is changed!");
    }
}
