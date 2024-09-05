package com.example.ui.Admin.CRUD;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateCustomerPage extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField passwordField;
    private CustomerDAO customerDAO;
    private String meterCode;

    private final Color buttonColor = new Color(53, 150, 243);
    private final Color cancelButtonColor = new Color(255, 51, 51);
    private final Color buttonTextColor = Color.WHITE;
    private final Color backgroundColor = Color.WHITE;

    public UpdateCustomerPage(String meterCode, CustomerManagementPage parent) {
        this.meterCode = meterCode;
        this.customerDAO = new CustomerDAO();

        setTitle("Update Customer");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        // Set button icons and colors
        updateButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\update.png"));
        updateButton.setBackground(buttonColor);
        updateButton.setForeground(buttonTextColor);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);

        cancelButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\cancel.png"));
        cancelButton.setBackground(cancelButtonColor);
        cancelButton.setForeground(buttonTextColor);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(150, 25); // Adjust size as needed
        updateButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        // Set font
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        updateButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);

        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

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
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across both columns
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(cancelButton, gbc);

        loadCustomerData();

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Customer customer = customerDAO.getCustomer(meterCode);
                    if (customer != null) {
                        customer.setName(nameField.getText());
                        customer.setEmail(emailField.getText());
                        customer.setPassword(passwordField.getText());
                        customerDAO.updateCustomer(customer);
                        JOptionPane.showMessageDialog(UpdateCustomerPage.this, "Customer updated successfully.");
                        dispose();
                        parent.loadCustomerData();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(UpdateCustomerPage.this, "Error updating customer.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void loadCustomerData() {
        try {
            Customer customer = customerDAO.getCustomer(meterCode);
            if (customer != null) {
                nameField.setText(customer.getName());
                emailField.setText(customer.getEmail());
                passwordField.setText(customer.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customer data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
