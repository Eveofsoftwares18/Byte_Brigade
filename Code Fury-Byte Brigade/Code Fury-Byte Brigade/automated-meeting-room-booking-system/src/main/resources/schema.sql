CREATE DATABASE IF NOT EXISTS meeting_room_booking_system;
USE meeting_room_booking_system;



CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    role ENUM('Admin', 'Manager', 'Member') NOT NULL,
    credits INT DEFAULT 0
);
CREATE TABLE meeting_rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    seating_capacity INT NOT NULL,
    projector BOOLEAN DEFAULT FALSE,
    wifi BOOLEAN DEFAULT FALSE,
    conference_call BOOLEAN DEFAULT FALSE,
    whiteboard BOOLEAN DEFAULT FALSE,
    water_dispenser BOOLEAN DEFAULT FALSE,
    tv BOOLEAN DEFAULT FALSE,
    coffee_machine BOOLEAN DEFAULT FALSE
);
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT NOT NULL,
    user_id INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    FOREIGN KEY (room_id) REFERENCES meeting_rooms(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
