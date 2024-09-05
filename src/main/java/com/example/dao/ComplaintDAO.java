package com.example.dao;

import com.example.util.DatabaseConnection;
import com.example.model.Complaint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {
    public void addComplaint(String meterCode, String complaintText) throws SQLException {
        String query = "INSERT INTO complaints (meter_code, complaint_text, complaint_date) VALUES (?, ?, GETDATE())";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meterCode);
            statement.setString(2, complaintText);

            statement.executeUpdate();
        }
    }

    public List<Complaint> getAllComplaints() throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT c.meter_code, c.complaint_text, c.complaint_date, cu.name " +
                "FROM complaints c " +
                "JOIN customers cu ON c.meter_code = cu.meter_code";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Complaint complaint = new Complaint();
                complaint.setMeterCode(resultSet.getString("meter_code"));
                complaint.setComplaintText(resultSet.getString("complaint_text"));
                complaint.setComplaintDate(resultSet.getDate("complaint_date"));
                complaint.setCustomerName(resultSet.getString("name"));
                complaints.add(complaint);
            }
        }
        return complaints;
    }
}
