package com.example.bookshelf;
//lombok biblioteka wlaczyc adnotation processing
//j unit, assertg ,vavr

import com.example.controller.BookController;
import com.example.controller.CustomerController;
import com.example.controller.OrderController;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;


import static fi.iki.elonen.NanoHTTPD.Method.*;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;

public class RequestUrlMapper {
    private final static String ADD_BOOK_URL = "/book/add";
    private final static String GET_BOOK_URL = "/book/get";
    private final static String GET_ALL_BOOK_URL = "/book/getAll";

    private final static String ADD_CUSTOMER_URL = "/customer/add";
    private final static String GET_CUSTOMER_URL = "/customer/get";
    private final static String GET_ALL_CUSTOMER_URL = "/customer/getAll";

    private final static String ADD_ORDER_URL = "/order/add";
    private final static String DELETE_ITEM_FROM_ORDER_URL = "/order/put";

    private BookController bookController = new BookController();
    private CustomerController customerController = new CustomerController();
    private OrderController orderController = new OrderController();


    public Response delegateRequest(NanoHTTPD.IHTTPSession session) {
        if(GET.equals(session.getMethod()) && GET_CUSTOMER_URL.equals(session.getUri())){
            return customerController.serveGetCustomerRequest(session);}
        else if(GET.equals(session.getMethod() )&& GET_ALL_CUSTOMER_URL.equals(session.getUri())){
            return customerController.serveGetAllCustomerResponse(session);
        }else if(POST.equals(session.getMethod()) && ADD_CUSTOMER_URL.equals(session.getUri()) ){
            return customerController.serveAddCustomerResponse(session);
        }
        if (GET.equals(session.getMethod()) && GET_BOOK_URL.equals(session.getUri())) {
            return bookController.serveGetBookRequest(session);
        } else if (GET.equals(session.getMethod()) && GET_ALL_BOOK_URL.equals(session.getUri())) {
            return bookController.serveGetAllBookRequest(session);
        } else if (POST.equals(session.getMethod()) && ADD_BOOK_URL.equals(session.getUri())) {
            return bookController.serveAddBookRequest(session);
        }
        if(POST.equals(session.getMethod()) && ADD_ORDER_URL.equals(session.getUri())){
            return  orderController.serveAddOrderRequest(session);
        }else if (PUT.equals(session.getMethod()) && DELETE_ITEM_FROM_ORDER_URL.equals(session.getUri())){
            return  orderController.serveChangeOrderRequest(session);
        }
        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain", "Not Found");
    }

    public BookController getBookController() {
        return bookController;
    }
}
