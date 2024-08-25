package com.booking.service;

import com.booking.beans.MeetingRoom;
import com.booking.exceptions.DataAccessException;
import com.booking.exceptions.RoomNotFoundException;

import java.sql.Timestamp;
import java.util.List;

public interface MeetingRoomService {
    MeetingRoom getMeetingRoom(int id) throws RoomNotFoundException;
    boolean isRoomAvailable(int roomId, Timestamp startTime, Timestamp endTime);
    int calculateRoomCost(MeetingRoom room);
    List<MeetingRoom> getAllMeetingRooms() throws DataAccessException;
    void addMeetingRoom(MeetingRoom meetingRoom) throws DataAccessException;
}

