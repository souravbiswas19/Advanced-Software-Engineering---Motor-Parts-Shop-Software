package com.motorpartshop.ui;

import com.motorpartshop.dao.VendorDAO;
import com.motorpartshop.models.Vendor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class VendorPanel extends JPanel {

    private JTable vendorsTable;
    private VendorDAO vendorDAO;
    private JTextField vendorNameField, addressField, contactField;

    public VendorPanel() {
        setLayout(new BorderLayout());
        vendorDAO = new VendorDAO();

        // Panel for adding/updating vendors
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));

        formPanel.add(new JLabel("Vendor Name:"));
        vendorNameField = new JTextField();
        formPanel.add(vendorNameField);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        formPanel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        formPanel.add(contactField);

        JButton addButton = new JButton("Add Vendor");
        formPanel.add(addButton);

        // Adding form panel to the top of the panel
        add(formPanel, BorderLayout.NORTH);

        // Table to display vendors
        vendorsTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(vendorsTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Load and display vendors in the table
        loadVendorsTable();

        // Add action listener to add vendor
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vendor vendor = new Vendor(0, vendorNameField.getText(), addressField.getText(), contactField.getText());
                    vendorDAO.addVendor(vendor);
                    loadVendorsTable();  // Refresh the table after adding
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(VendorPanel.this, "Error adding vendor: " + ex.getMessage());
                }
            }
        });
    }

    private void loadVendorsTable() {
        try {
            List<Vendor> vendors = vendorDAO.getAllVendors();
            String[] columnNames = {"Vendor ID", "Vendor Name", "Address", "Contact"};
            Object[][] data = new Object[vendors.size()][4];
            for (int i = 0; i < vendors.size(); i++) {
                Vendor vendor = vendors.get(i);
                data[i][0] = vendor.getId();
                data[i][1] = vendor.getName();
                data[i][2] = vendor.getAddress();
                data[i][3] = vendor.getContact();
            }
            vendorsTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
