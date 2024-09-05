package com.example.ui.Operator;

import com.example.ui.Operator.DefineTariffPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefineTariffFrame extends JFrame {

    public DefineTariffFrame() {
        setTitle("Define Tariff");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(600, 600);
        setLocationRelativeTo(null);
        add(new DefineTariffPanel());
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

    }
}
