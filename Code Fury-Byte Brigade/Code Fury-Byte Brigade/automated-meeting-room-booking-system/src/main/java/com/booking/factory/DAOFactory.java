package com.booking.factory;

import com.booking.dao.*;

public class DAOFactory {
    public static UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public static MeetingRoomDAO getMeetingRoomDAO() {
        return new MeetingRoomDAOImpl();
    }

    public static BookingDAO getBookingDAO() {
        return new BookingDAOImpl();
    }
}

