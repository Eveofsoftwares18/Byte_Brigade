package com.booking.service;

import com.booking.beans.User;
import com.booking.dao.UserDAO;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CreditService {

    private UserDAO userDAO;

    public CreditService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Resets credits for all managers to 2000
    public void resetCredits() {
        try {
            List<User> managers = getAllManagers();
            for (User user : managers) {
                if (user.getRole().equals("Manager")) {
                    userDAO.updateCredits(user.getId(), -user.getCredits() + 2000);
                }
            }
            System.out.println("Credits have been reset to 2000 for all managers.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch all users with the role "Manager"
    private List<User> getAllManagers() {
        // Implement logic to fetch all managers from the database
        // This is a placeholder. Replace it with actual implementation.
        List<User> managers = new ArrayList<>();
        // Assume a method in UserDAO to fetch all users
        try {
            // Example method to fetch all users
            // In a real scenario, implement UserDAO method and call it here
            for (User user : userDAO.getAllUsers()) {
                if (user.getRole().equals("Manager")) {
                    managers.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return managers;
    }

    // Starts the scheduler to reset credits every Monday at 6 AM
    public void startCreditResetScheduler() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                resetCredits();
            }
        }, getInitialDelay(), TimeUnit.DAYS.toMillis(7)); // 1 week period
    }

    // Calculates the initial delay until the next Monday at 6 AM
    private long getInitialDelay() {
        Calendar now = Calendar.getInstance();
        Calendar nextMonday = (Calendar) now.clone();
        nextMonday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        nextMonday.set(Calendar.HOUR_OF_DAY, 6);
        nextMonday.set(Calendar.MINUTE, 0);
        nextMonday.set(Calendar.SECOND, 0);
        nextMonday.set(Calendar.MILLISECOND, 0);

        if (now.after(nextMonday)) {
            nextMonday.add(Calendar.WEEK_OF_YEAR, 1);
        }

        return nextMonday.getTimeInMillis() - now.getTimeInMillis();
    }
}

