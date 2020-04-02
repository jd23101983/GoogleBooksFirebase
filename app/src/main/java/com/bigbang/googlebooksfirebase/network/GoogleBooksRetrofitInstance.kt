package com.bigbang.googlebooksfirebase.network

import com.bigbang.googlebooksfirebase.model.BookResultSet
import com.bigbang.googlebooksfirebase.util.Constants
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GoogleBooksRetrofitInstance {
    private val googleBooksService: GoogleBooksService
    private val client: OkHttpClient
    private val retrofitInstance: Retrofit
        private get() = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    private fun createGoogleBooksService(retrofitInstance: Retrofit): GoogleBooksService {
        return retrofitInstance.create(GoogleBooksService::class.java)
    }

    fun getGoogleBooksRx(search_terms: String?, api_key: String?): Observable<BookResultSet?>? {
        return googleBooksService.getGoogleBooksRx(search_terms, api_key)
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        googleBooksService = createGoogleBooksService(retrofitInstance)
    }
}