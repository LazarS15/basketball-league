package com.basketball.league.application.exception.password;

public class NewPasswordMismatchException extends PasswordException {
    public NewPasswordMismatchException(String s) {
        super(s);
    }
}
