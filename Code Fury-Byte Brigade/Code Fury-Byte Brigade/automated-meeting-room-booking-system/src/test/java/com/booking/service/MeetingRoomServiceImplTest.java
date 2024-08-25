package com.booking.service;



import com.booking.beans.MeetingRoom;
import com.booking.dao.MeetingRoomDAO;
import com.booking.service.MeetingRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MeetingRoomServiceImplTest {

    private MeetingRoomServiceImpl meetingRoomService;
    private MeetingRoomDAO meetingRoomDAO;

    @BeforeEach
    public void setUp() {
        meetingRoomDAO = mock(MeetingRoomDAO.class);
        meetingRoomService = new MeetingRoomServiceImpl(meetingRoomDAO);
    }

    @Test
    public void testGetAllMeetingRooms_NoRoomsAvailable() throws Exception {
        when(meetingRoomDAO.getAllMeetingRooms()).thenReturn(Collections.emptyList());

        List<MeetingRoom> rooms = meetingRoomService.getAllMeetingRooms();

        assertTrue(rooms.isEmpty());
    }

    @Test
    public void testGetAllMeetingRooms_RoomsAvailable() throws Exception {
        MeetingRoom mockRoom = new MeetingRoom(1, "Conference Room", 10, true, true, false, true, true, false, true);
        when(meetingRoomDAO.getAllMeetingRooms()).thenReturn(List.of(mockRoom));

        List<MeetingRoom> rooms = meetingRoomService.getAllMeetingRooms();

        assertFalse(rooms.isEmpty());
        assertEquals(1, rooms.size());
        assertEquals("Conference Room", rooms.get(0).getName());
    }
}

