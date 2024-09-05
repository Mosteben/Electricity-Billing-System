package com.example.ui.Admin.CRUD;

import com.example.dao.CustomerDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MeterManagementFrame extends JFrame {
    private String region;
    private boolean isMeterStopped = false;
    private final Color buttonColor = new Color(53, 150, 243);
    private final Color cancelButtonColor = new Color(255, 51, 51);
    private final Color buttonTextColor = Color.WHITE;
    private final Color backgroundColor = Color.WHITE;

    public MeterManagementFrame(String region) {
        this.region=region;
        setTitle("Meter Management");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(true); // Set undecorated to true to customize the title bar

        // Create a panel for custom title bar
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        titleBar.setBackground(Color.WHITE);
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        // Create close button
        JButton closeButton = new JButton(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\close.png")); // Add your close icon path here
        closeButton.setPreferredSize(new Dimension(30, 30));
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

        getContentPane().setBackground(backgroundColor);

        GridBagLayout layout = new GridBagLayout();
        JPanel contentPanel = new JPanel(layout);
        add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel meterCodeLabel = new JLabel("Enter Meter Code:");
        JTextField meterCodeField = new JTextField();

        JButton stopButton = new JButton("Stop Meter");
        stopButton.setBackground(buttonColor);
        stopButton.setForeground(buttonTextColor);
        stopButton.setBorderPainted(false);
        stopButton.setFocusPainted(false);

        JButton cancelSubscriptionButton = new JButton("Cancel Subscription");
        cancelSubscriptionButton.setBackground(cancelButtonColor);
        cancelSubscriptionButton.setForeground(buttonTextColor);
        cancelSubscriptionButton.setBorderPainted(false);
        cancelSubscriptionButton.setFocusPainted(false);

        meterCodeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        meterCodeField.setFont(new Font("Arial", Font.PLAIN, 14));
        stopButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelSubscriptionButton.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(meterCodeLabel, gbc);

        gbc.gridx = 1;
        contentPanel.add(meterCodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(stopButton, gbc);

        gbc.gridx = 1;
        contentPanel.add(cancelSubscriptionButton, gbc);

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String meterCode = meterCodeField.getText().trim();
                    if (meterCode.isEmpty()) {
                        JOptionPane.showMessageDialog(MeterManagementFrame.this, "Meter code field is empty.");
                        return;
                    }

                    CustomerDAO dao = new CustomerDAO();
                    if (!dao.meterExists(meterCode,region)) {
                        JOptionPane.showMessageDialog(MeterManagementFrame.this, "Meter not found.");
                        return;
                    }

                    if (isMeterStopped) {
                        dao.resumeMeter(meterCode); // Implement this method in CustomerDAO
                        stopButton.setText("Stop Meter");
                        JOptionPane.showMessageDialog(MeterManagementFrame.this, "Meter resumed successfully.");
                    } else {
                        dao.stopMeter(meterCode);
                        stopButton.setText("Resume Meter");
                        JOptionPane.showMessageDialog(MeterManagementFrame.this, "Meter stopped successfully.");
                    }

                    isMeterStopped = !isMeterStopped; // Toggle the state

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MeterManagementFrame.this, "Error stopping meter.");
                }
            }
        });

        cancelSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String meterCode = meterCodeField.getText().trim();
                    if (meterCode.isEmpty()) {
                        JOptionPane.showMessageDialog(MeterManagementFrame.this, "Meter code field is empty.");
                        return;
                    }

                    CustomerDAO dao = new CustomerDAO();
                    if (!dao.meterExists(meterCode,region)) { // Assume you have a method to check if the meter exists
                        JOptionPane.showMessageDialog(MeterManagementFrame.this, "Meter not found.");
                        return;
                    }

                    dao.cancelSubscription(meterCode);
                    JOptionPane.showMessageDialog(MeterManagementFrame.this, "Subscription cancelled successfully.");
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MeterManagementFrame.this, "Error cancelling subscription.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MeterManagementFrame("Luxor"));
    }
}
