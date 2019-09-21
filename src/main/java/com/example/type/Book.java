package com.example.type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Book {
    private long id;
    private String title;
    private String author;
    private Integer pageSum;
    private Integer yearOfPublish;
    private String publishingHouse;

}
