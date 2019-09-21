package com.example.storage;

import com.example.type.Customer;

import java.util.List;

public interface CustomerStorage {

    Customer getCustomer (int customerId);

    List<Customer> getAllCustomers();

    int addCustomer (Customer customer);
}
