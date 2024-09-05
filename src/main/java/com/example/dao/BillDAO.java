package com.example.dao;

import com.example.model.Bill;
import com.example.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    private CustomerDAO customerDAO;

    public BillDAO() {
        this.customerDAO = new CustomerDAO();
    }
    public List<Bill> getUnpaidOrPartiallyPaidBillsByRegion(String region) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT c.meter_code, b.bill_date, b.due_date, b.payment_status, c.previous_reading, c.current_reading " +
                "FROM bills b " +
                "JOIN customers c ON b.meter_code = c.meter_code " +
                "WHERE c.type = 'old' " +
                "AND b.region = ? " +
                "AND b.payment_status <> 'Paid'"+
                "AND c.subscription_status='Active' ";


        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, region);
            System.out.println("Executing query: " + statement.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String meterCode = resultSet.getString("meter_code");
                    Date sqlBillDate = resultSet.getDate("bill_date");
                    java.util.Date billDate = new java.util.Date(sqlBillDate.getTime());
                    Date sqlDueDate = resultSet.getDate("due_date");
                    java.util.Date dueDate = new java.util.Date(sqlDueDate.getTime());
                    String paymentStatus = resultSet.getString("payment_status");
                    double previousReading = resultSet.getDouble("previous_reading");
                    double currentReading = resultSet.getDouble("current_reading");

                    // Optional: Calculate amounts if necessary
                    double amountDue = currentReading - previousReading;
                    double amountPaid = 0; // Adjust based on your logic

                    String customerName = customerDAO.getCustomerNameByMeterCode(meterCode);

                    Bill bill = new Bill(meterCode, billDate, previousReading, currentReading, dueDate, paymentStatus, customerName, region);
                    bills.add(bill);
                }
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e.getMessage()); // Debugging
                throw new SQLException("Error fetching unpaid or partially paid bills: " + e.getMessage(), e);
            }
        }
        return bills;
    }





    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT b.meter_code, b.bill_date, b.amount_due, b.amount_paid, b.due_date, b.payment_status, b.region, " +
                "c.previous_reading, c.current_reading " +
                "FROM bills b " +
                "JOIN customers c ON b.meter_code = c.meter_code";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String meterCode = resultSet.getString("meter_code");
                Date sqlBillDate = resultSet.getDate("bill_date");
                java.util.Date billDate = new java.util.Date(sqlBillDate.getTime());
                double amountDue = resultSet.getDouble("amount_due");
                double amountPaid = resultSet.getDouble("amount_paid");
                Date sqlDueDate = resultSet.getDate("due_date");
                java.util.Date dueDate = new java.util.Date(sqlDueDate.getTime());
                String paymentStatus = resultSet.getString("payment_status");
                String region = resultSet.getString("region");
                double previousReading = resultSet.getDouble("previous_reading");
                double currentReading = resultSet.getDouble("current_reading");

                String customerName = customerDAO.getCustomerNameByMeterCode(meterCode);

                Bill bill = new Bill(meterCode, billDate, previousReading, currentReading, dueDate, paymentStatus, customerName, region);
                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching bills: " + e.getMessage(), e);
        }
        return bills;
    }


    public double getTotalCollected() throws SQLException {
        String query = "SELECT SUM(amount_paid) AS total_collected FROM bills";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble("total_collected");
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching total collected: " + e.getMessage(), e);
        }
        return 0.0;
    }

    public String getConsumptionStatistics() throws SQLException {
        StringBuilder statistics = new StringBuilder();
        String query = "SELECT meter_code, SUM(amount_paid) AS total FROM bills GROUP BY meter_code";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String meterCode = resultSet.getString("meter_code");
                double total = resultSet.getDouble("total");
                statistics.append("Meter Code: ").append(meterCode).append(" - Total Paid: ").append(total).append("\n");
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching consumption statistics: " + e.getMessage(), e);
        }
        return statistics.toString();
    }
    public Bill getBillByMeterCodeAndRegion(String meterCode, String region) throws SQLException {
        String query = "SELECT b.meter_code, b.bill_date, b.amount_due, b.amount_paid, b.due_date, b.payment_status, b.region, " +
                "c.previous_reading, c.current_reading " +
                "FROM bills b " +
                "JOIN customers c ON b.meter_code = c.meter_code " +
                "WHERE b.meter_code = ? AND b.region = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meterCode);
            statement.setString(2, region);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String meterCodeResult = resultSet.getString("meter_code");
                    Date sqlBillDate = resultSet.getDate("bill_date");
                    java.util.Date billDate = new java.util.Date(sqlBillDate.getTime());
                    double amountDue = resultSet.getDouble("amount_due");
                    double amountPaid = resultSet.getDouble("amount_paid");
                    Date sqlDueDate = resultSet.getDate("due_date");
                    java.util.Date dueDate = new java.util.Date(sqlDueDate.getTime());
                    String paymentStatus = resultSet.getString("payment_status");
                    String regionResult = resultSet.getString("region");
                    double previousReading = resultSet.getDouble("previous_reading");
                    double currentReading = resultSet.getDouble("current_reading");

                    String customerName = customerDAO.getCustomerNameByMeterCode(meterCodeResult);

                    return new Bill(meterCodeResult, billDate, previousReading, currentReading, dueDate, paymentStatus, customerName, regionResult);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching bill by meter code and region: " + e.getMessage(), e);
        }
        return null; // Return null if no bill is found with the given meter code and region
    }


}
