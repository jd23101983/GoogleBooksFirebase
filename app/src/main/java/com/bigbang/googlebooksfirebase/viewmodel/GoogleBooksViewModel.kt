package com.bigbang.googlebooksfirebase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bigbang.googlebooksfirebase.database.FirebaseEvents
import com.bigbang.googlebooksfirebase.model.Book
import com.bigbang.googlebooksfirebase.model.BookResultSet
import com.bigbang.googlebooksfirebase.network.GoogleBooksRetrofitInstance
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GoogleBooksViewModel(application: Application) : AndroidViewModel(application) {
    private val googleBooksRetrofitInstance: GoogleBooksRetrofitInstance
    private val firebaseEvents: FirebaseEvents
    fun getGoogleBooksRx(search_terms: String?, api_key: String?): Observable<BookResultSet?>? {
        return googleBooksRetrofitInstance
                .getGoogleBooksRx(search_terms, api_key)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
    }

    fun addBookToFavorites(book: Book?) {
        firebaseEvents.addNewBook(book)
    }

    val bookList: Observable<List<Book>>
        get() = firebaseEvents
                .bookList
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

    init {
        googleBooksRetrofitInstance = GoogleBooksRetrofitInstance()
        firebaseEvents = FirebaseEvents()
    }
}