package com.bigbang.googlebooksfirebase.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigbang.googlebooksfirebase.R
import com.bigbang.googlebooksfirebase.adapter.GoogleBooksAdapter
import com.bigbang.googlebooksfirebase.model.BookResultSet
import com.bigbang.googlebooksfirebase.model.Item
import com.bigbang.googlebooksfirebase.util.Constants
import com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug
import com.bigbang.googlebooksfirebase.util.DebugLogger.logError
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel
import com.google.firebase.database.DatabaseReference
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {
    private var googleBooksViewModel: GoogleBooksViewModel? = null
    private val compositeDisposable = CompositeDisposable() // RxJava
    private var favoritesFragment = FavoritesFragment()
    // TODO: remove
    private val reference: DatabaseReference? = null
    private var bookResultsRecyclerView: RecyclerView? = null
    private var searchEditText: EditText? = null
    private lateinit var searchButton: Button
    private lateinit var favoritesButton: Button
    private lateinit var animationButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        googleBooksViewModel = ViewModelProviders.of(this).get(GoogleBooksViewModel::class.java)
        favoritesFragment = FavoritesFragment()
        bookResultsRecyclerView = findViewById(R.id.book_results_recycler_view)
        searchEditText = findViewById(R.id.search_edittext)
        searchButton = findViewById(R.id.search_button)
        searchButton.setOnClickListener(View.OnClickListener { performSearch() })
        favoritesButton = findViewById(R.id.favorites_button)
        favoritesButton.setOnClickListener(View.OnClickListener { showFavoritesFragment() })
        animationButton = findViewById(R.id.animation_button)
        animationButton.setOnClickListener(View.OnClickListener { showAnimationActivity() })
    }

    fun performSearch() {
        val search_terms = searchEditText!!.text.toString().trim { it <= ' ' }
        searchEditText!!.setText("")
        searchEditText!!.clearFocus()
        // RxJava
        compositeDisposable.add(googleBooksViewModel!!.getGoogleBooksRx(search_terms, Constants.API_KEY)!!.subscribe(Consumer<BookResultSet?> { googleBookResults: BookResultSet? ->
            if (googleBookResults != null) {
                displayInformationRx(googleBookResults.items)
            }
        }, Consumer { throwable: Throwable? -> logError(throwable!!) }))
    }

    fun showFavoritesFragment() {
        favoritesFragment = FavoritesFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.favorite_book_frame, favoritesFragment)
                .commit()
    }

    fun showAnimationActivity() {
        val intent = Intent(this, AnimationActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    // RxJava
    private fun displayInformationRx(googleBookResults: List<Item?>) {
        updateRecyclerView(googleBookResults)
        for (i in googleBookResults.indices) { //DebugLogger.logDebug("RxJava : " + googleBookResults.get(i).getVolumeInfo().getImageLinks().getThumbnail());
            if (googleBookResults[i] != null) {
                logDebug("RxJava : " + googleBookResults[i]!!.volumeInfo.title)
                logDebug("RxJava : " + googleBookResults[i]!!.volumeInfo.authors)
                logDebug("RxJava : " + googleBookResults[i]!!.volumeInfo.description)
            }
        }
    }

    private fun updateRecyclerView(googleBookResults: List<Item?>) {
        val adapter = GoogleBooksAdapter(googleBookResults)
        bookResultsRecyclerView!!.layoutManager = LinearLayoutManager(this)
        bookResultsRecyclerView!!.adapter = adapter
    }

    fun backFromFavorites() {
        supportFragmentManager
                .beginTransaction()
                .remove(favoritesFragment)
                .commit()
    }
}