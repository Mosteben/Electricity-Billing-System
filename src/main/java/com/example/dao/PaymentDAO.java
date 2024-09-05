package com.example.dao;

import java.sql.*;
import com.example.util.DatabaseConnection;

public class PaymentDAO {
    public void addPayment(String meterCode, double amount) throws SQLException {
        String query = "INSERT INTO payments (meter_code, amount, payment_date) VALUES (?, ?, GETDATE())";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meterCode);
            statement.setDouble(2, amount);

            statement.executeUpdate();
        }
    }


}
