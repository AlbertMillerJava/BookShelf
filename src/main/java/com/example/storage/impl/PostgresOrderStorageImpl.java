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
        final String sqlListOfItems = "insert into order_items (order_item_id,order_id,book_id,amount) " +
                "values(nextval('items_sequence'),?,?,?);";

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
    public List<Order> getAllOrders() {
        return null;
    }
}
