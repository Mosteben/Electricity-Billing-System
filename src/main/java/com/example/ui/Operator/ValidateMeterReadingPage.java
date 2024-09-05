package com.example.ui.Operator;

import com.example.dao.BillDAO;
import com.example.model.Bill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ValidateMeterReadingPage extends JFrame {
    private String region;

    private JTextField meterCodeField;
    private JTextField currentReadingField;
    private JTextArea resultArea;

    public ValidateMeterReadingPage(String region) {
        this.region = region;
        setTitle("Validate Meter Reading");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true); // Set undecorated to true to customize the title bar

        // Create a panel for custom title bar
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        titleBar.setBackground(Color.WHITE);
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        // Load image from file system
        ImageIcon closeIcon = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\close.png");

        if (closeIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Error: Close icon not found.");
        }

        JButton closeButton = new JButton(closeIcon);
        closeButton.setPreferredSize(new Dimension(30,30));
        closeButton.setBackground(Color.GRAY);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });

        titleBar.add(closeButton);
        add(titleBar, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Meter Code label and field
        JLabel meterCodeLabel = new JLabel("Meter Code:");
        meterCodeLabel.setForeground(new Color(0, 51, 102)); // Dark blue color for text
        inputPanel.add(meterCodeLabel);

        meterCodeField = new JTextField();
        meterCodeField.setBackground(Color.WHITE);
        inputPanel.add(meterCodeField);

        // Current Reading label and field
        JLabel currentReadingLabel = new JLabel("Current Reading:");
        currentReadingLabel.setForeground(new Color(0, 51, 102)); // Dark blue color for text
        inputPanel.add(currentReadingLabel);

        currentReadingField = new JTextField();
        currentReadingField.setBackground(Color.WHITE);
        inputPanel.add(currentReadingField);

        // Validate Button
        JButton validateButton = new JButton("Validate Reading");
        validateButton.setBackground(new Color(0, 51, 153)); // Dark blue background
        validateButton.setForeground(Color.WHITE); // White text
        validateButton.addActionListener(e -> validateReading());
        inputPanel.add(validateButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBackground(Color.WHITE); // White background
        resultArea.setForeground(new Color(0, 51, 102)); // Dark blue text
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void validateReading() {
        String meterCode = meterCodeField.getText();
        String currentReadingText = currentReadingField.getText();
        if (meterCode.isEmpty() || currentReadingText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both meter code and current reading.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int currentReading = Integer.parseInt(currentReadingText);
            BillDAO billDAO = new BillDAO();
            Bill bill = billDAO.getBillByMeterCodeAndRegion(meterCode, region);

            if (bill != null) {
                double previousReading = bill.getPreviousReading();
                double actualReading = bill.getCurrentReading();

                if (currentReading < actualReading) {
                    resultArea.setText("Current reading is less than the recorded current reading.\n" +
                            "Previous Reading: " + previousReading + "\n" +
                            "Current Reading: " + actualReading);
                } else if (currentReading > actualReading) {
                    resultArea.setText("Current reading is more than the recorded current reading.\n" +
                            "Previous Reading: " + previousReading + "\n" +
                            "Current Reading: " + actualReading);
                } else {
                    resultArea.setText("The current reading matches the recorded current reading.\n" +
                            "Previous Reading: " + previousReading + "\n" +
                            "Current Reading: " + actualReading);
                }
            } else {
                resultArea.setText("No bill found for meter code: " + meterCode);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for current reading.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving bill information.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ValidateMeterReadingPage("luxor").setVisible(true));
    }
}
