package com.booking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.booking.beans.User;
import com.booking.exceptions.DataAccessException;
import com.booking.exceptions.UserNotFoundException;
import com.booking.util.DBUtil;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setRole(rs.getString("role"));
                    user.setCredits(rs.getInt("credits"));
                    return user;
                } else {
                    throw new UserNotFoundException("User with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error occurred.");
        }
    }

    @Override
    public void updateCredits(int userId, int cost) throws UserNotFoundException {
        String sql = "UPDATE users SET credits = credits - ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cost);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error occurred.");
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email, phone, role, credits FROM users";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setCredits(rs.getInt("credits"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions
        }

        return users;
    }
    @Override
    public void createUser(User user) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (name, email, phone, role) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions, perhaps rethrow as a custom exception
        }
    }
    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role"));
                user.setCredits(resultSet.getInt("credits"));
                return user;
            } else {
                throw new UserNotFoundException("User with email " + email + " not found.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving user by email", e);
        }
    }
}

