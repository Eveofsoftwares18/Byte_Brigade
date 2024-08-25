package com.booking.factory;

import com.booking.dao.*;
import com.booking.service.*;
public class ServiceFactory {

    private static final UserDAO userDAO = new UserDAOImpl();
    private static final MeetingRoomDAO meetingRoomDAO = new MeetingRoomDAOImpl();
    private static final BookingDAO bookingDAO = new BookingDAOImpl();

    public static UserService getUserService() {
        return new UserServiceImpl(userDAO);
    }

    public static MeetingRoomService getMeetingRoomService() {
        return new MeetingRoomServiceImpl(meetingRoomDAO);
    }

    public static BookingService getBookingService() {
        return new BookingServiceImpl(bookingDAO);
    }

    public static CreditService getCreditService() {
        return new CreditService(userDAO);
    }
}

