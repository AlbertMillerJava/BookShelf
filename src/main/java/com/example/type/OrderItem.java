package com.example.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private int orderItemId;
    private Book book;
    private int amount;

}
