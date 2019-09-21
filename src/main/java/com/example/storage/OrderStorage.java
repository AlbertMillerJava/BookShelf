package com.example.storage;

import com.example.type.Order;

import java.util.List;

public interface OrderStorage {

    Order getOrder(int orderId);

    int addOrder(Order order);

    List<Order> getAllOrders();
}
