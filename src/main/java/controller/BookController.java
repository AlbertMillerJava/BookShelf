package controller;

import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import storage.BookStorage;
import storage.impl.StaticListBookStorageImpl;

import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;


public class BookController {

    private BookStorage bookStorage = new StaticListBookStorageImpl();

    public Response serveGetBookRequest (IHTTPSession session){
        return null;
    }
    public Response serveGetAllBookRequest (IHTTPSession session){
        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";
        try{
            response = objectMapper.writeValueAsString(bookStorage.getAllBooks());
        }catch (JsonProcessingException j){
            System.err.println("Error during process request : \n" + j);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain","Internall error cant read all book");
        }
        return newFixedLengthResponse(OK,"application/json", response);
    }
    public Response serveAddBookRequest (IHTTPSession session){
        return null;
    }
}
