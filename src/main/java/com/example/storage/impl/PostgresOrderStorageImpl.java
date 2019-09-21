package com.example.storage.impl;

import com.example.storage.OrderStorage;
import com.example.type.Order;
import com.example.type.OrderItem;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class PostgresOrderStorageImpl implements OrderStorage {


    @Override
    public Order getOrder(int orderId) {
        return null;
    }

    @Override
    public long addOrder(Order order) {
        final String sqlAddOrder = "insert into orders(order_id ,order_date ,customer_id)" +
                "Values (nextval('order_sequence'),current_date , ?) returning order_id;";
        final String sqlListOfItems = "insert into order_items (order_item_id,order_id,book_id,amount,is_active) " +
                "values(nextval('items_sequence'),?,?,?,true);";

        long orderId = 0;
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementAddItems = null;
        try {
            preparedStatement = connection.prepareStatement(sqlAddOrder);
            preparedStatementAddItems = connection.prepareStatement(sqlListOfItems);
            preparedStatement.setLong(1, order.getCustomer().getCustomerId());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }

            for (OrderItem orderItem : order.getOrderedItems()) {

                preparedStatementAddItems.setLong(1, orderId);
                preparedStatementAddItems.setLong(2, orderItem.getBook().getId());
                preparedStatementAddItems.setInt(3, orderItem.getAmount());
                preparedStatementAddItems.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query order section");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, preparedStatement);
            DatabaseConnection.closeDatabaseResources(connection, preparedStatementAddItems);
        }
        return orderId;
    }


    @Override
    public long changeOrder(Order order) {
        final String sqlDelete = " Update order_items set is_active = false where order_id=" + order.getOrderId();
        final String sqlAddNewItems = "Insert into order_items (order_item_id, order_id, book_id,amount,is_active)" +
                "Values (nextval('items_sequence')," + order.getOrderId() + ", ?,?,true)";

        long orderId = 0;

        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;
        try {
            updateStatement = connection.prepareStatement(sqlDelete);
            insertStatement = connection.prepareStatement(sqlAddNewItems);
            updateStatement.execute();

            for (OrderItem orderItem : order.getOrderedItems()) {
                insertStatement.setLong(1, orderItem.getBook().getId());
                insertStatement.setInt(2, orderItem.getAmount());
                insertStatement.executeUpdate();

            }
            ResultSet resultSet = updateStatement.getResultSet();
            while (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query order section");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, updateStatement);
            DatabaseConnection.closeDatabaseResources(connection, insertStatement);
        }
        return orderId;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}
