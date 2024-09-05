package com.example.ui.Operator;

import com.example.ui.Operator.OperatorDashboard;
import com.example.util.DatabaseConnection;
import com.example.dao.BillDAO;
import com.example.model.Bill;
import com.itextpdf.text.Phrase;
import java.io.IOException;
import com.example.service.BillCalculator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ViewRegionalBillsPage extends JFrame {

    private String operatorRegion;
    private DefaultTableModel tableModel;
    private JTable billsTable;
    private String username;

    public ViewRegionalBillsPage(String username, String operatorRegion) {
        this.operatorRegion = operatorRegion;
        System.out.println(operatorRegion);
        this.username = username;

        setTitle("View Regional Bills");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("E:\\spring\\ElectricityBillingSystem\\src\\main\\java\\com\\example\\ui\\imgs\\Operator.jpg");
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
            new OperatorDashboard(username, operatorRegion).setVisible(true);
        });
        headerPanel.add(backButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(50, 50, 900, 500);
        contentPanel.setOpaque(false);
        backgroundPanel.add(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        String[] columnNames = {
                "Meter Code",
                "Previous Reading",
                "Current Reading",
                "Due Date",
                "Payment Status",
                "Customer Name",
                "Consumption",
                "Amount"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        billsTable = new JTable(tableModel);

        billsTable.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 11));
        billsTable.setFillsViewportHeight(true);
        billsTable.setBackground(Color.WHITE);
        billsTable.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        billsTable.setRowHeight(30);

        billsTable.getTableHeader().setBackground(Color.WHITE);
        billsTable.setBackground(Color.WHITE);
        billsTable.setForeground(Color.BLACK);

        billsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE);
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        loadBills();

        billsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(billsTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backgroundPanel.add(buttonPanel);
        buttonPanel.setBounds(50, 550, 900, 50);
        buttonPanel.setOpaque(false);

        JButton downloadPDFButton = new JButton("Download as PDF");
        downloadPDFButton.setBackground(new Color(0, 70, 240));
        downloadPDFButton.setForeground(Color.WHITE);
        downloadPDFButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        downloadPDFButton.setBorderPainted(false);
        downloadPDFButton.setFocusPainted(false);
        downloadPDFButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                downloadPDFButton.setBackground(new Color(0, 70, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                downloadPDFButton.setBackground(new Color(0, 51, 153));
            }
        });

        downloadPDFButton.addActionListener(e -> downloadSelectedBillAsPDF());
        buttonPanel.add(downloadPDFButton);




    }

    private void loadBills() {
        tableModel.setRowCount(0);
        BillDAO billDAO = new BillDAO();
        try {
            List<Bill> bills = billDAO.getUnpaidOrPartiallyPaidBillsByRegion(operatorRegion);
            if (bills != null) {
                for (Bill bill : bills) {
                    double consumption = bill.getConsumption();
                    System.out.println(bill);
                    double advancedBill = BillCalculator.calculateBill(consumption);

                    String[] data = {
                            bill.getMeterCode(),
                            String.valueOf(bill.getPreviousReading()),
                            String.valueOf(bill.getCurrentReading()),
                            bill.getDueDate().toString(),
                            bill.getPaymentStatus(),
                            bill.getCustomerName(),
                            String.valueOf(consumption),
                            String.format("%.2f", advancedBill)
                    };
                    tableModel.addRow(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bills.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void downloadSelectedBillAsPDF() {
        int selectedRow = billsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bill to download.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String meterCode = (String) billsTable.getValueAt(selectedRow, 0);
        String customerName = (String) billsTable.getValueAt(selectedRow, 5);
        String fileName = meterCode + "_bill.pdf";

        String customerEmail = "";
        String customerType = "";
        String propertyDetails = "";
        String contractAttachment = "";
        double consumption = Double.parseDouble((String) billsTable.getValueAt(selectedRow, 6));
        double advancedBill = BillCalculator.calculateBill(consumption);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT email, type, property_details, contract_attachment FROM customers WHERE meter_code = ?")) {
            statement.setString(1, meterCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customerEmail = resultSet.getString("email");
                    customerType = resultSet.getString("type");
                    propertyDetails = resultSet.getString("property_details");
                    contractAttachment = resultSet.getString("contract_attachment");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving customer details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 10);

            PdfPTable companyTable = new PdfPTable(1);
            companyTable.setWidthPercentage(100);
            companyTable.addCell(createCell("Electricity Bill", headerFont, Element.ALIGN_LEFT));
            companyTable.addCell(createCell(operatorRegion, normalFont, Element.ALIGN_LEFT));
            companyTable.addCell(createCell(username, normalFont, Element.ALIGN_LEFT));
            document.add(companyTable);

            document.add(new Paragraph("\n"));

            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);

            PdfPTable billToTable = new PdfPTable(1);
            billToTable.addCell(createCell("BILL TO", smallFont, Element.ALIGN_LEFT));
            billToTable.addCell(createCell(customerName, normalFont, Element.ALIGN_LEFT));
            billToTable.addCell(createCell(propertyDetails, normalFont, Element.ALIGN_LEFT));
            billToTable.addCell(createCell(customerEmail, normalFont, Element.ALIGN_LEFT));
            detailsTable.addCell(billToTable);

            PdfPTable invoiceDetailsTable = new PdfPTable(1);
            invoiceDetailsTable.addCell(createCell("INVOICE DETAILS", smallFont, Element.ALIGN_LEFT));
            invoiceDetailsTable.addCell(createCell("Invoice Number: " + meterCode, normalFont, Element.ALIGN_LEFT));
            invoiceDetailsTable.addCell(createCell("Invoice Date: " + java.time.LocalDate.now().toString(), normalFont, Element.ALIGN_LEFT));
            invoiceDetailsTable.addCell(createCell("Contract: " + contractAttachment, normalFont, Element.ALIGN_LEFT));
            invoiceDetailsTable.addCell(createCell("Customer Type: " + customerType, normalFont, Element.ALIGN_LEFT));
            detailsTable.addCell(invoiceDetailsTable);

            document.add(detailsTable);

            document.add(new Paragraph("\n"));

            PdfPTable billTable = new PdfPTable(2);
            billTable.setWidthPercentage(100);
            billTable.addCell(createCell("Description", normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell("Amount", normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell("Previous Reading", normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell(String.valueOf(billsTable.getValueAt(selectedRow, 1)), normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell("Current Reading", normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell(String.valueOf(billsTable.getValueAt(selectedRow, 2)), normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell("Consumption", normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell(String.valueOf(consumption), normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell("Amount", normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell(String.format("%.2f", advancedBill), normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell("Payment Status", normalFont, Element.ALIGN_LEFT));
            billTable.addCell(createCell((String) billsTable.getValueAt(selectedRow, 4), normalFont, Element.ALIGN_LEFT));
            document.add(billTable);

            document.add(new Paragraph("\n"));

            PdfPTable paymentTable = new PdfPTable(1);
            paymentTable.setWidthPercentage(100);
            paymentTable.addCell(createCell("Payment due by " + billsTable.getValueAt(selectedRow, 3), normalFont, Element.ALIGN_CENTER));
            document.add(paymentTable);

            // Add a section detailing the calculation
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Amount Calculation:", headerFont));
            document.add(new Paragraph("Total Consumption: " + consumption + " kWh", normalFont));
            document.add(new Paragraph("Calculated Amount: $" + String.format("%.2f", advancedBill), normalFont));

            document.close();
            writer.close();

            JOptionPane.showMessageDialog(this, "Bill saved as " + fileName, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private PdfPCell createCell(String content, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }








    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewRegionalBillsPage("Luxor","Mostafa Alaa").setVisible(true));
    }
}
