package com.example.dao;

import com.example.model.User;
import com.example.model.Admin;
import com.example.model.Operator;
import java.sql.*;
import com.example.util.DatabaseConnection;

public class UserDAO {
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, role, password, region) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRole());
            statement.setString(3, user.getPassword());
            if (user instanceof Operator) {
                statement.setString(4, ((Operator) user).getRegion());
            } else {
                statement.setNull(4, Types.VARCHAR);
            }

            statement.executeUpdate();
        }
    }

    public User getUser(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    String password = resultSet.getString("password");
                    String region = resultSet.getString("region");
                    if ("Operator".equals(role)) {
                        return new Operator(username, password, region);
                    } else if ("Admin".equals(role)) {
                        return new Admin(username, password);
                    }
                }
            }
        }
        return null;
    }
}
