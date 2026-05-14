package com.example.claims.repository;

import com.example.claims.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/claimsdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public User findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setEnabled(rs.getBoolean("enabled"));
                    return user;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur DB users", e);
        }

        return null;
    }
}