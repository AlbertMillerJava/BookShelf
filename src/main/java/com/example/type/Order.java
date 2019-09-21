package com.example.type;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Order {
    private  long orderId;
    private LocalDate orderDate;
    private Customer customer;
    private List<OrderItem> orderedItems;

}
