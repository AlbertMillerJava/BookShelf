package com.example.type;

public class OrderItems {
    private int orderItemId;
    private int orderId;
    private int bookId;
    private int amount;

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
