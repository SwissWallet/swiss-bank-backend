package com.swiss.bank.exception;

public class UserUniqueViolationException extends RuntimeException {

    public UserUniqueViolationException(String message) {
        super(message);
    }
}

