package org.raflab.studsluzba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnect {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/projekat?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "Karaburma69!";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}