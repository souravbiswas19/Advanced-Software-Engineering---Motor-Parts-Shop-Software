package com.motorpartshop;

import com.motorpartshop.database.DatabaseSetup;
import com.motorpartshop.ui.MainFrame;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Initialize the database
        DatabaseSetup.setupDatabase();

        // Start the application
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
