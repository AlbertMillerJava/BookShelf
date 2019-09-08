package com.example.controller;

import com.example.storage.impl.PostgresBookStorageImpl;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.storage.BookStorage;
import com.example.storage.impl.StaticListBookStorageImpl;
import com.example.type.Book;

import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status.*;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;


public class BookController {
    private final static String BOOK_ID_PARAM_NAME = "bookId";
    public BookStorage bookStorage = new PostgresBookStorageImpl();

    public Response serveGetBookRequest(IHTTPSession session) {

        Map<String, List<String>> requestParameters = session.getParameters();

        if (requestParameters.containsKey(BOOK_ID_PARAM_NAME)) {
            List<String> bookIdParams = requestParameters.get(BOOK_ID_PARAM_NAME);
            String bookIdParam = bookIdParams.get(0);
            long bookId = 0;

            try {
                bookId = Long.parseLong(bookIdParam);
            } catch (NumberFormatException nfe) {
                return newFixedLengthResponse(BAD_REQUEST, "text/plain", "request id have to be a number");
            }

            Book book = bookStorage.getBook(bookId);

            if (book != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String response = objectMapper.writeValueAsString(book);
                    System.out.println(book.toString());
                    return newFixedLengthResponse(OK, "application/json", response);
                } catch (JsonProcessingException e) {
                    return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "cant get all books");
                }
            }

            return newFixedLengthResponse(NOT_FOUND, "application/json", "");
        }
        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Uncorrected request");
    }

    public Response serveGetAllBookRequest(IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";

        try {
            response = objectMapper.writeValueAsString(bookStorage.getAllBooks());
        } catch (JsonProcessingException j) {
            System.err.println("Error during process request : \n" + j);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error there is no books");
        }

        return newFixedLengthResponse(OK, "application/json", response);
    }

    public Response serveAddBookRequest(IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();

        String lengthHeader = session.getHeaders().get("content-length");
        int bookId = 0;
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];

        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            Book requestBook = objectMapper.readValue(requestBody, Book.class);
            bookId = bookStorage.addBook(requestBook);

        } catch (Exception e) {
            System.err.println("Error during process request  \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error book " +
                    "hasnt been added");
        }
        return newFixedLengthResponse(OK, "text/plain", "Book has been successfully added, id ="
                + bookId);
    }

    public BookStorage getBookStorage() {
        return bookStorage;
    }
}
