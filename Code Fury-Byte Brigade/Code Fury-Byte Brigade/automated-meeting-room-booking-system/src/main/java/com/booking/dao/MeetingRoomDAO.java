package com.booking.dao;

import com.booking.beans.MeetingRoom;
import com.booking.exceptions.DataAccessException;
import com.booking.exceptions.RoomNotFoundException;

import java.sql.Timestamp;
import java.util.List;

public interface MeetingRoomDAO {
    void createMeetingRoom(MeetingRoom meetingRoom) throws DataAccessException;
    MeetingRoom getMeetingRoomById(int id) throws RoomNotFoundException;
    boolean isRoomAvailable(int roomId, Timestamp startTime, Timestamp endTime);
    int calculateRoomCost(MeetingRoom room);
    List<MeetingRoom> getAllMeetingRooms() throws DataAccessException;
}

