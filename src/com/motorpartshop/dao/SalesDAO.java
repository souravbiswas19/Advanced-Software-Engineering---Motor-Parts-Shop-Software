package com.motorpartshop.dao;

import com.motorpartshop.database.DatabaseConnection;
import com.motorpartshop.models.Part;
import com.motorpartshop.models.Sales;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    // Method to add a sale to the database
    public void addSale(Sales sale) throws SQLException {
        String query = "INSERT INTO sales (part_id, quantity, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, sale.getPart().getId());
            pstmt.setInt(2, sale.getQuantity());
            pstmt.setDouble(3, sale.getTotalAmount());
            pstmt.executeUpdate();
        }
    }

    // Method to fetch all sales from the database
    public List<Sales> getAllSales() throws SQLException {
        List<Sales> sales = new ArrayList<>();
        String query = "SELECT * FROM sales";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Get part from the PartDAO to retrieve part details
                Part part = new PartDAO().getPartById(rs.getInt("part_id"));
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        part,
                        rs.getInt("quantity"),
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("sales_date")
                );
                sales.add(sale);
            }
        }
        return sales;
    }

    // Method to get a specific sale by its ID
    public Sales getSaleById(int id) throws SQLException {
        String query = "SELECT * FROM sales WHERE sales_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Part part = new PartDAO().getPartById(rs.getInt("part_id"));
                    return new Sales(
                            rs.getInt("sales_id"),
                            part,
                            rs.getInt("quantity"),
                            rs.getDouble("total_amount"),
                            rs.getTimestamp("sales_date")
                    );
                }
            }
        }
        return null;
    }

    // Method to update a sale
    public void updateSale(Sales sale) throws SQLException {
        String query = "UPDATE sales SET part_id = ?, quantity = ?, total_amount = ? WHERE sales_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, sale.getPart().getId());
            pstmt.setInt(2, sale.getQuantity());
            pstmt.setDouble(3, sale.getTotalAmount());
            pstmt.setInt(4, sale.getId());
            pstmt.executeUpdate();
        }
    }

    // Method to delete a sale
    public void deleteSale(int saleId) throws SQLException {
        String query = "DELETE FROM sales WHERE sales_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, saleId);
            pstmt.executeUpdate();
        }
    }
}
