package com.swiss.bank.exception;

public class BalanceInsuficientException extends RuntimeException {
    public BalanceInsuficientException(String message) {
        super(message);
    }
}
