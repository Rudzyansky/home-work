package com.sbrf.reboot.repository.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sbrf.reboot.dto.Customer;
import com.sbrf.reboot.repository.CustomerRepository;
import lombok.NonNull;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerH2Repository implements CustomerRepository {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/my_db";

    private static final String USER = "sa";
    private static final String PASS = "";

    private final DataSource dataSource;

    @SneakyThrows
    public CustomerH2Repository() {

        // This must be receive via DI (eg. Dagger)
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(JDBC_DRIVER);
        cpds.setJdbcUrl(DB_URL);
        cpds.setUser(USER);
        cpds.setPassword(PASS);
        cpds.setMaxStatements(100);

        dataSource = cpds;

        createTableIfNotExists();
    }

    @SneakyThrows
    void createTableIfNotExists() {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `CUSTOMERS` (" +
                    "`ID` INT PRIMARY KEY AUTO_INCREMENT, " +
                    "`NAME` VARCHAR(255) NOT NULL UNIQUE, " +
                    "`EMAIL` VARCHAR(255))");
        }
    }

    @SneakyThrows
    void dropTable() {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS `CUSTOMERS`");
        }
    }

    @SneakyThrows
    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT `ID`, `NAME`, `EMAIL` FROM `CUSTOMERS`")) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("ID"));
                customer.setName(rs.getString("NAME"));
                customer.setEMail(rs.getString("EMAIL"));
                customers.add(customer);
            }
        }

        return customers;
    }

    @SneakyThrows
    @Override
    public boolean createCustomer(String name, String eMail) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO `CUSTOMERS` (`NAME`, `EMAIL`) " +
                     "SELECT ?, ? WHERE NOT EXISTS(SELECT * FROM `CUSTOMERS` WHERE `NAME` = ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, eMail);
            stmt.setString(3, name);
            int affected = stmt.executeUpdate();
            return affected > 0;
        }
    }

    @SneakyThrows
    @Override
    public boolean updateCustomerEMailByUserName(@NonNull String userName, String eMail) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE `CUSTOMERS` SET `EMAIL` = ? WHERE `NAME` = ?")) {
            stmt.setString(1, eMail);
            stmt.setString(2, userName);
            int affected = stmt.executeUpdate();
            return affected > 0;
        }
    }

    @SneakyThrows
    @Override
    public boolean isCustomerExists(@NonNull String userName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT EXISTS(SELECT * FROM `CUSTOMERS` WHERE `NAME` = ?)")) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.first()) throw new SQLException("Not found rows in SELECT EXISTS(...)");
                return rs.getBoolean(1);
            }
        }
    }
}
