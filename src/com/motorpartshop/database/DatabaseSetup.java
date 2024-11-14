package com.motorpartshop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    public static void setupDatabase() {
        // Step 1: Connect to the default 'postgres' database to check and create 'mpss' if needed
        try (Connection connection = DriverManager.getConnection(URL + "postgres", USER, PASSWORD); Statement stmt = connection.createStatement()) {

            // Check if the 'mpss' database exists
            String checkDatabaseQuery = "SELECT 1 FROM pg_database WHERE datname = 'mpss'";
            var resultSet = stmt.executeQuery(checkDatabaseQuery);

            if (!resultSet.next()) {
                System.out.println("Database does not exist. Creating database...");
                // Create the 'mpss' database
                String createDatabaseQuery = "CREATE DATABASE mpss";
                stmt.executeUpdate(createDatabaseQuery);
                System.out.println("Database created successfully.");
            } else {
                System.out.println("Database already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return; // Exit if there was an error in creating/checking the database
        }

        // Step 2: Connect to the newly created (or already existing) 'mpss' database and create tables
        try (Connection dbConnection = DriverManager.getConnection(URL + "mpss", USER, PASSWORD)) {
            createTables(dbConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create 'parts' table
            String createPartsTable = "CREATE TABLE IF NOT EXISTS parts ("
                    + "part_id SERIAL PRIMARY KEY, "
                    + "part_name VARCHAR(100), "
                    + "description TEXT, "
                    + "stock INTEGER, "
                    + "reorder_threshold INTEGER, "
                    + "rack_location VARCHAR(50))";
            stmt.executeUpdate(createPartsTable);

            // Create 'vendors' table
            String createVendorsTable = "CREATE TABLE IF NOT EXISTS vendors ("
                    + "vendor_id SERIAL PRIMARY KEY, "
                    + "vendor_name VARCHAR(100), "
                    + "address TEXT, "
                    + "contact VARCHAR(100))";
            stmt.executeUpdate(createVendorsTable);

            // Create 'sales' table
            String createSalesTable = "CREATE TABLE IF NOT EXISTS sales ("
                    + "sales_id SERIAL PRIMARY KEY, "
                    + "part_id INTEGER REFERENCES parts(part_id), "
                    + "quantity INTEGER, "
                    + "total_amount DECIMAL(10, 2), "
                    + "sales_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(createSalesTable);

            System.out.println("Tables created successfully.");
        }
    }
}
