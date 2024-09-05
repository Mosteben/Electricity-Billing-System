package com.example.ui.Admin;
import com.example.ui.Admin.CRUD.CustomerManagementPage;
import com.example.ui.Admin.Compliant.ViewComplaintsPage;
import com.example.ui.Admin.Statistics.ViewBillsPage;
import com.example.ui.Login.AdminLogin;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {

    private JPanel contentPanel;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(new BorderLayout());

        // Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\imgs\\Admin.jpeg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); // Use null layout for absolute positioning
        add(backgroundPanel, BorderLayout.CENTER);

        // Header Panel
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
                // Set custom colors for the confirm dialog
                UIManager.put("OptionPane.background", Color.WHITE);
                UIManager.put("Panel.background", Color.WHITE);
                UIManager.put("OptionPane.messageForeground", new Color(0, 51, 153));
                UIManager.put("Button.background", new Color(0, 51, 153));
                UIManager.put("Button.foreground", Color.WHITE);
                UIManager.put("Button.focus", new Color(0, 51, 153));

                // Show confirmation dialog with custom styling
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

        // Sidebar Panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(null);
        sidebarPanel.setBounds(0, 30, 200, 570);
        sidebarPanel.setBackground(new Color(0, 51, 153));
        backgroundPanel.add(sidebarPanel);

        // Buttons for the sidebar
        //JButton homeButton = createSidebarButton("HOME", 90, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\home.png");
        JButton showUpdateDeleteButton = createSidebarButton("Check Coustumers", 100, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\update_delete.png");
        JButton addNewButton = createSidebarButton("statistics", 190, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\stat.png");
        //JButton ordersButton = createSidebarButton("Orders", 290, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\orders.png");
        JButton complaintsButton = createSidebarButton("Complaints", 280, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\complaints.png");
        JButton logoutSidebarButton = createSidebarButton("Logout", 370, "E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\logout.png");

        // Add buttons to the sidebar
        //sidebarPanel.add(homeButton);
        sidebarPanel.add(showUpdateDeleteButton);
        sidebarPanel.add(addNewButton);
        //sidebarPanel.add(ordersButton);
        sidebarPanel.add(complaintsButton);
        sidebarPanel.add(logoutSidebarButton);

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setBounds(200, 30, 800, 570);
        contentPanel.setOpaque(false); // Make sure the contentPanel is transparent to show the background
        backgroundPanel.add(contentPanel);

        // Set initial content
        showViewBillsPanel();

        // Action Listeners for Sidebar Buttons
        //homeButton.addActionListener(new ActionListener() {
           // @Override
            //public void actionPerformed(ActionEvent e) {
              //  showViewBillsPanel();
            //}
        //});

        showUpdateDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showViewStatisticsPanel();
            }
        });

        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showManageUsersPanel();
            }
        });

        //ordersButton.addActionListener(new ActionListener() {
        //@Override
        //public void actionPerformed(ActionEvent e) {
        //  showViewComplaintsPanel();
        //}
        //});

        complaintsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showViewComplaintsPanel();
            }
        });

        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatisticsPanel();
            }
        });

        logoutSidebarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to log out
                dispose();
                new AdminLogin().setVisible(true); // Redirect to login page
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

    private void showViewBillsPanel() {
        contentPanel.removeAll();
        JLabel label = new JLabel(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\bills_image.png"));
        contentPanel.add(label);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showViewStatisticsPanel() {
        dispose();
        new CustomerManagementPage().setVisible(true);
    }

    private void showStatisticsPanel() {
        dispose();
        SwingUtilities.invokeLater(ViewBillsPage::new);
    }


    private void showManageUsersPanel() {



    }

    private void showViewComplaintsPanel() {
        dispose();
        ViewComplaintsPage viewComplaintsPage = new ViewComplaintsPage();
        viewComplaintsPage.setVisible(true);

    }



    public static void main(String[] args) {
        new AdminDashboard();
    }
}