package com.example.controller;

import com.example.storage.CustomerStorage;
import com.example.storage.OrderStorage;
import com.example.storage.impl.PostgresCustomerStorageImpl;
import com.example.storage.impl.PostgresOrderStorageImpl;
import com.example.type.Order;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import utils.dataTransferObjects.OrderData;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import utils.dataTransferObjects.OrderDataId;

import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class OrderController {
    private final static  String ORDER_ID_PARAM_NAME = "orderId";
    private OrderStorage orderStorage = new PostgresOrderStorageImpl();
    private CustomerStorage customerStorage = new PostgresCustomerStorageImpl();

    public NanoHTTPD.Response serveAddOrderRequest(NanoHTTPD.IHTTPSession session){

        ObjectMapper objectMapper = new ObjectMapper();
        String lengthHeader = session.getHeaders().get("content-length");
        long orderId = 0;
        long customerId = 0;
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];
        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            OrderData requestOrderData = objectMapper.readValue(requestBody, OrderData.class);
            Order requestOrder = requestOrderData.createOrder();
            orderId = orderStorage.addOrder(requestOrder);

        } catch (Exception e) {
            System.err.println("Error during process request  \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error order  " +
                    "hasnt been added");
        }
        return newFixedLengthResponse(OK, "text/plain", "Order has been successfully added, id ="
                + orderId);
    }

    public NanoHTTPD.Response serveChangeOrderRequest(NanoHTTPD.IHTTPSession session){

        ObjectMapper objectMapper = new ObjectMapper();
        String lengthHeader = session.getHeaders().get("content-length");
        long orderId = 0;
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];
        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();


            OrderDataId requestOrderDataId = objectMapper.readValue(requestBody, OrderDataId.class);

            System.out.println("Blada");
            Order requestOrder = requestOrderDataId.createOrderById();

            orderId = orderStorage.changeOrder(requestOrder);

        } catch (Exception e) {
            System.err.println("Error during process request  \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error order  " +
                    "hasnt been added");
        }
        return newFixedLengthResponse(OK, "text/plain", "Order has been successfully added, id ="
                + orderId);
    }
}
