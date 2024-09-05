package com.example.ui.Login;

import com.example.dao.CustomerDAO;
import com.example.model.NewCustomer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerRegistration extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField propertyDetailsField;
    private JTextField contractAttachmentField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private CustomerDAO customerDAO;

    private final Color buttonColor = new Color(53, 150, 243);
    private final Color cancelButtonColor = new Color(255, 51, 51);
    private final Color buttonTextColor = Color.WHITE;
    private final Color backgroundColor = Color.WHITE;

    public CustomerRegistration() {
        this.customerDAO = new CustomerDAO();

        setTitle("Register New Customer");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel propertyDetailsLabel = new JLabel("Property Details:");
        propertyDetailsField = new JTextField();
        JLabel contractAttachmentLabel = new JLabel("Contract Attachment:");
        contractAttachmentField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(buttonColor);
        registerButton.setForeground(buttonTextColor);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(cancelButtonColor);
        cancelButton.setForeground(buttonTextColor);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);

        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        propertyDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contractAttachmentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        propertyDetailsField.setFont(new Font("Arial", Font.PLAIN, 14));
        contractAttachmentField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(propertyDetailsLabel, gbc);
        gbc.gridx = 1;
        add(propertyDetailsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(contractAttachmentLabel, gbc);
        gbc.gridx = 1;
        add(contractAttachmentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(registerButton, gbc);
        gbc.gridx = 1;
        add(cancelButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String propertyDetails = propertyDetailsField.getText().trim();
                    String contractAttachment = contractAttachmentField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();
                    String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

                    if (name.isEmpty() || email.isEmpty() || propertyDetails.isEmpty() ||
                            contractAttachment.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(CustomerRegistration.this,
                                "All fields must be filled.", "Validation Error",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(CustomerRegistration.this,
                                "Passwords do not match.", "Validation Error",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String meterCode = generateMeterCode();

                    NewCustomer customer = new NewCustomer(meterCode, name, email, propertyDetails, contractAttachment, password);
                    customerDAO.addCustomer(customer);
                    JOptionPane.showMessageDialog(CustomerRegistration.this, "Customer registered successfully.");
                    dispose();
                    // Optionally, add code to refresh customer data if needed.
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CustomerRegistration.this, "Error registering customer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private String generateMeterCode() throws SQLException {
        int customerCount = customerDAO.getCustomerCount();
        return "MC00" + (customerCount + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerRegistration().setVisible(true));
    }
}
