package com.booking.exceptions;

public class RoomAlreadyBookedException extends Exception{
    public RoomAlreadyBookedException() {
    }

    public RoomAlreadyBookedException(String message) {
        super(message);
    }
}
