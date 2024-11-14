// File: src/com/motorpartshop/ui/InventoryPanel.java
package com.motorpartshop.ui;

import com.motorpartshop.dao.PartDAO;
import com.motorpartshop.models.Part;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class InventoryPanel extends JPanel {

    private JTable partsTable;
    private PartDAO partDAO;
    private JTextField partNameField, descriptionField, stockField, reorderThresholdField, rackLocationField;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private int selectedPartId = -1;  // Used to store the ID of the selected part for updating

    public InventoryPanel() {
        setLayout(new BorderLayout());
        partDAO = new PartDAO();

        // Panel for adding/updating parts
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2));

        formPanel.add(new JLabel("Part Name:"));
        partNameField = new JTextField();
        formPanel.add(partNameField);

        formPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Stock:"));
        stockField = new JTextField();
        formPanel.add(stockField);

        formPanel.add(new JLabel("Reorder Threshold:"));
        reorderThresholdField = new JTextField();
        formPanel.add(reorderThresholdField);

        formPanel.add(new JLabel("Rack Location:"));
        rackLocationField = new JTextField();
        formPanel.add(rackLocationField);

        addButton = new JButton("Add Part");
        updateButton = new JButton("Update Part");
        deleteButton = new JButton("Delete Part");
        refreshButton = new JButton("Refresh");  // New Refresh button

        formPanel.add(addButton);
        formPanel.add(updateButton);
        formPanel.add(deleteButton);
        formPanel.add(refreshButton);  // Add Refresh button to form panel

        // Adding form panel to the top of the panel
        add(formPanel, BorderLayout.NORTH);

        // Table to display parts
        partsTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(partsTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Load and display parts in the table
        loadPartsTable();

        // Action listener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Part part = new Part(0, partNameField.getText(), descriptionField.getText(),
                            Integer.parseInt(stockField.getText()), Integer.parseInt(reorderThresholdField.getText()), rackLocationField.getText());
                    partDAO.addPart(part);
                    loadPartsTable();  // Refresh the table after adding
                    clearFormFields();
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InventoryPanel.this, "Error adding part: " + ex.getMessage());
                }
            }
        });

        // Action listener for Update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPartId != -1) {  // Check if a part is selected
                    try {
                        Part part = new Part(selectedPartId, partNameField.getText(), descriptionField.getText(),
                                Integer.parseInt(stockField.getText()), Integer.parseInt(reorderThresholdField.getText()), rackLocationField.getText());
                        partDAO.updatePart(part);
                        loadPartsTable();  // Refresh the table after updating
                        clearFormFields();
                    } catch (SQLException | NumberFormatException ex) {
                        JOptionPane.showMessageDialog(InventoryPanel.this, "Error updating part: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(InventoryPanel.this, "Please select a part to update.");
                }
            }
        });

        // Action listener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPartId != -1) {  // Check if a part is selected
                    int confirmation = JOptionPane.showConfirmDialog(InventoryPanel.this, "Are you sure you want to delete this part?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        try {
                            partDAO.deletePart(selectedPartId);
                            loadPartsTable();  // Refresh the table after deletion
                            clearFormFields();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(InventoryPanel.this, "Error deleting part: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(InventoryPanel.this, "Please select a part to delete.");
                }
            }
        });

        // Action listener for Refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPartsTable();  // Simply reloads the table to refresh the data
            }
        });

        // Table selection listener to load data into form fields for updating
        partsTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = partsTable.getSelectedRow();
            if (selectedRow != -1) {
                selectedPartId = (int) partsTable.getValueAt(selectedRow, 0);  // Get part_id from table
                partNameField.setText(partsTable.getValueAt(selectedRow, 1).toString());
                descriptionField.setText(partsTable.getValueAt(selectedRow, 2).toString());
                stockField.setText(partsTable.getValueAt(selectedRow, 3).toString());
                reorderThresholdField.setText(partsTable.getValueAt(selectedRow, 4).toString());
                rackLocationField.setText(partsTable.getValueAt(selectedRow, 5).toString());
            }
        });
    }

    // Method to load parts into the table
    private void loadPartsTable() {
        try {
            List<Part> parts = partDAO.getAllParts();
            String[] columnNames = {"ID", "Part Name", "Description", "Stock", "Reorder Threshold", "Rack Location"};
            Object[][] data = new Object[parts.size()][6];
            for (int i = 0; i < parts.size(); i++) {
                Part part = parts.get(i);
                data[i][0] = part.getId();
                data[i][1] = part.getName();
                data[i][2] = part.getDescription();
                data[i][3] = part.getStock();
                data[i][4] = part.getReorderThreshold();
                data[i][5] = part.getRackLocation();
            }
            partsTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to clear form fields after add/update/delete actions
    private void clearFormFields() {
        partNameField.setText("");
        descriptionField.setText("");
        stockField.setText("");
        reorderThresholdField.setText("");
        rackLocationField.setText("");
        selectedPartId = -1;  // Reset selected part ID after clearing
    }
}
