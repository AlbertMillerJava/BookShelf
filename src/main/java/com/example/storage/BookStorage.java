package com.example.storage;

import com.example.type.Book;

import java.util.List;

public interface BookStorage {

    Book getBook(long id);

    List<Book> getAllBooks();

    int addBook (Book book);

}
