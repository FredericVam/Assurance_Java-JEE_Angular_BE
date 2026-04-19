package com.example.claims.repository;

import com.example.claims.entity.Contract;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContractRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/claimsdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public List<Contract> findAll() {
        List<Contract> contracts = new ArrayList<>();

        String sql = """
                SELECT id, contract_number, subscriber_name, contract_type, status, start_date
                FROM contracts
                ORDER BY id
                """;

        try {
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    Contract contract = new Contract();
                    contract.setId(rs.getLong("id"));
                    contract.setContractNumber(rs.getString("contract_number"));
                    contract.setSubscriberName(rs.getString("subscriber_name"));
                    contract.setContractType(rs.getString("contract_type"));
                    contract.setStatus(rs.getString("status"));

                    java.sql.Date sqlDate = rs.getDate("start_date");
                    if (sqlDate != null) {
                        contract.setStartDate(sqlDate.toLocalDate());
                    }

                    contracts.add(contract);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture des contrats PostgreSQL", e);
        }

        return contracts;
    }
}