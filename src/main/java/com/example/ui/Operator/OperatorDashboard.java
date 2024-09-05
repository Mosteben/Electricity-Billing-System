package com.example.ui.Operator;

import com.example.dao.BillDAO;
import com.example.model.Bill;
import com.example.ui.Admin.CRUD.MeterManagementFrame;
import com.example.ui.Operator.DefineTariffPanel;
import com.example.ui.Login.OperatorLogin;
import com.example.dao.CustomerDAO;
import java.sql.SQLException;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OperatorDashboard extends JFrame {

    private JPanel contentPanel;
    private String operatorRegion;
    private String username;

    public OperatorDashboard(String username,String operatorRegion) {
        this.operatorRegion = operatorRegion;
        System.out.println(operatorRegion);

        this.username=username;

        setTitle("Operator Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                ImageIcon backgroundImage = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\imgs\\Operator.jpg");
                g.drawImage(backgroundImage.getImage(), 300, 0, 500, 500, this);
            }
        };
        backgroundPanel.setLayout(null);
        add(backgroundPanel, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1000, 30);
        headerPanel.setBackground(new Color(0, 51, 153));
        backgroundPanel.add(headerPanel);

        JButton closeButton = new JButton();
        closeButton.setBounds(970, 0, 30, 30);
        closeButton.setBackground(new Color(0, 51, 153));
        closeButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\close.png"));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("OptionPane.background", Color.WHITE);
                UIManager.put("Panel.background", Color.WHITE);
                UIManager.put("OptionPane.messageForeground", new Color(0, 51, 153));
                UIManager.put("Button.background", new Color(0, 51, 153));
                UIManager.put("Button.foreground", Color.WHITE);
                UIManager.put("Button.focus", new Color(0, 51, 153));

                int response = JOptionPane.showConfirmDialog(null,
                        "<html><font color='#003399'>Do you really want to exit?</font></html>",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\exit.png"));

                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        headerPanel.add(closeButton);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(null);
        sidebarPanel.setBounds(0, 30, 200, 570);
        sidebarPanel.setBackground(new Color(0, 51, 153));
        backgroundPanel.add(sidebarPanel);

        JButton viewRegionalBillsButton = createSidebarButton("View Regional Bills", 100, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\region.png");
        JButton validateReadingButton = createSidebarButton("Validate Reading", 190, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\validate.png");
        JButton defineTariffButton = createSidebarButton("Define Tariff", 280, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\tariff.png");
        JButton stopMeterButton = createSidebarButton("Stop Meter", 370, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\stop.png");
        JButton logoutSidebarButton = createSidebarButton("Logout", 460, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\logout.png");

        sidebarPanel.add(viewRegionalBillsButton);
        sidebarPanel.add(validateReadingButton);
        sidebarPanel.add(defineTariffButton);
        sidebarPanel.add(stopMeterButton);
        sidebarPanel.add(logoutSidebarButton);

        contentPanel = new JPanel();
        contentPanel.setBounds(200, 30, 800, 570);
        contentPanel.setOpaque(false);
        backgroundPanel.add(contentPanel);

        showDefaultPanel();

        viewRegionalBillsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                showViewRegionalBillsPanel();
            }
        });

        validateReadingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showValidateReadingPanel();
            }
        });

        defineTariffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDefineTariffPanel();
            }
        });

        stopMeterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStopMeterPanel();
            }
        });

        logoutSidebarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OperatorLogin().setVisible(true);
            }
        });

        setVisible(true);
    }

    private JButton createSidebarButton(String text, int yPosition, String iconPath) {
        JButton button = new JButton(text, new ImageIcon(iconPath));
        button.setBounds(10, yPosition, 180, 50);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 51, 153));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private void showDefaultPanel() {
        contentPanel.removeAll();
        JLabel label = new JLabel(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\default_image.png"));
        contentPanel.add(label);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showViewRegionalBillsPanel() {
        new ViewRegionalBillsPage(username,operatorRegion).setVisible(true);
    }


    private void showValidateReadingPanel() {
            new ValidateMeterReadingPage(operatorRegion).setVisible(true);
        }


    private void showDefineTariffPanel() {
        SwingUtilities.invokeLater(() -> {
            DefineTariffFrame frame = new DefineTariffFrame();
            frame.setVisible(true);
        });
    }

    private void showStopMeterPanel() {
        SwingUtilities.invokeLater(() -> new MeterManagementFrame(operatorRegion));
    }


    public static void main(String[] args) {
        new OperatorDashboard("Aswan","Ashrf");
    }
}
