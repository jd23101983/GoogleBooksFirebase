package com.bigbang.googlebooksfirebase.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = { BookEntity.class })
public abstract class BooksDB extends RoomDatabase {
    public abstract BookDAO getBookDAO();
}
