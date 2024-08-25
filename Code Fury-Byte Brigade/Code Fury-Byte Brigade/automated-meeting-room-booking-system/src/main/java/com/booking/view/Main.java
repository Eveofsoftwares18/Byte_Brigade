package com.booking.view;

import com.booking.beans.Booking;
import com.booking.beans.MeetingRoom;
import com.booking.beans.User;
import com.booking.exceptions.*;
import com.booking.factory.ServiceFactory;
import com.booking.service.BookingService;
import com.booking.service.CreditService;
import com.booking.service.MeetingRoomService;
import com.booking.service.UserService;
import com.booking.util.SchemaInitializer;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = ServiceFactory.getUserService();
    private static final MeetingRoomService meetingRoomService = ServiceFactory.getMeetingRoomService();
    private static final BookingService bookingService = ServiceFactory.getBookingService();
    private static final CreditService creditService = ServiceFactory.getCreditService();
    private static User currentUser;

    public static void main(String[] args) {
        SchemaInitializer.executeSchemaScript();
        creditService.startCreditResetScheduler();

        while (true) {
            System.out.println("Welcome to the Meeting Room Booking System");
            System.out.println("1. Login/Register");
            System.out.println("2. Exit");

            int choice = getUserChoice();

            if (choice == 1) {
                if (loginOrRegister()) {
                    userMenu();
                }
            } else {
                System.out.println("Exiting the system.");
                return;
            }
        }
    }

    private static boolean loginOrRegister() {
        System.out.print("Enter your email: ");
        String email = scanner.next();
        System.out.print("Enter your role (Admin/Manager/Member): ");
        String roleStr = scanner.next();

        try {
            currentUser = userService.getUserByEmail(email);
            if (currentUser != null && currentUser.getRole().equalsIgnoreCase(roleStr)) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("User not found or role mismatch. Registering new user...");
                registerUser(email, roleStr);
                return true;
            }
        } catch (UserNotFoundException e) {
            System.out.println("User not found. Registering new user...");
            registerUser(email, roleStr);
            return true;
        } catch (DataAccessException e) {
            System.out.println("Error during login or registration: " + e.getMessage());
            return false;
        }
    }

    private static void registerUser(String email, String role) {
        System.out.print("Enter your name: ");
        String name = scanner.next();
        System.out.print("Enter your phone number: ");
        String phone = scanner.next();

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setRole(role);
        newUser.setCredits("Manager".equalsIgnoreCase(role) ? 2000 : 0);

        try {
            userService.createUser(newUser);
            System.out.println("User registered successfully.");
            currentUser = newUser;
        } catch (DataAccessException e) {
            System.out.println("Error during user registration: " + e.getMessage());
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("Welcome " + currentUser.getRole() + "!");
            System.out.println("1. View Meeting Rooms");
            System.out.println("2. Book a Meeting Room");
            System.out.println("3. View Meeting Room Schedule");
            if ("Admin".equalsIgnoreCase(currentUser.getRole())) {
                System.out.println("4. Create Meeting Room");
            }
            System.out.println("5. Exit");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    viewMeetingRooms();
                    break;
                case 2:
                    bookMeetingRoom();
                    break;
                case 3:
                    viewMeetingRoomSchedule();
                    break;

                case 4:
                    if ("Admin".equalsIgnoreCase(currentUser.getRole())) {
                        createMeetingRoom();
                    } else {
                        System.out.println("You do not have permission to create meeting rooms.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getUserChoice() {
        int choice = -1;
        while (choice < 1 || choice > 6) {
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.next(); // Clear the invalid input
            }
        }
        return choice;
    }

    private static void viewMeetingRooms() {
        try {
            List<MeetingRoom> rooms = meetingRoomService.getAllMeetingRooms();
            if (rooms.isEmpty()) {
                System.out.println("No meeting rooms available.");
            } else {
                System.out.println("Meeting Rooms:");
                for (MeetingRoom room : rooms) {
                    System.out.println(room);
                }
            }
        } catch (DataAccessException e) {
            System.out.println("Error retrieving meeting rooms: " + e.getMessage());
        }
    }

    private static void bookMeetingRoom() {
        try {
            System.out.print("Enter Room ID: ");
            int roomId = scanner.nextInt();

            System.out.print("Enter Start Time (yyyy-mm-dd hh:mm:ss): ");
            String startTimeStr = scanner.next();
            Timestamp startTime = Timestamp.valueOf(startTimeStr);

            System.out.print("Enter End Time (yyyy-mm-dd hh:mm:ss): ");
            String endTimeStr = scanner.next();
            Timestamp endTime = Timestamp.valueOf(endTimeStr);

            Booking booking = new Booking(roomId, currentUser.getId(), startTime, endTime);

            bookingService.addBooking(booking);
            System.out.println("Room booked successfully!");

        } catch (RoomAlreadyBookedException e) {
            System.out.println("Error: Room already booked.");
        } catch (UserNotFoundException e) {
            System.out.println("Error: User not found.");
        } catch (InsufficientCreditsException e) {
            System.out.println("Error: Insufficient credits.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void viewMeetingRoomSchedule() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            if (bookings.isEmpty()) {
                System.out.println("No bookings available.");
            } else {
                System.out.println("Meeting Room Schedule:");
                for (Booking booking : bookings) {
                    System.out.println(booking);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }
    }



    private static void createMeetingRoom() {
        try {
            System.out.print("Enter Room Name: ");
            String name = scanner.next();

            System.out.print("Enter Seating Capacity: ");
            int seatingCapacity = scanner.nextInt();

            System.out.print("Has Projector (true/false): ");
            boolean projector = scanner.nextBoolean();

            System.out.print("Has WiFi (true/false): ");
            boolean wifi = scanner.nextBoolean();

            System.out.println("Has Conference Call (true/false):");
            boolean conferenceCall = scanner.nextBoolean();
            System.out.println("Has White board (true/false):");
            boolean whiteboard = scanner.nextBoolean();
            System.out.println("Has Water Dispenser (true/false):");
            boolean waterDispenser = scanner.nextBoolean();
            System.out.println("Has tv (true/false):");
            boolean tv = scanner.nextBoolean();
            System.out.println("Has Coffee Machine (true/false):");
            boolean coffeeMachine = scanner.nextBoolean();


            // Continue with other amenities...

            MeetingRoom room = new MeetingRoom();
            room.setName(name);
            room.setSeatingCapacity(seatingCapacity);
            room.setProjector(projector);
            room.setWifi(wifi);
            room.setCoffeeMachine(coffeeMachine);
            room.setConferenceCall(conferenceCall);
            room.setWaterDispenser(waterDispenser);
            room.setWhiteboard(whiteboard);
            room.setTv(tv);
            // Set other amenities...

            meetingRoomService.addMeetingRoom(room);
            System.out.println("Meeting room created successfully!");

        } catch (DataAccessException e) {
            System.out.println("Error creating meeting room: " + e.getMessage());
        }
    }
}
