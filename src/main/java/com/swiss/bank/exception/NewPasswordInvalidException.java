package com.swiss.bank.exception;

public class NewPasswordInvalidException extends RuntimeException {

    public NewPasswordInvalidException(String message) {
        super(message);
    }

}
