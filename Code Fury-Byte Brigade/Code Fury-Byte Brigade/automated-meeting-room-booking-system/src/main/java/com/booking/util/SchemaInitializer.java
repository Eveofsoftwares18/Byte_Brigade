package com.booking.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    private static final String URL = "jdbc:mysql://localhost:3306/meeting_room_booking_system?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Navya@123#@";

    public static void executeSchemaScript() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new InputStreamReader(
                     SchemaInitializer.class.getResourceAsStream("/schema.sql")))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            statement.execute(sql.toString());
            System.out.println("Database schema initialized successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

