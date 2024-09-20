package com.basketball.league.application.exception.password;

public abstract class PasswordException extends IllegalStateException {
    public PasswordException(String message) {
        super(message);
    }
}
