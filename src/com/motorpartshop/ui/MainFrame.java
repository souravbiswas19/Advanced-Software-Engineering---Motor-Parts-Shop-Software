package com.motorpartshop.ui;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Set the main window properties
        setTitle("Motor Part Shop Software");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create the tabbed pane for different functionalities
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add individual panels to the tabbed pane
        tabbedPane.addTab("Inventory", new InventoryPanel());
        tabbedPane.addTab("Sales", new SalesPanel());
        tabbedPane.addTab("Vendors", new VendorPanel());
        tabbedPane.addTab("Reports", new ReportPanel());

        // Add the tabbed pane to the frame
        add(tabbedPane, BorderLayout.CENTER);

        // Add a title label at the top
        JLabel titleLabel = new JLabel("Motor Part Shop Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);

        // Make the frame visible
        setVisible(true);
    }

    // public void main(String[] args) {
    //     // Run the application on the Event Dispatch Thread for thread-safety
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             new MainFrame();
    //         }
    //     });
    // }
}
