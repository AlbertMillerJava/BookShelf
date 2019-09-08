package com.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class BookShelfAppTest {

    private static final String BOOK_1 = "{\"title\":\"lalala2\",\"author\":\"polska\"," +
            "\"pageSum\":1,\"yearOfPublish\":-29,\"publishingHouse\":\"PWN\"}";
    private static final String BOOK_2 = "{\"title\":\"bylejako\",\"author\":\"jakistam\"," +
            "\"pageSum\":1000,\"yearOfPublish\":2020,\"publishingHouse\":\"GITprodukcja\"}";
    private static final int APP_PORT = 8090;
    private BookShelfApp bookShelfApp;

    @BeforeAll
    public static void beforeAll() {
        RestAssured.port = APP_PORT;
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        bookShelfApp = new BookShelfApp(APP_PORT);
    }

    @AfterEach
    public void afterEach() {
        bookShelfApp.getRequestUrlMapper().getBookController().getBookStorage().getAllBooks().clear();
        bookShelfApp.stop();
    }

    @Test
    public void addMethod_correct_shouldReturnStatus200() throws Exception {
        System.out.println("wejscie");
        with().body(BOOK_1).when().post("/book/add").then().statusCode(200).body(startsWith("Book has been " +
                "successfully added, id ="));
    }

    @Test
    public void addMethod_fieldTypeMismatch_shouldReturn500() {
        String bookWithFieldTypeMismatch = "{\"title\":\"lalala2\",\"author\":\"polska\",\"pageSum\":\"1 page\"," +
                "\"yearOfPublish\":-29,\"publishingHouse\":\"PWN\"}";
        with().body(bookWithFieldTypeMismatch).when().post("/book/add").then().statusCode(500);
    }

    @Test
    public void addMethod_unexpectedField_shouldReturn500() {
        with().body("{\"numberOfChapters\":10000}").when().post("/book/add").then().statusCode(500);
    }

    private long addBookAndGetId(String json) {
        String response = with().body(json)
                .when().post("/book/add")
                .then().statusCode(200)
                .body(startsWith("Book has been successfully added, id ="))
                .extract().body().asString();
        String strId = response.replace("Book has been successfully added, id =", "");
        return Long.parseLong(strId);
    }

    @Test
    public void getMethod_correctParams_shouldReturn200() {
        long book1Id = addBookAndGetId(BOOK_1);
        with().param("bookId", book1Id)
                .when().get("/book/get")
                .then().statusCode(200)
                .body("id", equalTo(book1Id))
                .body("title", equalTo("lalala2"))
                .body("author", equalTo("polska"))
                .body("pageSum", equalTo(1))
                .body("yearOfPublish", equalTo(-29))
                .body("publishingHouse", equalTo("PWN"));
    }

    @Test
    public void getMethod_noId_ShouldReturn400() {
        with().get("/book/get").then().statusCode(400).body(equalTo("Uncorrected request"));

    }

    @Test
    public void getMethod_IdAsString_ShouldReturn400() {
        with().param("bookId", "abc")
                .when().get("/book/get")
                .then().statusCode(400)
                .body(equalTo("request id have to be a number"));
    }

    @Test
    public void getMethod_noExistingId_shouldReturn404() {
        with().param("bookId", 1234567).when().get("/book/get")
                .then().statusCode(404);
    }

    @Test
    public void getAllMethod_noBook_shouldReturn() {

        with().get("/book/getAll").then().statusCode(200).body("", hasSize(0));
    }

    @Test
    public void getAllMethhod_1Book_shouldReturn200() {
        long bookId = addBookAndGetId(BOOK_1);

        with().get("/book/getAll").then().statusCode(200)
                .body("", hasSize(1))
                .body("id", hasItem(bookId))
                .body("title", hasItem("lalala2"))
                .body("author", hasItem("polska"))
                .body("pageSum", hasItem(1))
                .body("yearOfPublish", hasItem(-29))
                .body("publishingHouse", hasItem("PWN"));
    }

    @Test
    public void getAllMethod_2Books_shouldReturn200() {
        long book1ID = addBookAndGetId(BOOK_1);
        long book2Id = addBookAndGetId(BOOK_2);

        with().get("/book/getAll").then().statusCode(200)
                .body("", hasSize(2))
                .body("id", hasItems(book1ID, book2Id))
                .body("title", hasItems("lalala2", "bylejako"))
                .body("author", hasItems("polska", "jakistam"))
                .body("pageSum", hasItems(1, 1000))
                .body("yearOfPublish", hasItems(-29, 2020))
                .body("publishingHouse", hasItems("PWN", "GITprodukcja"));
    }


}