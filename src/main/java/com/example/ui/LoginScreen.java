package com.example.ui;

import com.example.ui.Login.AdminLogin;
import com.example.ui.Login.CustomerLogin;
import com.example.ui.Login.OperatorLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setTitle("Login Screen");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\imgs\\loginas.jpeg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 500, 600);
        add(backgroundPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(500, 0, 500, 600);
        rightPanel.setBackground(Color.WHITE);

        JLabel loginAsLabel = new JLabel("Log in as :");
        loginAsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginAsLabel.setBounds(200, 200, 250, 30);
        rightPanel.add(loginAsLabel);

        JButton adminButton = new JButton("ADMIN");
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setBounds(75, 280, 350, 40);
        adminButton.setBackground(new Color(0, 51, 153));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        rightPanel.add(adminButton);

        JButton customerButton = new JButton("CUSTOMER");
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setBounds(75, 340, 350, 40);
        customerButton.setBackground(new Color(0, 51, 153));
        customerButton.setForeground(Color.WHITE);
        customerButton.setFocusPainted(false);
        rightPanel.add(customerButton);

        JButton operatorButton = new JButton("OPERATOR");
        operatorButton.setFont(new Font("Arial", Font.BOLD, 16));
        operatorButton.setBounds(75, 400, 350, 40);
        operatorButton.setBackground(new Color(0, 51, 153));
        operatorButton.setForeground(Color.WHITE);
        operatorButton.setFocusPainted(false);
        rightPanel.add(operatorButton);

        add(rightPanel);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0,0, 1000, 30);
        headerPanel.setBackground(new Color(0, 51, 153));
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

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLogin();
                dispose();
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerLogin();
                dispose();
            }
        });

        operatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OperatorLogin().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
