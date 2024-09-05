package com.example.service;

import com.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillCalculator {

    private static double tier1Rate;
    private static double tier2Rate;
    private static double tier3Rate;
    private static double tier4Rate;
    private static double tier5Rate;
    private static double tier6Rate;

    private static double customerServiceFee1;
    private static double customerServiceFee2;
    private static double customerServiceFee3;
    private static double customerServiceFee4;
    private static double customerServiceFee5;

    static {
        loadTariffsFromDatabase();
    }

    private static void loadTariffsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT TOP 1 * FROM tariffs ORDER BY effective_date DESC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                tier1Rate = resultSet.getDouble("tier1_rate");
                tier2Rate = resultSet.getDouble("tier2_rate");
                tier3Rate = resultSet.getDouble("tier3_rate");
                tier4Rate = resultSet.getDouble("tier4_rate");
                tier5Rate = resultSet.getDouble("tier5_rate");
                tier6Rate = resultSet.getDouble("tier6_rate");

                customerServiceFee1 = resultSet.getDouble("service_fee1");
                customerServiceFee2 = resultSet.getDouble("service_fee2");
                customerServiceFee3 = resultSet.getDouble("service_fee3");
                customerServiceFee4 = resultSet.getDouble("service_fee4");
                customerServiceFee5 = resultSet.getDouble("service_fee5");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double calculateBill(double consumption) {
        double bill = 0;

        if (consumption <= 50) {
            bill = consumption * tier1Rate;
        } else if (consumption <= 100) {
            bill = 50 * tier1Rate + (consumption - 50) * tier2Rate;
        } else if (consumption <= 200) {
            bill = 50 * tier1Rate + 50 * tier2Rate + (consumption - 100) * tier3Rate;
        } else if (consumption <= 350) {
            bill = 50 * tier1Rate + 50 * tier2Rate + 100 * tier3Rate + (consumption - 200) * tier4Rate;
        } else if (consumption <= 650) {
            bill = 50 * tier1Rate + 50 * tier2Rate + 100 * tier3Rate + 150 * tier4Rate + (consumption - 350) * tier5Rate;
        } else if (consumption <= 1000) {
            bill = 50 * tier1Rate + 50 * tier2Rate + 100 * tier3Rate + 150 * tier4Rate + 300 * tier5Rate + (consumption - 650) * tier6Rate;
        } else {
            bill = 50 * tier1Rate + 50 * tier2Rate + 100 * tier3Rate + 150 * tier4Rate + 300 * tier5Rate + (consumption - 1000) * tier6Rate;
        }

        if (consumption <= 100) {
            bill += customerServiceFee1;
        } else if (consumption <= 200) {
            bill += customerServiceFee2;
        } else if (consumption <= 350) {
            bill += customerServiceFee3;
        } else if (consumption <= 650) {
            bill += customerServiceFee3;
        } else if (consumption <= 1000) {
            bill += customerServiceFee4;
        } else {
            bill += customerServiceFee5;
        }

        return bill;
    }

    public static void setTier1Rate(double rate) { tier1Rate = rate; }
    public static void setTier2Rate(double rate) { tier2Rate = rate; }
    public static void setTier3Rate(double rate) { tier3Rate = rate; }
    public static void setTier4Rate(double rate) { tier4Rate = rate; }
    public static void setTier5Rate(double rate) { tier5Rate = rate; }
    public static void setTier6Rate(double rate) { tier6Rate = rate; }

    public static void setCustomerServiceFee1(double fee) { customerServiceFee1 = fee; }
    public static void setCustomerServiceFee2(double fee) { customerServiceFee2 = fee; }
    public static void setCustomerServiceFee3(double fee) { customerServiceFee3 = fee; }
    public static void setCustomerServiceFee4(double fee) { customerServiceFee4 = fee; }
    public static void setCustomerServiceFee5(double fee) { customerServiceFee5 = fee; }

    public static void defineTariff(double tier1, double tier2, double tier3, double tier4, double tier5, double tier6,
                                    double fee1, double fee2, double fee3, double fee4, double fee5) {
        String updateSQL = "UPDATE tariffs SET " +
                "tier1_rate = ?, tier2_rate = ?, tier3_rate = ?, tier4_rate = ?, tier5_rate = ?, tier6_rate = ?, " +
                "service_fee1 = ?, service_fee2 = ?, service_fee3 = ?, service_fee4 = ?, service_fee5 = ? " +
                "WHERE tariff_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setDouble(1, tier1);
            preparedStatement.setDouble(2, tier2);
            preparedStatement.setDouble(3, tier3);
            preparedStatement.setDouble(4, tier4);
            preparedStatement.setDouble(5, tier5);
            preparedStatement.setDouble(6, tier6);

            preparedStatement.setDouble(7, fee1);
            preparedStatement.setDouble(8, fee2);
            preparedStatement.setDouble(9, fee3);
            preparedStatement.setDouble(10, fee4);
            preparedStatement.setDouble(11, fee5);

            preparedStatement.setInt(12, 1);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Tariff data updated successfully.");
                loadTariffsFromDatabase();
            } else {
                System.out.println("No rows were updated. Please check the identifier.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
