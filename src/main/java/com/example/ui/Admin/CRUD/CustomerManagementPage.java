package com.example.ui.Admin.CRUD;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;
import com.example.model.OldCustomer;
import com.example.model.NewCustomer;
import com.example.ui.Admin.AdminDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CustomerManagementPage extends JFrame {
    private JTable customersTable;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO;
    private JTextField searchField;

    private final Color primaryColor = new Color(0, 51, 153);
    private final Color buttonColor = new Color(53, 150, 243);
    private final Color buttonTextColor = Color.WHITE;

    public CustomerManagementPage() {
        setTitle("Customer Management");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        customerDAO = new CustomerDAO();

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\imgs\\Admin.jpeg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        add(backgroundPanel, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1000, 30);
        headerPanel.setBackground(primaryColor);
        backgroundPanel.add(headerPanel);

        JButton backButton = new JButton();
        backButton.setBounds(10, 0, 30, 30);
        backButton.setBackground(primaryColor);
        backButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\back.png"));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> {
            dispose();
            new AdminDashboard().setVisible(true);
        });
        headerPanel.add(backButton);

        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(50, 30, 900, 30);
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backgroundPanel.add(searchPanel);

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(buttonColor);
        searchButton.setForeground(buttonTextColor);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchCustomer());

        searchPanel.add(new JLabel("Search by Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(50, 70, 900, 400);
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        backgroundPanel.add(tablePanel);

        String[] columnNames = {"Meter Code", "Name", "Email", "Type", "Previous Reading", "Current Reading", "Property Details", "Contract Attachment", "Password"};
        tableModel = new NonEditableTableModel(columnNames);
        customersTable = new JTable(tableModel);
        customersTable.setFillsViewportHeight(true);
        customersTable.setBackground(Color.WHITE);
        customersTable.setFont(new Font("Arial", Font.PLAIN, 11));
        customersTable.setRowHeight(30);

        customersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        customersTable.getTableHeader().setBackground(Color.WHITE);
        customersTable.getTableHeader().setForeground(Color.BLACK);

        customersTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE);
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(customersTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(80, 470, 900, 200);
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        backgroundPanel.add(buttonsPanel);

        JButton addButton = new JButton("Add Customer");
        addButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\add.png"));
        addButton.setBackground(buttonColor);
        addButton.setForeground(buttonTextColor);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(150, 30));
        buttonsPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\update.png"));
        updateButton.setBackground(buttonColor);
        updateButton.setForeground(buttonTextColor);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(150, 30));
        buttonsPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\delete.png"));
        deleteButton.setBackground(buttonColor);
        deleteButton.setForeground(buttonTextColor);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new Dimension(150, 30));
        buttonsPanel.add(deleteButton);

        updateButton.addActionListener(e -> {
            int selectedRow = customersTable.getSelectedRow();
            if (selectedRow >= 0) {
                String meterCode = (String) tableModel.getValueAt(selectedRow, 0);
                new UpdateCustomerPage(meterCode, CustomerManagementPage.this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(CustomerManagementPage.this, "Please select a customer to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = customersTable.getSelectedRow();
            if (selectedRow >= 0) {
                String meterCode = (String) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(CustomerManagementPage.this, "Are you sure you want to delete this customer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        customerDAO.deleteCustomer(meterCode);
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(CustomerManagementPage.this, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CustomerManagementPage.this, "Error deleting customer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(CustomerManagementPage.this, "Please select a customer to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addButton.addActionListener(e -> new AddCustomerPage(CustomerManagementPage.this).setVisible(true));

        backButton.addActionListener(e -> dispose());

        loadCustomerData();
    }

    public void loadCustomerData() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            tableModel.setRowCount(0);
            for (Customer customer : customers) {
                String type = (customer instanceof OldCustomer) ? "Old" : "New";

                String[] data;
                if (customer instanceof OldCustomer) {
                    OldCustomer oldCustomer = (OldCustomer) customer;
                    data = new String[] {
                            oldCustomer.getMeterCode(),
                            oldCustomer.getName(),
                            oldCustomer.getEmail(),
                            type,
                            String.valueOf(oldCustomer.getPreviousReading()),
                            String.valueOf(oldCustomer.getCurrentReading()),
                            "",
                            "",
                            oldCustomer.getPassword()
                    };
                } else {
                    NewCustomer newCustomer = (NewCustomer) customer;
                    data = new String[] {
                            newCustomer.getMeterCode(),
                            newCustomer.getName(),
                            newCustomer.getEmail(),
                            type,
                            "",
                            "",
                            newCustomer.getPropertyDetails(),
                            newCustomer.getContractAttachment(),
                            newCustomer.getPassword()
                    };
                }

                tableModel.addRow(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customer data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCustomer() {
        String searchTerm = searchField.getText().trim();
        try {
            List<Customer> customers = customerDAO.searchCustomersByName(searchTerm);
            tableModel.setRowCount(0);
            for (Customer customer : customers) {
                String type = (customer instanceof OldCustomer) ? "Old" : "New";
                String[] data = {
                        customer.getMeterCode(),
                        customer.getName(),
                        customer.getEmail(),
                        type,
                        (customer instanceof OldCustomer) ? String.valueOf(((OldCustomer) customer).getPreviousReading()) : "",
                        (customer instanceof OldCustomer) ? String.valueOf(((OldCustomer) customer).getCurrentReading()) : "",
                        (customer instanceof NewCustomer) ? ((NewCustomer) customer).getPropertyDetails() : "",
                        (customer instanceof NewCustomer) ? ((NewCustomer) customer).getContractAttachment() : "",
                        customer.getPassword()
                };
                tableModel.addRow(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for customers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class NonEditableTableModel extends DefaultTableModel {
        NonEditableTableModel(Object[] columnNames) {
            super(columnNames, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
