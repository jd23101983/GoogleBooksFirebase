package com.bigbang.googlebooksfirebase.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Books")
public class BookEntity {

    @PrimaryKey(autoGenerate = true)
    private int bookID;

    @ColumnInfo(name = "bookImageURL")
    private String bookImageURL;

    @ColumnInfo(name = "bookTitle")
    private String bookTitle;

    @ColumnInfo(name = "bookAuthors")
    private String bookAuthors;

    public BookEntity(int bookID, String bookImageURL, String bookTitle, String bookAuthors) {
        this.bookID = bookID;
        this.bookImageURL = bookImageURL;
        this.bookTitle = bookTitle;
        this.bookAuthors = bookAuthors;
    }

    @Ignore
    public BookEntity(String bookImageURL, String bookTitle, String bookAuthors) {
        this.bookImageURL = bookImageURL;
        this.bookTitle = bookTitle;
        this.bookAuthors = bookAuthors;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookImageURL() {
        return bookImageURL;
    }

    public void setBookImageURL(String bookImageURL) {
        this.bookImageURL = bookImageURL;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(String bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
