package com.example.controller;

import com.example.storage.CustomerStorage;
import com.example.storage.impl.PostgresBookStorageImpl;
import com.example.storage.impl.PostgresCustomerStorageImpl;
import com.example.type.Book;
import com.example.type.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;

import fi.iki.elonen.*;

import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status.*;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class CustomerController {
    private final static String CUSTOMER_ID_PARAM_NAME = "customerId";
    public CustomerStorage customerStorage = new PostgresCustomerStorageImpl();

    public NanoHTTPD.Response serveGetCustomerRequest(NanoHTTPD.IHTTPSession session) {

        Map<String, List<String>> requestParameters = session.getParameters();

        if (requestParameters.containsKey(CUSTOMER_ID_PARAM_NAME)) {
            List<String> customerIdParams = requestParameters.get(CUSTOMER_ID_PARAM_NAME);
            String customerIdParam = customerIdParams.get(0);
            int customerId = 0;

            try {
                customerId = Integer.parseInt(customerIdParam);
            } catch (NumberFormatException nfe) {
                return newFixedLengthResponse(BAD_REQUEST, "text/plain", "request id have to be a number");
            }

            Customer customer = customerStorage.getCustomer(customerId);

            if (customer != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String response = objectMapper.writeValueAsString(customer);
                    System.out.println(customer.toString());
                    return newFixedLengthResponse(OK, "application/json", response);
                } catch (JsonProcessingException e) {
                    return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "cant get all books");
                }
            }

            return newFixedLengthResponse(NOT_FOUND, "application/json", "");
        }
        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Uncorrected request");
    }

    public NanoHTTPD.Response serveGetAllCustomerResponse(NanoHTTPD.IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";
        try {
            response = objectMapper.writeValueAsString(customerStorage.getAllCustomers());
        } catch (JsonProcessingException j) {
            System.err.println("Error during process request : \n" + j);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error there is no books");
        }

        return newFixedLengthResponse(OK, "application/json", response);
    }
    public  NanoHTTPD.Response serveAddCustomerResponse (NanoHTTPD.IHTTPSession session){

        ObjectMapper objectMapper = new ObjectMapper();

        String lengthHeader = session.getHeaders().get("content-length");
        int customerId = 0;
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];

        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            Customer requestCustomer = objectMapper.readValue(requestBody, Customer.class);
            customerId = customerStorage.addCustomer(requestCustomer);

        } catch (Exception e) {
            System.err.println("Error during process request  \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error customer " +
                    "hasnt been added");
        }
        return newFixedLengthResponse(OK, "text/plain", "Customer has been successfully added, id ="
                + customerId);
    }

}



