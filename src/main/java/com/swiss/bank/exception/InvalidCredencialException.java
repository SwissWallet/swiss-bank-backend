package com.swiss.bank.exception;

public class InvalidCredencialException extends RuntimeException {
    public InvalidCredencialException(String message) {
        super(message);
    }
}
