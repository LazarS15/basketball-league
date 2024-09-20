package com.basketball.league.application.exception.alreadyExists;

public class UserAlreadyExistsException extends AlreadyExistsException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
