package com.example.storage;

import com.example.type.Order;
import com.example.type.OrderItems;

import java.util.List;

public interface OrderStorage {

    Order getOrder(int orderId);

    int addOrder(List<OrderItems> orderedItems);

    List<Order> getAllOrders();
}
