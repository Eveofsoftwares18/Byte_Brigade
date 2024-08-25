package com.booking.dao;

import com.booking.beans.Booking;
import com.booking.exceptions.*;

import java.sql.Timestamp;

import java.util.List;

public interface BookingDAO {
    Booking getBookingById(int id) throws BookingNotFoundException;
    void addBooking(Booking booking) throws RoomNotFoundException,RoomAlreadyBookedException,UserNotFoundException,InsufficientCreditsException;
    void updateBooking(Booking booking) throws DataAccessException;
    void cancelBooking(int id) throws BookingNotFoundException, DataAccessException;
    List<Booking> getAllBookings() throws DataAccessException;  // New method
}

