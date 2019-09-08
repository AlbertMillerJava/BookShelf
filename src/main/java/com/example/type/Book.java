package com.example.type;

public class Book {
    private long id;
    private String title;
    private String author;
    private Integer pageSum;
    private Integer yearOfPublish;
    private String publishingHouse;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageSum(Integer pageSum) {
        this.pageSum = pageSum;
    }

    public void setYearOfPublish(Integer yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPageSum() {
        return pageSum;
    }

    public Integer getYearOfPublish() {
        return yearOfPublish;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    @Override
    public String toString() {

        return "id: " + id + " tytul: "+title;
    }
}
