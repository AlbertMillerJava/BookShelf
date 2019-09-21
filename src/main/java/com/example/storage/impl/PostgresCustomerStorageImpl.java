package com.example.storage.impl;

import com.example.storage.CustomerStorage;
import com.example.type.Customer;
import com.example.type.Customer;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.DatabaseConnection.initializeDataBaseConnection;

public class PostgresCustomerStorageImpl implements CustomerStorage {


    @Override
    public Customer getCustomer(int customerId) {
        final String getOneBook = "SELECT * FROM customers WHERE customer_id= ?;";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(getOneBook);
            preparedStatement.setLong(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setName(resultSet.getString("name"));

                return customer;
            }
        } catch (SQLException s) {
            System.err.println("Cant stand writing these exceptions anymore .." + s.getMessage());
            throw new RuntimeException("Cant stand writing these exceptions anymore ..");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, preparedStatement);
        }
    return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        final String getAll = "SELECT * FROM customers";
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        Statement statement = null;
        List<Customer> customers = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                Customer customer = new Customer();

                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setName(resultSet.getString("name"));

                customers.add(customer);
            }
        } catch (SQLException s) {
            System.err.println("Failed in geting sql query in getallcustomers" + s.getMessage());
            throw new RuntimeException("Failed in geting sql query in getallcustomers");

        } finally {
            DatabaseConnection.closeDatabaseResources(connection, statement);
        }
        return customers;
    }

    @Override
    public int addCustomer(Customer customer) {
        final String sqlInsertCustomer = "INSERT INTO customers (customer_id, name) VALUES (nextval('sequence'),?) " +
                "returning customer_id;";
        int customerId = 0;
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlInsertCustomer);

            preparedStatement.setString(1, customer.getName());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                customerId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, preparedStatement);
        }

        return customerId;
    }
}
