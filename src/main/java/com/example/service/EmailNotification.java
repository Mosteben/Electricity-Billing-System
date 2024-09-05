package com.example.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailNotification {
    public static void sendEmail(String to, String subject, String body) {
        String from = "your_email@example.com";
        String host = "smtp.example.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
