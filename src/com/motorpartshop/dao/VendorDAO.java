package com.motorpartshop.dao;

import com.motorpartshop.database.DatabaseConnection;
import com.motorpartshop.models.Vendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendorDAO {

    // Method to add a new vendor to the database
    public void addVendor(Vendor vendor) throws SQLException {
        String query = "INSERT INTO vendors (vendor_name, address, contact) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vendor.getName());
            pstmt.setString(2, vendor.getAddress());
            pstmt.setString(3, vendor.getContact());
            pstmt.executeUpdate();
        }
    }

    // Method to get all vendors from the database
    public List<Vendor> getAllVendors() throws SQLException {
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT * FROM vendors";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Vendor vendor = new Vendor(
                        rs.getInt("vendor_id"),
                        rs.getString("vendor_name"),
                        rs.getString("address"),
                        rs.getString("contact")
                );
                vendors.add(vendor);
            }
        }
        return vendors;
    }

    // Method to get a vendor by ID
    public Vendor getVendorById(int id) throws SQLException {
        String query = "SELECT * FROM vendors WHERE vendor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Vendor(
                            rs.getInt("vendor_id"),
                            rs.getString("vendor_name"),
                            rs.getString("address"),
                            rs.getString("contact")
                    );
                }
            }
        }
        return null;
    }

    // Method to update a vendor record
    public void updateVendor(Vendor vendor) throws SQLException {
        String query = "UPDATE vendors SET vendor_name = ?, address = ?, contact = ? WHERE vendor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vendor.getName());
            pstmt.setString(2, vendor.getAddress());
            pstmt.setString(3, vendor.getContact());
            pstmt.setInt(4, vendor.getId());
            pstmt.executeUpdate();
        }
    }

    // Method to delete a vendor record
    public void deleteVendor(int vendorId) throws SQLException {
        String query = "DELETE FROM vendors WHERE vendor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, vendorId);
            pstmt.executeUpdate();
        }
    }
}
