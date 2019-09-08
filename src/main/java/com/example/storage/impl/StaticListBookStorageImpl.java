package com.example.storage.impl;

import com.example.storage.BookStorage;
import com.example.type.Book;

import java.util.ArrayList;
import java.util.List;

public class StaticListBookStorageImpl implements BookStorage {
    private static List<Book> bookStorage = new ArrayList<Book>();


    public Book getBook(long id) {
        for (Book book : bookStorage) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return bookStorage;
    }

    public int addBook(Book book) {
        bookStorage.add(book);
        return (int)book.getId();
    }

}
