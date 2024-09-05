package com.example.ui.Login;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;
import com.example.model.OldCustomer;
import com.example.model.NewCustomer;
import com.example.ui.Login.CustomerRegistration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerLogin extends JFrame {

    private JTextField meterCodeField;
    private JPasswordField passwordField;

    public CustomerLogin() {
        setTitle("Customer Login");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Background Panel with GIF
        JPanel backgroundPanel = new JPanel() {
            private ImageIcon gifIcon = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\imgs\\cust.gif");

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 30, 500, 600); // Set bounds to match the frame size
        backgroundPanel.setBackground(Color.WHITE); // Ensure background is white
        add(backgroundPanel);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(500, 0, 500, 600);
        loginPanel.setBackground(Color.WHITE);

        // Login Title
        JLabel loginLabel = new JLabel("Customer Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setBounds(150, 100, 200, 30);
        loginPanel.add(loginLabel);

        // Meter Code Label
        JLabel meterCodeLabel = new JLabel("Meter Code");
        meterCodeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        meterCodeLabel.setBounds(140, 170, 100, 30);
        loginPanel.add(meterCodeLabel);

        JLabel usernameIcon = new JLabel(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\user.png")); // Icon for username
        usernameIcon.setBounds(100, 200, 30, 30);
        loginPanel.add(usernameIcon);

        // Meter Code Field
        meterCodeField = new JTextField();
        meterCodeField.setBounds(140, 200, 300, 30);
        meterCodeField.setBackground(Color.WHITE); // Ensure background is white
        loginPanel.add(meterCodeField);

        JLabel passwordIcon = new JLabel(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\pass.png")); // Icon for password
        passwordIcon.setBounds(100, 270, 30, 30);
        loginPanel.add(passwordIcon);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setBounds(140, 240, 100, 30);
        loginPanel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 270, 300, 30);
        passwordField.setBackground(Color.WHITE); // Ensure background is white
        loginPanel.add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBounds(150, 320, 250, 40);
        loginButton.setBackground(new Color(0, 51, 153));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBounds(150, 440, 250, 40);
        cancelButton.setBackground(new Color(204, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        loginPanel.add(cancelButton);

        add(loginPanel);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1000, 30);
        headerPanel.setBackground(Color.WHITE); // Ensure background is white
        add(headerPanel);

        // Close Button
        JButton closeButton = new JButton();
        closeButton.setBounds(970, 0, 30, 30);
        closeButton.setBackground(new Color(255, 255, 255));
        closeButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\close.png"));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set custom colors for the confirm dialog
                UIManager.put("OptionPane.background", Color.WHITE); // Dialog background color
                UIManager.put("Panel.background", Color.WHITE); // Panel background color
                UIManager.put("OptionPane.messageForeground", new Color(0, 51, 153)); // Message text color
                UIManager.put("Button.background", new Color(0, 51, 153)); // Button background color
                UIManager.put("Button.foreground", Color.WHITE); // Button text color
                UIManager.put("Button.focus", new Color(0, 51, 153)); // Button focus color

                // Show confirmation dialog with custom styling
                int response = JOptionPane.showConfirmDialog(null,
                        "<html><font color='#003399'>Do you really want to exit?</font></html>",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\exit.png")); // Custom icon

                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        headerPanel.add(closeButton);
        JButton backButton = new JButton();
        backButton.setBounds(0, 0, 30, 30); // Positioned on the left side
        backButton.setBackground(Color.WHITE); // Ensure background is white
        backButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\back.png")); // Icon for back arrow
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current login window

            }
        });
        headerPanel.add(backButton);
        // Registration Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBounds(150, 380, 250, 40);
        registerButton.setBackground(new Color(0, 153, 76));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        loginPanel.add(registerButton);

// Action Listener for Register Button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerRegistration().setVisible(true); // Open the registration page
            }
        });


        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String meterCode = meterCodeField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    if (authenticateCustomer(meterCode, password)) {
                        JOptionPane.showMessageDialog(null, "Customer Login Successful");
                        dispose();
                        // new CustomerDashboard().setVisible(true); // Load customer dashboard based on type
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Credentials");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private boolean authenticateCustomer(String meterCode, String password) throws SQLException {
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomer(meterCode);
        if (customer != null && customer.getPassword().equals(password)) {
            determineCustomerType(customer);
            return true;
        }
        return false;
    }

    private void determineCustomerType(Customer customer) {
        if (customer instanceof OldCustomer) {
            System.out.println("This is an old customer.");
        } else if (customer instanceof NewCustomer) {
            System.out.println("This is a new customer.");
        } else {
            System.out.println("Customer type is unknown.");
        }
    }

    public static void main(String[] args) {
        new CustomerLogin();
    }
}
