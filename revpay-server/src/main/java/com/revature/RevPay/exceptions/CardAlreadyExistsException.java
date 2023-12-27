package com.revature.RevPay.exceptions;

public class CardAlreadyExistsException extends RuntimeException {

    public CardAlreadyExistsException(String message) {
        super(message);
    }

}
