package com.booking.service;

import com.booking.beans.MeetingRoom;
import com.booking.dao.MeetingRoomDAO;
import com.booking.exceptions.DataAccessException;
import com.booking.exceptions.RoomNotFoundException;

import java.sql.Timestamp;
import java.util.List;

public class MeetingRoomServiceImpl implements MeetingRoomService {

    private MeetingRoomDAO meetingRoomDAO;

    public MeetingRoomServiceImpl(MeetingRoomDAO meetingRoomDAO) {
        this.meetingRoomDAO = meetingRoomDAO;
    }

    @Override
    public MeetingRoom getMeetingRoom(int id) throws RoomNotFoundException {
        return meetingRoomDAO.getMeetingRoomById(id);
    }

    @Override
    public boolean isRoomAvailable(int roomId, Timestamp startTime, Timestamp endTime) {
        return meetingRoomDAO.isRoomAvailable(roomId, startTime, endTime);
    }

    @Override
    public int calculateRoomCost(MeetingRoom room) {
        return meetingRoomDAO.calculateRoomCost(room);
    }
    @Override
    public List<MeetingRoom> getAllMeetingRooms() throws DataAccessException {
        return meetingRoomDAO.getAllMeetingRooms();
    }
    @Override
    public void addMeetingRoom(MeetingRoom meetingRoom) throws DataAccessException {
        meetingRoomDAO.createMeetingRoom(meetingRoom);
    }
}

