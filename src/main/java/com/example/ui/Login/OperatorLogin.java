package com.example.ui.Login;
import com.example.ui.LoginScreen;

import com.example.dao.UserDAO;
import com.example.model.Operator;
import com.example.model.User;
import com.example.ui.Operator.OperatorDashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class OperatorLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public OperatorLogin() {
        setTitle("Operator Login");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Set white background
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Draw the image
                ImageIcon icon = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\imgs\\operlog.png"); // Update path as needed
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setBounds(0, 0, 500, 600);
        add(backgroundPanel);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(500, 0, 500, 600);
        loginPanel.setBackground(Color.WHITE);

        // Login Title
        JLabel loginLabel = new JLabel("Operator Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setBounds(150, 100, 200, 30);
        loginPanel.add(loginLabel);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setBounds(140, 170, 100, 30);
        loginPanel.add(usernameLabel);

        // Username Icon
        JLabel usernameIcon = new JLabel(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\user.png")); // Update path as needed
        usernameIcon.setBounds(100, 200, 30, 30);
        loginPanel.add(usernameIcon);

        // Username Field
        usernameField = new JTextField();
        usernameField.setBounds(140, 200, 300, 30);
        usernameField.setBackground(Color.WHITE);
        loginPanel.add(usernameField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setBounds(140, 240, 100, 30);
        loginPanel.add(passwordLabel);

        // Password Icon
        JLabel passwordIcon = new JLabel(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\pass.png")); // Update path as needed
        passwordIcon.setBounds(100, 270, 30, 30);
        loginPanel.add(passwordIcon);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 270, 300, 30);
        passwordField.setBackground(Color.WHITE);
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
        cancelButton.setBounds(150, 380, 250, 40); // Positioned below login button
        cancelButton.setBackground(new Color(204, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        loginPanel.add(cancelButton);

        add(loginPanel);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, getWidth(), 30); // Header panel spans full width and has a height of 30 pixels
        headerPanel.setBackground(new Color(0, 51, 153));
        add(headerPanel);

        // Close Button
        JButton closeButton = new JButton();
        closeButton.setBounds(970, 0, 30, 30);
        closeButton.setBackground(new Color(255, 255, 255));
        closeButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\close.png")); // Update path as needed
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set custom colors for the confirm dialog
                UIManager.put("OptionPane.background", new Color(255, 255, 255)); // Dialog background color
                UIManager.put("Panel.background", new Color(255, 255, 255)); // Panel background color
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
                        new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\close.png")); // Update path as needed

                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        headerPanel.add(closeButton);

        // Back Button
        JButton backButton = new JButton();
        backButton.setBounds(0, 0, 30, 30); // Positioned on the left side
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\back.png")); // Update path as needed
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginScreen(); // Close the current login window
            }
        });
        headerPanel.add(backButton);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.getUser(username);
                    if (user instanceof Operator) {
                        Operator operator = (Operator) user;
                        if (operator.getPassword().equals(password)) {
                            handleLogin(username, password);
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Credentials");
                        }
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
                dispose(); // Close the login window
            }
        });
    }

    private void handleLogin(String username, String password) {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUser(username);
            if (user instanceof Operator) {
                Operator operator = (Operator) user;
                String operatorRegion = operator.getRegion();
                System.out.println(operatorRegion);
                new OperatorDashboard(username,operatorRegion).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "User is not an Operator");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        new OperatorLogin().setVisible(true);
    }
}
