package com.booking.service;



import com.booking.beans.*;
import com.booking.dao.BookingDAO;
import com.booking.dao.MeetingRoomDAO;
import com.booking.dao.UserDAO;
import com.booking.exceptions.*;
import com.booking.service.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    private BookingServiceImpl bookingService;
    private BookingDAO bookingDAO;
    private MeetingRoomDAO meetingRoomDAO;
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        bookingDAO = mock(BookingDAO.class);
        meetingRoomDAO = mock(MeetingRoomDAO.class);
        userDAO = mock(UserDAO.class);
        bookingService = new BookingServiceImpl(bookingDAO, meetingRoomDAO, userDAO);
    }

    @Test
    public void testGetAllBookings_NoBookingsAvailable() throws Exception {
        when(bookingDAO.getAllBookings()).thenReturn(Collections.emptyList());

        List<Booking> bookings = bookingService.getAllBookings();

        assertTrue(bookings.isEmpty());
    }

    @Test
    public void testGetAllBookings_BookingsAvailable() throws Exception {
        Booking mockBooking = new Booking(1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));
        when(bookingDAO.getAllBookings()).thenReturn(List.of(mockBooking));

        List<Booking> bookings = bookingService.getAllBookings();

        assertFalse(bookings.isEmpty());
        assertEquals(1, bookings.size());
    }
}

