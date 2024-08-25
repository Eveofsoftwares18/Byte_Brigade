package com.booking.service;

import com.booking.beans.User;
import com.booking.dao.UserDAO;
import com.booking.exceptions.UserNotFoundException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        User user = userDAO.getUserById(id);
        if (user == null)
            throw new UserNotFoundException("User with ID " + id + " not found.");
        return user;
    }

    @Override
    public void updateUserCredits(int userId, int amount) throws UserNotFoundException {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        int newCreditBalance = user.getCredits() + amount;
        userDAO.updateCredits(userId, newCreditBalance);
    }
    @Override
    public void createUser(User user) {
                userDAO.createUser(user);
    }
    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found.");
        }
        return user;
    }
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}


