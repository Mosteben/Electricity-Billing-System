package com.example.ui.Admin.Statistics;

import com.example.dao.BillDAO;
import com.example.model.Bill;
import com.example.ui.Admin.AdminDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ViewBillsPage extends JFrame {

    private List<Bill> bills;
    private JLabel originalTotalLabel;
    private JLabel totalPaymentsLabel;

    public ViewBillsPage() {
        setTitle("View Bills");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

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
        headerPanel.setBackground(new Color(0, 51, 153));
        backgroundPanel.add(headerPanel);

        JButton backButton = new JButton();
        backButton.setBounds(10, 0, 30, 30);
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setIcon(new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\icos\\back.png"));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> {
            dispose();
            new AdminDashboard().setVisible(true);
        });
        headerPanel.add(backButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(50, 50, 900, 358);
        contentPanel.setOpaque(false);
        backgroundPanel.add(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel();
        filterPanel.setOpaque(false);
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel filterLabel = new JLabel("Filter by Region:");
        filterLabel.setForeground(Color.WHITE);

        JComboBox<String> regionComboBox = new JComboBox<>(new String[]{"All", "Cairo", "Alexandria", "Giza", "Luxor", "Aswan"});
        regionComboBox.setPreferredSize(new Dimension(150, 30));
        regionComboBox.setBackground(Color.WHITE);
        regionComboBox.setForeground(Color.BLACK);
        regionComboBox.setBorder(BorderFactory.createEmptyBorder());
        regionComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        regionComboBox.setRenderer(new CustomComboBoxRenderer());

        filterPanel.add(filterLabel);
        filterPanel.add(regionComboBox);
        backgroundPanel.add(filterPanel);
        filterPanel.setBounds(50, 20, 300, 30);

        String[] columnNames = {"Meter Code", "Customer Name", "Bill Date", "Amount Due", "Amount Paid", "Due Date", "Payment Status", "Region"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable billsTable = new JTable(tableModel);

        billsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        billsTable.getTableHeader().setBackground(Color.WHITE);
        billsTable.getTableHeader().setForeground(Color.BLACK);
        billsTable.setBackground(Color.WHITE);
        billsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        billsTable.setRowHeight(30);

        billsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (column == 6) {
                    String status = (String) value;
                    if ("Paid".equalsIgnoreCase(status)) {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    } else if ("Unpaid".equalsIgnoreCase(status)) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        billsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        BillDAO billDAO = new BillDAO();
        try {
            bills = billDAO.getAllBills();
            if (bills != null) {
                for (Bill bill : bills) {
                    String[] data = {
                            bill.getMeterCode(),
                            bill.getCustomerName(),
                            bill.getBillDate().toString(),
                            String.valueOf(bill.getAmountDue()),
                            String.valueOf(bill.getAmountPaid()),
                            bill.getDueDate().toString(),
                            bill.getPaymentStatus(),
                            bill.getRegion()
                    };
                    tableModel.addRow(data);
                }
            }
            // Initialize labels for displaying totals
            originalTotalLabel = new JLabel("Original Total Payment: $0.00");
            originalTotalLabel.setForeground(Color.BLACK);
            originalTotalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            originalTotalLabel.setBounds(50, 420, 300, 30);
            backgroundPanel.add(originalTotalLabel);

            totalPaymentsLabel = new JLabel("Total Payments: $0.00");
            totalPaymentsLabel.setForeground(Color.BLACK);
            totalPaymentsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            totalPaymentsLabel.setBounds(50, 460, 300, 30);
            backgroundPanel.add(totalPaymentsLabel);


            updateTotals();  // Update totals after loading bills
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(billsTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        regionComboBox.addActionListener(e -> {
            String selectedRegion = (String) regionComboBox.getSelectedItem();
            tableModel.setRowCount(0);

            if (bills != null) {
                for (Bill bill : bills) {
                    String billRegion = bill.getRegion() != null ? bill.getRegion() : "";

                    if ("All".equals(selectedRegion) || billRegion.equalsIgnoreCase(selectedRegion)) {
                        String[] data = {
                                bill.getMeterCode(),
                                bill.getCustomerName(),
                                bill.getBillDate().toString(),
                                String.valueOf(bill.getAmountDue()),
                                String.valueOf(bill.getAmountPaid()),
                                bill.getDueDate().toString(),
                                bill.getPaymentStatus(),
                                billRegion
                        };
                        tableModel.addRow(data);
                    }
                }
            }
            updateTotals();  // Update totals after filtering
        });

        setVisible(true);
    }

    private double calculateOriginalTotalPayment(List<Bill> bills) {
        double total = 0.0;
        for (Bill bill : bills) {
            total += bill.getAmountDue();
        }
        return total;
    }

    private double calculateTotalPayments(List<Bill> bills) {
        double total = 0.0;
        for (Bill bill : bills) {
            total += bill.getAmountPaid();
        }
        return total;
    }

    private void updateTotals() {
        if (bills != null) {
            double originalTotalPayment = calculateOriginalTotalPayment(bills);
            double totalPayments = calculateTotalPayments(bills);

            if (originalTotalLabel != null && totalPaymentsLabel != null) {
                originalTotalLabel.setText(String.format("Original Total Payment: $%.2f", originalTotalPayment));
                totalPaymentsLabel.setText(String.format("Total Payments: $%.2f", totalPayments));
            }
        }
    }

    private static class CustomComboBoxRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (isSelected) {
                c.setBackground(new Color(0, 51, 153));
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewBillsPage::new);
    }
}
