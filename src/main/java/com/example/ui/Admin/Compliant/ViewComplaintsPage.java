package com.example.ui.Admin.Compliant;

import com.example.dao.ComplaintDAO;
import com.example.model.Complaint;
import com.example.ui.Admin.AdminDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ViewComplaintsPage extends JFrame {

    public ViewComplaintsPage() {
        setTitle("View Complaints");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        JButton backButton = new JButton();
        backButton.setBounds(10, 0, 30, 30);
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\back.png"));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);

        backButton.addActionListener(e -> {
            dispose(); // Close the current frame
            new AdminDashboard().setVisible(true); // Open the AdminDashboard
        });
        headerPanel.add(backButton);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(50, 50, 900, 358); // Reduce the height to 300
        contentPanel.setOpaque(false);
        backgroundPanel.add(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        // Table to display complaints
        String[] columnNames = {"Customer Name", "Complaint", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        JTable complaintsTable = new JTable(tableModel);

        // Set the font size for the table header and cells
        complaintsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        complaintsTable.setFillsViewportHeight(true);
        complaintsTable.setBackground(Color.WHITE);
        complaintsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        complaintsTable.setRowHeight(30); // Adjust row height to 25 for a more compact table

        // Set the background color of the table, header, and rows to white
        complaintsTable.getTableHeader().setBackground(Color.WHITE);
        complaintsTable.setBackground(Color.WHITE);
        complaintsTable.setForeground(Color.BLACK);

        // Set the background color of the table rows to white
        complaintsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE); // Set row background color to white
                setHorizontalAlignment(JLabel.CENTER); // Center the text
                return c;
            }
        });

        // Enable auto-resize to adjust columns with data size
        complaintsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        ComplaintDAO complaintDAO = new ComplaintDAO();
        List<Complaint> complaints = null;
        try {
            complaints = complaintDAO.getAllComplaints();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (complaints != null) {
            for (Complaint complaint : complaints) {
                String[] data = {
                        complaint.getCustomerName(),
                        complaint.getComplaintText(),
                        complaint.getComplaintDate().toString()
                };
                tableModel.addRow(data);
            }
        }

        JScrollPane scrollPane = new JScrollPane(complaintsTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewComplaintsPage();
    }
}
