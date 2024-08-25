package com.booking.exceptions;

public class BookingNotFoundException extends Exception{
    public BookingNotFoundException() {
    }

    public BookingNotFoundException(String message) {
        super(message);
    }
}
