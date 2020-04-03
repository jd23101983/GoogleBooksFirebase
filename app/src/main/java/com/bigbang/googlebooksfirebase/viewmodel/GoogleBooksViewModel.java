package com.bigbang.googlebooksfirebase.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bigbang.googlebooksfirebase.database.FirebaseEvents;
import com.bigbang.googlebooksfirebase.model.Book;
import com.bigbang.googlebooksfirebase.model.BookResultSet;
import com.bigbang.googlebooksfirebase.network.GoogleBooksRetrofitInstance;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoogleBooksViewModel extends AndroidViewModel {

    private GoogleBooksRetrofitInstance googleBooksRetrofitInstance;
    private FirebaseEvents firebaseEvents;

    public GoogleBooksViewModel(@NonNull Application application) {
        super(application);

        googleBooksRetrofitInstance = new GoogleBooksRetrofitInstance();
        firebaseEvents = new FirebaseEvents();
    }

    public Observable<BookResultSet> getGoogleBooksRx(String search_terms, String api_key) {
        return  googleBooksRetrofitInstance
                .getGoogleBooksRx(search_terms, api_key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public void addBookToFavorites (Book book) {
        firebaseEvents.addNewBook(book);
    }

    public Observable<List<Book>> getBookList() {

        Log.d("TAG_X", "inside HotelViewModel::getGuestList() . . . returning");

        return  firebaseEvents
                .getBookList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
