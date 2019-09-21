package com.example.type;

public class OrderItem {
    private int orderItemId;
    private Book book;
    private int amount;

    public int getOrderItemId() {
        return orderItemId;
    }

    public Book getBook() {
        return book;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
