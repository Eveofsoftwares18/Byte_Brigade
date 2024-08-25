package com.booking.service;

import com.booking.beans.User;
import com.booking.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    User getUserById(int id) throws UserNotFoundException;
    void updateUserCredits(int userId, int amount) throws UserNotFoundException;
    void createUser(User user);
    List<User> getAllUsers();
    User getUserByEmail(String email) throws UserNotFoundException;

}

