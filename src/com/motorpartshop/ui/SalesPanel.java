// File: src/com/motorpartshop/ui/SalesPanel.java
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

public class SalesPanel extends JPanel {

    private JTable salesTable;
    private SalesDAO salesDAO;
    private PartDAO partDAO;
    private JTextField quantityField, totalAmountField;
    private JComboBox<String> partComboBox;
    private JButton addButton, refreshButton;
    private List<Part> partsList;  // Store parts list for reference

    public SalesPanel() {
        setLayout(new BorderLayout());
        salesDAO = new SalesDAO();
        partDAO = new PartDAO();

        // Panel for adding sales
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2));  // Adjusted to add Refresh button

        formPanel.add(new JLabel("Part Name:"));
        partComboBox = new JComboBox<>();
        formPanel.add(partComboBox);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Total Amount:"));
        totalAmountField = new JTextField();
        formPanel.add(totalAmountField);

        addButton = new JButton("Add Sale");
        refreshButton = new JButton("Refresh");  // New Refresh button
        formPanel.add(addButton);
        formPanel.add(refreshButton);  // Add Refresh button to the form panel

        add(formPanel, BorderLayout.NORTH);

        // Table to display sales records
        salesTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(salesTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Load parts into the combo box and display sales records in the table
        loadPartsComboBox();
        loadSalesTable();

        // Add action listener to add sale
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSale();
            }
        });

        // Add action listener to refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPartsComboBox();  // Refresh the parts dropdown
                loadSalesTable();     // Refresh the sales table
            }
        });
    }

    private void loadPartsComboBox() {
        try {
            partsList = partDAO.getAllParts();  // Fetch parts with names and stock
            partComboBox.removeAllItems();
            for (Part part : partsList) {
                partComboBox.addItem(part.getName());  // Display only the part name
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSalesTable() {
        try {
            List<Sales> sales = salesDAO.getAllSales();
            String[] columnNames = {"Sales ID", "Part Name", "Quantity", "Total Amount", "Sales Date"};
            Object[][] data = new Object[sales.size()][5];
            for (int i = 0; i < sales.size(); i++) {
                Sales sale = sales.get(i);
                data[i][0] = sale.getId();
                data[i][1] = sale.getPart().getName();
                data[i][2] = sale.getQuantity();
                data[i][3] = sale.getTotalAmount();
                data[i][4] = sale.getSalesDate();
            }
            salesTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void processSale() {
        try {
            int selectedIndex = partComboBox.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(SalesPanel.this, "Please select a part.");
                return;
            }

            // Retrieve selected part and sale details
            Part selectedPart = partsList.get(selectedIndex);  // Get part by index
            int quantity = Integer.parseInt(quantityField.getText());
            double totalAmount = Double.parseDouble(totalAmountField.getText());

            // Check if sufficient stock is available
            if (quantity > selectedPart.getStock()) {
                JOptionPane.showMessageDialog(SalesPanel.this, "Insufficient stock for selected part.");
                return;
            }

            // Record the sale
            Sales sale = new Sales(0, selectedPart, quantity, totalAmount, new java.util.Date());
            salesDAO.addSale(sale);

            // Update part stock after sale
            selectedPart.setStock(selectedPart.getStock() - quantity);  // Deduct quantity from stock
            partDAO.updatePart(selectedPart);  // Update the part in the database

            // Refresh the combo box and sales table
            loadPartsComboBox();
            loadSalesTable();

            // Clear input fields
            quantityField.setText("");
            totalAmountField.setText("");

            JOptionPane.showMessageDialog(SalesPanel.this, "Sale added successfully!");
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(SalesPanel.this, "Error adding sale: " + ex.getMessage());
        }
    }
}
