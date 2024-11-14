package com.motorpartshop.dao;

import com.motorpartshop.database.DatabaseConnection;
import com.motorpartshop.models.Part;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartDAO {

    public void addPart(Part part) throws SQLException {
        String query = "INSERT INTO parts (part_name, description, stock, reorder_threshold, rack_location) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            System.out.println("Part inserted");
            pstmt.setString(1, part.getName());
            pstmt.setString(2, part.getDescription());
            pstmt.setInt(3, part.getStock());
            pstmt.setInt(4, part.getReorderThreshold());
            pstmt.setString(5, part.getRackLocation());
            pstmt.executeUpdate();
        }
    }

    public List<Part> getAllParts() throws SQLException {
        List<Part> parts = new ArrayList<>();
        String query = "SELECT * FROM parts";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                parts.add(new Part(
                        rs.getInt("part_id"),
                        rs.getString("part_name"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getInt("reorder_threshold"),
                        rs.getString("rack_location")
                ));
            }
        }
        return parts;
    }

    public Part getPartById(int partId) throws SQLException {
        String query = "SELECT * FROM parts WHERE part_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, partId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Part(
                            rs.getInt("part_id"),
                            rs.getString("part_name"),
                            rs.getString("description"),
                            rs.getInt("stock"),
                            rs.getInt("reorder_threshold"),
                            rs.getString("rack_location")
                    );
                }
            }
        }
        return null; // Return null if part not found
    }

    // Update an existing part's details
    public void updatePart(Part part) throws SQLException {
        String query = "UPDATE parts SET part_name = ?, description = ?, stock = ?, reorder_threshold = ?, rack_location = ? WHERE part_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            System.out.println("Part Updated");
            pstmt.setString(1, part.getName());
            pstmt.setString(2, part.getDescription());
            pstmt.setInt(3, part.getStock());
            pstmt.setInt(4, part.getReorderThreshold());
            pstmt.setString(5, part.getRackLocation());
            pstmt.setInt(6, part.getId());
            pstmt.executeUpdate();
        }
    }

    // Delete a part by its ID
    public void deletePart(int partId) throws SQLException {
        String query = "DELETE FROM parts WHERE part_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            System.out.println("Part Deleted");
            pstmt.setInt(1, partId);
            pstmt.executeUpdate();
        }
    }
}
