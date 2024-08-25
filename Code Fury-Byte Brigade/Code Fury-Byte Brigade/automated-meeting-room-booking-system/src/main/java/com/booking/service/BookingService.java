package com.booking.service;

import com.booking.beans.Booking;
import com.booking.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public interface BookingService {
        Booking getBookingById(int id) throws BookingNotFoundException, SQLException;
        void addBooking(Booking booking) throws SQLException,RoomNotFoundException, RoomAlreadyBookedException,UserNotFoundException,InsufficientCreditsException;
        void updateBooking(Booking booking) throws SQLException;
        void cancelBooking(int id) throws BookingNotFoundException, SQLException;
        List<Booking> getAllBookings() throws SQLException;  // Updated to throw SQLException
}


