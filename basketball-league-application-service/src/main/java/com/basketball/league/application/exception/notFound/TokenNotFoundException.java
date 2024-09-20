package com.basketball.league.application.exception.notFound;

public class TokenNotFoundException extends NotFoundException{
    public TokenNotFoundException(String message) {
        super(message);
    }
}
