package com.example.type;

import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderData {

    int customerId;
    List<ItemData> orderItems;

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setOrderItems(List<ItemData> orderItems) {
        this.orderItems = orderItems;
    }
    public Order createOrder(){
        Order order  =  new Order();
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        order.setCustomer(customer);
        List<OrderItem> orderedItems = new ArrayList<>();
        for(ItemData itemData: orderItems){
            orderedItems.add(itemData.createOrderItem());
        }
        order.setOrderedItems(orderedItems);
        return order;
    }
}
