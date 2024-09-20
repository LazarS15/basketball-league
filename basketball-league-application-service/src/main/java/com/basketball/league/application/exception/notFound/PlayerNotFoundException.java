package com.basketball.league.application.exception.notFound;

public class PlayerNotFoundException extends NotFoundException{
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
