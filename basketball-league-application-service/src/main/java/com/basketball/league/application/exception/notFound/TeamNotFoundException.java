package com.basketball.league.application.exception.notFound;

public class TeamNotFoundException extends NotFoundException {
    public TeamNotFoundException(String message) {
        super(message);
    }
}
