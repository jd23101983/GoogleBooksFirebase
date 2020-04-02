package com.bigbang.googlebooksfirebase.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookDAO {

    // User Queries
    @Insert
    void addNewBook(BookEntity newBook);

    @Delete
    void deleteBook(BookEntity deleteBook);

    @Query("SELECT * FROM Books WHERE bookID = :bookID")
    BookEntity selectBook(int bookID);

    @Update
    void updateBook(BookEntity bookEntity);
}

