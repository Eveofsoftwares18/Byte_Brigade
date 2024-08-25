package com.booking.service;

import com.booking.beans.Booking;
import com.booking.dao.*;
import com.booking.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private MeetingRoomDAO meetingRoomDAO;
    private  UserDAO userDAO;

    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
    public BookingServiceImpl(BookingDAO bookingDAO, MeetingRoomDAO meetingRoomDAO, UserDAO userDAO) {
        if (meetingRoomDAO == null) {
            throw new IllegalArgumentException("MeetingRoomDAO cannot be null");
        }
        this.bookingDAO = bookingDAO;
        this.meetingRoomDAO = meetingRoomDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Booking getBookingById(int id) throws BookingNotFoundException, SQLException {
        return bookingDAO.getBookingById(id);
    }

    @Override
    public void addBooking(Booking booking) throws SQLException,RoomNotFoundException, RoomAlreadyBookedException,UserNotFoundException,InsufficientCreditsException {

            bookingDAO.addBooking(booking);

    }

    @Override
    public void updateBooking(Booking booking) throws SQLException {
        bookingDAO.updateBooking(booking);
    }

    @Override
    public void cancelBooking(int id) throws BookingNotFoundException, SQLException {
        bookingDAO.cancelBooking(id);
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        return bookingDAO.getAllBookings();
    }
}


