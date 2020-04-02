package com.bigbang.googlebooksfirebase.model;

public class Book {

    private String imageURL;
    private String title;
    private String authors;

    public Book(String imageURL, String title, String authors) {
        this.imageURL = imageURL;
        this.title = title;
        this.authors = authors;
    }

    public Book() {}

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
