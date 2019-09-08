package com.example.storage.impl;

import com.example.storage.BookStorage;
import com.example.type.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresBookStorageImpl implements BookStorage {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/book_shelf";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASS = "password";

    static {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException cnf) {
            System.err.println("Cant find postgresql driver ");
        }
    }

    private Connection initializeDataBaseConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        } catch (SQLException sql) {
            System.err.println("Server cant initialize connection to databse");
            throw new RuntimeException("Server cant initialize connection to databse");
        }
    }

    private void closeDatabaseResources(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException s) {
            System.err.println("Error during closing databse resources");
            throw new RuntimeException("Error during closing databse resources");
        }

    }

    public Book getBook(long id) {
        final String getOneBook = "SELECT * FROM books WHERE book_id= ?;";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(getOneBook);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPageSum(resultSet.getInt("page_sum"));
                book.setYearOfPublish(resultSet.getInt("year_of_published"));
                book.setPublishingHouse(resultSet.getString("publishing_house"));
                return book;
            }
        } catch (SQLException s) {
            System.err.println("Cant stand writing these exceptions anymore .." + s.getMessage());
            throw new RuntimeException("Cant stand writing these exceptions anymore ..");
        } finally {
            closeDatabaseResources(connection, preparedStatement);
        }

        return null;
    }

    public List<Book> getAllBooks() {
        final String getAll = "SELECT * FROM books";
        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Book> books = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                Book book = new Book();

                book.setId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPageSum(resultSet.getInt("page_sum"));
                book.setYearOfPublish(resultSet.getInt("year_of_published"));
                book.setPublishingHouse(resultSet.getString("publishing_house"));

                books.add(book);
            }
        } catch (SQLException s) {
            System.err.println("Failed in geting sql query in getallbooks" + s.getMessage());
            throw new RuntimeException("Failed in geting sql query in getallbooks");

        } finally {
            closeDatabaseResources(connection, statement);
        }
        return books;
    }

    public int addBook(Book book) {

        final String sqlInsertBook = "INSERT INTO books( " + "book_id, title, author, page_sum, year_of_published," +
                " publishing_house) "
                + "VALUES (nextval('sequence'),?,?,?,?,?) returning book_id;";
        int book_id = 2;
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement(sqlInsertBook);
           // preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPageSum());
            preparedStatement.setInt(4, book.getYearOfPublish());
            preparedStatement.setString(5, book.getPublishingHouse());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                book_id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query");
        } finally {
            closeDatabaseResources(connection, preparedStatement);
        }
        System.out.println(book_id);
        return book_id;

    }
}
