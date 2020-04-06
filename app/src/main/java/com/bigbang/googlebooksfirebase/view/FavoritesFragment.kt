package com.bigbang.googlebooksfirebase.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigbang.googlebooksfirebase.R
import com.bigbang.googlebooksfirebase.adapter.FavoriteBookAdapter
import com.bigbang.googlebooksfirebase.model.Book
import com.bigbang.googlebooksfirebase.util.DebugLogger.logError
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class FavoritesFragment : Fragment() {
    private var googleBooksViewModel: GoogleBooksViewModel? = null
    private val compositeDisposable = CompositeDisposable()
    private val bookList: List<Book> = ArrayList()
    private var favoriteBookTitle: TextView? = null
    private var favoriteBookRecyclerView: RecyclerView? = null
    private lateinit var favoriteBackButton: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleBooksViewModel = ViewModelProviders.of(this).get(GoogleBooksViewModel::class.java)
        compositeDisposable.add(googleBooksViewModel!!.bookList.subscribe({ bookList: List<Book?> ->
            Log.d("TAG_X", "BookResults size() = " + bookList.size)
            displayBooks(bookList)
        }) { throwable: Throwable? -> logError(throwable!!) })
        favoriteBookTitle = view.findViewById(R.id.favorite_books_textView)
        favoriteBookRecyclerView = view.findViewById(R.id.favorite_books_recycler_view)
        favoriteBackButton = view.findViewById(R.id.back_button)
        favoriteBackButton.setOnClickListener(View.OnClickListener { (context as MainActivity?)!!.backFromFavorites() })
    }

    fun displayBooks(newBookList: List<Book?>?) { //logDebug("newBookList size = " + newBookList.size());
        val favoriteBookAdapter = FavoriteBookAdapter(newBookList!!)
        favoriteBookRecyclerView!!.layoutManager = LinearLayoutManager(context)
        favoriteBookRecyclerView!!.adapter = favoriteBookAdapter
    }
}