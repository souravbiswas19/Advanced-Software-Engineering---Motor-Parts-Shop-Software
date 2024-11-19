// File: src/com/motorpartshop/ui/ReportPanel.java
package com.motorpartshop.ui;

import com.motorpartshop.dao.PartDAO;
import com.motorpartshop.dao.SalesDAO;
import com.motorpartshop.models.Part;
import com.motorpartshop.models.Sales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ReportPanel extends JPanel {

    private JTextArea reportArea;
    private SalesDAO salesDAO;
    private PartDAO partDAO;

    public ReportPanel() {
        // Initialize the DAOs to avoid NullPointerException
        salesDAO = new SalesDAO();
        partDAO = new PartDAO();

        setLayout(new BorderLayout());

        // Text area to display the report
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for report generation options
        JPanel buttonPanel = new JPanel();
        JButton generateSalesReportButton = new JButton("Generate Sales Report");
        JButton generateInventoryReportButton = new JButton("Generate Inventory Report");

        buttonPanel.add(generateSalesReportButton);
        buttonPanel.add(generateInventoryReportButton);

        add(buttonPanel, BorderLayout.NORTH);

        // Action listeners for buttons
        generateSalesReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSalesReport();
            }
        });

        generateInventoryReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInventoryReport();
            }
        });
    }

    private void generateSalesReport() {
        try {
            // Fetch sales data from the database
            List<Sales> salesList = salesDAO.getAllSales();

            // Initialize a StringBuilder to format the report
            StringBuilder report = new StringBuilder();
            report.append("Sales Report:\n\n");
            report.append(String.format("%-10s %-20s %-10s %-15s %-20s\n", "Sales ID", "Part Name", "Quantity", "Total Amount", "Sales Date"));

            // Initialize a variable to calculate the total sales amount
            double totalSalesAmount = 0;

            // Loop through the sales list and add each entry to the report
            for (Sales sale : salesList) {
                report.append(String.format("%-10d %-20s %-10d %-15.2f %-20s\n",
                        sale.getId(),
                        sale.getPart().getName(),
                        sale.getQuantity(),
                        sale.getTotalAmount(),
                        sale.getSalesDate().toString()));

                // Accumulate the total amount
                totalSalesAmount += sale.getTotalAmount();
            }

            // Add the total sales amount to the report
            report.append("\n");
            report.append(String.format("Total Sales Amount: %.2f\n", totalSalesAmount));

            // Set the report text in the JTextArea
            reportArea.setText(report.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            reportArea.setText("Error fetching sales data: " + e.getMessage());
        }
    }

    private void generateInventoryReport() {
        try {
            // Fetch parts data from the database
            List<Part> partList = partDAO.getAllParts();

            // Initialize a StringBuilder to format the report
            StringBuilder report = new StringBuilder();
            report.append("Inventory Report:\n\n");
            report.append(String.format("%-10s %-20s %-20s %-10s %-20s %-15s\n", "Part ID", "Part Name", "Description", "Stock", "Reorder Threshold", "Rack Location"));

            // Loop through the parts list and add each entry to the report
            for (Part part : partList) {
                report.append(String.format("%-10d %-20s %-20s %-10d %-20d %-15s\n",
                        part.getId(),
                        part.getName(),
                        part.getDescription(),
                        part.getStock(),
                        part.getReorderThreshold(),
                        part.getRackLocation()));
            }

            // Set the report text in the JTextArea
            reportArea.setText(report.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            reportArea.setText("Error fetching inventory data: " + e.getMessage());
        }
    }
}
