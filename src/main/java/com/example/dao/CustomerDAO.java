package com.example.dao;

import com.example.model.Customer;
import com.example.model.OldCustomer;
import com.example.model.NewCustomer;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.util.DatabaseConnection;

public class CustomerDAO {
    private static final Logger logger = Logger.getLogger(CustomerDAO.class.getName());

    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (meter_code, name, email, password, type, previous_reading, current_reading, property_details, contract_attachment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getMeterCode());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());

            String password = customer instanceof OldCustomer
                    ? ((OldCustomer) customer).getPassword()
                    : ((NewCustomer) customer).getPassword();
            statement.setString(4, password);

            if (customer instanceof OldCustomer) {
                OldCustomer oldCustomer = (OldCustomer) customer;
                statement.setString(5, "Old");
                statement.setDouble(6, oldCustomer.getPreviousReading());
                statement.setDouble(7, oldCustomer.getCurrentReading());
                statement.setString(8, null);
                statement.setString(9, null);
            } else {
                NewCustomer newCustomer = (NewCustomer) customer;
                statement.setString(5, "New");
                statement.setDouble(6, 0);
                statement.setDouble(7, 0);
                statement.setString(8, newCustomer.getPropertyDetails());
                statement.setString(9, newCustomer.getContractAttachment());
            }

            statement.executeUpdate();
        }
    }

    public Customer getCustomer(String meterCode) throws SQLException {
        String query = "SELECT * FROM customers WHERE meter_code = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meterCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String type = resultSet.getString("type");
                    double previousReading = resultSet.getDouble("previous_reading");
                    double currentReading = resultSet.getDouble("current_reading");
                    String propertyDetails = resultSet.getString("property_details");
                    String contractAttachment = resultSet.getString("contract_attachment");

                    if ("Old".equals(type)) {
                        return new OldCustomer(meterCode, name, email, previousReading, currentReading, password);
                    } else {
                        return new NewCustomer(meterCode, name, email, propertyDetails, contractAttachment, password);
                    }
                }
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String meterCode = resultSet.getString("meter_code");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");

                Customer customer;

                if ("Old".equals(type)) {

                    double previousReading = resultSet.getDouble("previous_reading");
                    double currentReading = resultSet.getDouble("current_reading");
                    customer = new OldCustomer(meterCode, name, email, previousReading, currentReading, password);
                } else if ("New".equals(type)) {
                    String propertyDetails = resultSet.getString("property_details");
                    String contractAttachment = resultSet.getString("contract_attachment");
                    customer = new NewCustomer(meterCode, name, email, propertyDetails, contractAttachment, password);
                } else {

                    throw new SQLException("Unknown customer type: " + type);
                }

                customers.add(customer);
            }
        }
        return customers;
    }


    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET name = ?, email = ?, password = ?, type = ?, previous_reading = ?, current_reading = ?, property_details = ?, contract_attachment = ? WHERE meter_code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPassword());

            if (customer instanceof OldCustomer) {
                OldCustomer oldCustomer = (OldCustomer) customer;
                statement.setString(4, "Old");
                statement.setDouble(5, oldCustomer.getPreviousReading());
                statement.setDouble(6, oldCustomer.getCurrentReading());
                statement.setString(7, null);
                statement.setString(8, null);
            } else {
                NewCustomer newCustomer = (NewCustomer) customer;
                statement.setString(4, "New");
                statement.setDouble(5, 0);
                statement.setDouble(6, 0);
                statement.setString(7, newCustomer.getPropertyDetails());
                statement.setString(8, newCustomer.getContractAttachment());
            }
            statement.setString(9, customer.getMeterCode());

            statement.executeUpdate();
        }
    }

    public void deleteCustomer(String meterCode) throws SQLException {
        String deleteComplaintsQuery = "DELETE FROM complaints WHERE meter_code = ?";
        String deleteSubscriptionQuery = "DELETE FROM subscriptions WHERE meter_code = ?";
        String deleteCustomerQuery = "DELETE FROM customers WHERE meter_code = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteComplaintsStatement = connection.prepareStatement(deleteComplaintsQuery);
                 PreparedStatement deleteSubscriptionStatement = connection.prepareStatement(deleteSubscriptionQuery);
                 PreparedStatement deleteCustomerStatement = connection.prepareStatement(deleteCustomerQuery)) {

                // Delete related complaints
                deleteComplaintsStatement.setString(1, meterCode);
                deleteComplaintsStatement.executeUpdate();

                // Delete related subscriptions
                deleteSubscriptionStatement.setString(1, meterCode);
                deleteSubscriptionStatement.executeUpdate();

                // Delete the customer
                deleteCustomerStatement.setString(1, meterCode);
                deleteCustomerStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }


    public String getCustomerNameByMeterCode(String meterCode) throws SQLException {
        String query = "SELECT name FROM customers WHERE meter_code = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meterCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }

        }
        return "Unknown";
    }

    public int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers"; // Adjust table name if necessary
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public List<Customer> searchCustomersByName(String name) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer;
                    String meterCode = rs.getString("meter_code");
                    String customerName = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String type = rs.getString("type");
                    double previousReading = rs.getDouble("previous_reading");
                    double currentReading = rs.getDouble("current_reading");
                    String propertyDetails = rs.getString("property_details");
                    String contractAttachment = rs.getString("contract_attachment");

                    if ("Old".equals(type)) {
                        customer = new OldCustomer(meterCode, customerName, email, previousReading, currentReading, password);
                    } else {
                        customer = new NewCustomer(meterCode, customerName, email, propertyDetails, contractAttachment, password);
                    }
                    customers.add(customer);
                }
            }
        }
        return customers;
    }
    public boolean meterExists(String meterCode, String region) throws SQLException {
        String query = "SELECT COUNT(*) FROM bills WHERE meter_code = ? AND region = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, meterCode);
            preparedStatement.setString(2, region);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    public void stopMeter(String meterCode) throws SQLException {
        String query = "UPDATE customers SET subscription_status = 'Stopped' WHERE meter_code = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meterCode);
            statement.executeUpdate();
        }
    }

    public void cancelSubscription(String meterCode) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String updateSubscriptionStatus = "UPDATE subscriptions SET status = 'Cancelled', end_date = GETDATE() WHERE meter_code = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSubscriptionStatus)) {
                updateStatement.setString(1, meterCode);
                updateStatement.executeUpdate();
            }

            String deleteSubscription = "DELETE FROM customers WHERE meter_code = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSubscription)) {
                deleteStatement.setString(1, meterCode);
                deleteStatement.executeUpdate();
            }
        }
    }
    public void resumeMeter(String meterCode) throws SQLException {
        String sql = "UPDATE customers SET subscription_status = 'active' WHERE meter_code = ?";
        try (Connection conn= DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, meterCode);
            stmt.executeUpdate();
        }
    }

    public Customer getCustomerByStatus(String status) throws SQLException {
        String query = "SELECT * FROM customers WHERE subscription_status = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Create and return Customer object based on the result
                }
            }
        }
        return null;
    }
}
