package com.bigbang.googlebooksfirebase.network

import com.bigbang.googlebooksfirebase.model.BookResultSet
import com.bigbang.googlebooksfirebase.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksService {
    /*
    "https://www.googleapis.com/books/v1/volumes?q={search term}&key={YOUR_API_KEY}”
    */
    @GET(Constants.URL_POSTFIX)
    fun getGoogleBooksRx(@Query("q") search_terms: String?, @Query("key") api_key: String?): Observable<BookResultSet?>?
}
