package com.basketball.league.application.exception.password;

public class WrongCurrentPasswordException extends PasswordException{
    public WrongCurrentPasswordException(String s) {
        super(s);
    }
}
