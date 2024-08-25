package com.booking.dao;

import com.booking.beans.MeetingRoom;
import com.booking.exceptions.DataAccessException;
import com.booking.exceptions.RoomNotFoundException;
import com.booking.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingRoomDAOImpl implements MeetingRoomDAO {

    @Override
    public MeetingRoom getMeetingRoomById(int id) throws RoomNotFoundException {
        String sql = "SELECT * FROM meeting_rooms WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MeetingRoom room = new MeetingRoom();
                    room.setId(rs.getInt("id"));
                    room.setName(rs.getString("name"));
                    room.setSeatingCapacity(rs.getInt("seating_capacity"));
                    room.setProjector(rs.getBoolean("projector"));
                    room.setWifi(rs.getBoolean("wifi"));
                    room.setConferenceCall(rs.getBoolean("conference_call"));
                    room.setWhiteboard(rs.getBoolean("whiteboard"));
                    room.setWaterDispenser(rs.getBoolean("water_dispenser"));
                    room.setTv(rs.getBoolean("tv"));
                    room.setCoffeeMachine(rs.getBoolean("coffee_machine"));
                    room.setPerHourCost(rs.getInt("per_hour_cost"));
                    return room;
                } else {
                    throw new RoomNotFoundException("Meeting room with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error occurred.");
        }
    }

    @Override
    public boolean isRoomAvailable(int roomId, Timestamp startTime, Timestamp endTime) {
        String sql = "SELECT * FROM bookings WHERE room_id = ? AND (start_time < ? AND end_time > ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setTimestamp(2, endTime);
            stmt.setTimestamp(3, startTime);
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next(); // Room is available if no conflicting bookings
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error occurred.");
        }
    }

    @Override
    public int calculateRoomCost(MeetingRoom room) {
        int cost = 0;
        // Calculate cost based on amenities
        if (room.isProjector()) cost += 5;
        if (room.isWifi()) cost += 10;
        if (room.isConferenceCall()) cost += 15;
        if (room.isWhiteboard()) cost += 5;
        if (room.isWaterDispenser()) cost += 5;
        if (room.isTv()) cost += 10;
        if (room.isCoffeeMachine()) cost += 10;

        // Calculate cost based on seating capacity
        if (room.getSeatingCapacity() > 10) cost += 20;
        else if (room.getSeatingCapacity() > 5) cost += 10;

        return cost;
    }
    @Override
    public List<MeetingRoom> getAllMeetingRooms() throws DataAccessException {
        String query = "SELECT * FROM meeting_rooms";
        List<MeetingRoom> rooms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(new MeetingRoom(rs.getInt("id"), rs.getString("name"), rs.getInt("capacity"), rs.getBoolean("projector"),
                        rs.getBoolean("wifi"), rs.getBoolean("conference_call"), rs.getBoolean("whiteboard"),
                        rs.getBoolean("water_dispenser"), rs.getBoolean("tv"), rs.getBoolean("coffee_machine"),rs.getInt("perHourCost")));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all meeting rooms", e);
        }
        return rooms;
    }
    @Override
    public void createMeetingRoom(MeetingRoom meetingRoom) throws DataAccessException {
        String sql = "INSERT INTO meeting_rooms (name, seating_capacity, projector, wifi, conference_call, whiteboard, water_dispenser, tv, coffee_machine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meetingRoom.getName());
            pstmt.setInt(2, meetingRoom.getSeatingCapacity());
            pstmt.setBoolean(3, meetingRoom.isProjector());
            pstmt.setBoolean(4, meetingRoom.isWifi());
            pstmt.setBoolean(5, meetingRoom.isConferenceCall());
            pstmt.setBoolean(6, meetingRoom.isWhiteboard());
            pstmt.setBoolean(7, meetingRoom.isWaterDispenser());
            pstmt.setBoolean(8, meetingRoom.isTv());
            pstmt.setBoolean(9, meetingRoom.isCoffeeMachine());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error creating meeting room", e);
        }
    }


}

