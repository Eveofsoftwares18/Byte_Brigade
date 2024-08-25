package com.booking.service;
import com.booking.beans.User;
import com.booking.dao.UserDAO;
import com.booking.exceptions.UserNotFoundException;
import com.booking.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void testGetUserByEmail_UserExists() throws UserNotFoundException {
        User mockUser = new User(1, "John Doe", "john@example.com", "1234567890", "Admin", 0);
        when(userDAO.getUserByEmail("john@example.com")).thenReturn(mockUser);

        User user = userService.getUserByEmail("john@example.com");

        assertNotNull(user);
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetUserByEmail_UserNotFound() {
        try {
            when(userDAO.getUserByEmail("nonexistent@example.com"))
                    .thenThrow(new UserNotFoundException("User not found"));
        } catch (UserNotFoundException e) {
            // Handle the exception here if necessary or leave it empty
        }

    }

    @Test
    public void testCreateUser_Success() throws Exception {
        User newUser = new User(0, "Jane Doe", "jane@example.com", "0987654321", "Manager", 2000);
        userService.createUser(newUser);
        verify(userDAO, times(1)).createUser(newUser);
    }
}

