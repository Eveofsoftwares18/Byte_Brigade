package com.booking.exceptions;

public class InsufficientCreditsException extends Exception{
    public InsufficientCreditsException() {
    }

    public InsufficientCreditsException(String message) {
        super(message);
    }
}
