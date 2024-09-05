package com.example.ui.Operator;

import com.example.service.BillCalculator;
import javax.swing.*;
import java.awt.*;

public class DefineTariffPanel extends JPanel {

    public DefineTariffPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(255, 255, 255)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField[] tierRateFields = new JTextField[6];
        for (int i = 0; i < 6; i++) {
            addLabelAndField(contentPanel, gbc, "Tier " + (i + 1) + " Rate:", i);
            tierRateFields[i] = addField(contentPanel, gbc, i);
        }

        JTextField[] feeFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            addLabelAndField(contentPanel, gbc, "Service Fee " + (i + 1) + ":", i + 6);
            feeFields[i] = addField(contentPanel, gbc, i + 6);
        }

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveButton = new JButton("Save Tariff");
        saveButton.setBackground(new Color(0, 51, 153)); // Dark blue background
        saveButton.setForeground(Color.WHITE); // White text
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveTariffs(tierRateFields, feeFields));
        contentPanel.add(saveButton, gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(0, 51, 102)); // Dark blue color for text
        panel.add(label, gbc);
    }

    private JTextField addField(JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 1;
        gbc.gridy = row;
        JTextField field = new JTextField(10);
        field.setBackground(Color.WHITE);
        panel.add(field, gbc);
        return field;
    }

    private void saveTariffs(JTextField[] tierRateFields, JTextField[] feeFields) {
        try {
            double[] tiers = new double[6];
            double[] fees = new double[5];

            for (int i = 0; i < 6; i++) {
                tiers[i] = Double.parseDouble(tierRateFields[i].getText());
            }

            for (int i = 0; i < 5; i++) {
                fees[i] = Double.parseDouble(feeFields[i].getText());
            }

            BillCalculator.defineTariff(tiers[0], tiers[1], tiers[2], tiers[3], tiers[4], tiers[5],
                    fees[0], fees[1], fees[2], fees[3], fees[4]);

            JOptionPane.showMessageDialog(this, "Tariffs updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {

    }
}
