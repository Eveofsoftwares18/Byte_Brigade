package com.booking.dao;

import com.booking.exceptions.UserNotFoundException;
import com.booking.beans.User;

import java.util.List;

public interface UserDAO {
    User getUserById(int id) throws UserNotFoundException;
    void updateCredits(int userId, int cost) throws UserNotFoundException;
    List<User> getAllUsers();
    void createUser(User user);
    User getUserByEmail(String email) throws UserNotFoundException;

}

