package com.booking.dao;

import com.booking.beans.Booking;
import com.booking.beans.MeetingRoom;
import com.booking.beans.User;
import com.booking.exceptions.*;
import com.booking.factory.DAOFactory;
import com.booking.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public void addBooking(Booking booking) throws RoomNotFoundException,RoomAlreadyBookedException,UserNotFoundException,InsufficientCreditsException{
        MeetingRoomDAO meetingRoomDAO = DAOFactory.getMeetingRoomDAO();
        MeetingRoom room = meetingRoomDAO.getMeetingRoomById(booking.getRoomId());

        if (!meetingRoomDAO.isRoomAvailable(booking.getRoomId(), new Timestamp(booking.getStartTime().getTime()), new Timestamp(booking.getEndTime().getTime()))) {
            throw new RoomAlreadyBookedException("Room is already booked for the specified time.");
        }

        // Calculate cost and update credits
        int cost = meetingRoomDAO.calculateRoomCost(room);
        UserDAO userDAO = DAOFactory.getUserDAO();
        User user = userDAO.getUserById(booking.getManagerId());

        if (user.getCredits() < cost) {
            throw new InsufficientCreditsException("Insufficient credits for booking.");
        }

        // Add booking to database
        String sql = "INSERT INTO bookings (room_id, manager_id, start_time, end_time, cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getRoomId());
            stmt.setInt(2, booking.getManagerId());
            stmt.setTimestamp(3, new Timestamp(booking.getStartTime().getTime()));
            stmt.setTimestamp(4, new Timestamp(booking.getEndTime().getTime()));
            stmt.setInt(5, cost);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error occurred.");
        }

        // Update manager's credits
        userDAO.updateCredits(booking.getManagerId(), cost);
    }
    @Override
    public Booking getBookingById(int id) throws BookingNotFoundException {
        String query = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Booking(rs.getInt("id"), rs.getInt("room_id"), rs.getInt("user_id"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"));
                } else {
                    throw new BookingNotFoundException("Booking with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing booking data", e);
        }
    }
    @Override
    public void updateBooking(Booking booking) throws DataAccessException {
        String query = "UPDATE bookings SET room_id = ?, user_id = ?, start_time = ?, end_time = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, booking.getRoomId());
            pstmt.setInt(2, booking.getManagerId());
            pstmt.setTimestamp(3, new Timestamp(booking.getStartTime().getTime()));
            pstmt.setTimestamp(4, new Timestamp(booking.getEndTime().getTime()));
            pstmt.setInt(5, booking.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating booking", e);
        }
    }

    @Override
    public void cancelBooking(int id) throws BookingNotFoundException, DataAccessException {
        String query = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookingNotFoundException("Booking with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error canceling booking", e);
        }
    }

    @Override
    public List<Booking> getAllBookings() throws DataAccessException {
        String query = "SELECT * FROM bookings";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(new Booking(rs.getInt("id"), rs.getInt("room_id"), rs.getInt("user_id"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time")));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all bookings", e);
        }
        return bookings;
    }

}

