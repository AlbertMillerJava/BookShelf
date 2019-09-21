package com.example.type;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private  int orderId;
    private LocalDateTime orderDate;
    private int customer_id;
    private List<OrderItems> orderedItems;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public List<OrderItems> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderItems> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
