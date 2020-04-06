package com.bigbang.googlebooksfirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bigbang.googlebooksfirebase.R
import com.bigbang.googlebooksfirebase.adapter.GoogleBooksAdapter.BookViewHolder
import com.bigbang.googlebooksfirebase.model.Book
import com.bigbang.googlebooksfirebase.model.Item
import com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug
import com.bigbang.googlebooksfirebase.view.MainActivity
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel
import com.bumptech.glide.Glide

class GoogleBooksAdapter(private val bookResults: List<Item?>) : RecyclerView.Adapter<BookViewHolder>() {
    private var googleBooksViewModel: GoogleBooksViewModel? = null
    private var theParent: ViewGroup? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        theParent = parent
        googleBooksViewModel = ViewModelProviders.of((theParent!!.context as MainActivity)).get(GoogleBooksViewModel::class.java)
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_item_layout, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) { //logDebug("Book ImageURL: " + bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail());
//logDebug("Book Title: " + bookResults.get(position).getVolumeInfo().getTitle());
//logDebug("Book Authors: " + bookResults.get(position).getVolumeInfo().getAuthors().toString());
        val slideInAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_in_animation)
        holder.itemView.startAnimation(slideInAnimation)
        //if (bookResults.get(position) != null) {
        try { //if (!bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail().equals(null)) {
            if (bookResults[position] != null) {
                Glide.with(theParent!!.context)
                        .load(bookResults[position]!!.volumeInfo.imageLinks.thumbnail)
                        .placeholder(R.drawable.button_background)
                        .into(holder.bookImage)
                holder.bookTitle.text = bookResults[position]!!.volumeInfo.title
                holder.bookAuthors.text = bookResults[position]!!.volumeInfo.authors.toString()
                holder.bookFavorite.text = "Favorite: "
                holder.favoriteToggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        logDebug("Favorite Selected")
                        googleBooksViewModel!!.addBookToFavorites(Book(
                                bookResults[position]!!.volumeInfo.imageLinks.thumbnail,
                                bookResults[position]!!.volumeInfo.title,
                                bookResults[position]!!.volumeInfo.authors.toString()))
                    } else {
                        logDebug("Favorite De-Selected")
                        /*
            todo: firebase implementation

                                    booksDB.getBookDAO().deleteBook(new BookEntity (
                                            bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail(),
                                            bookResults.get(position).getVolumeInfo().getTitle(),
                                            bookResults.get(position).getVolumeInfo().getAuthors().toString()));
        */
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return bookResults.size
    }

    inner class BookViewHolder(itemView: View) : ViewHolder(itemView) {
        val bookImage: ImageView
        val bookTitle: TextView
        val bookAuthors: TextView
        val bookFavorite: TextView
        val favoriteToggleButton: ToggleButton

        init {
            bookImage = itemView.findViewById(R.id.book_image)
            bookTitle = itemView.findViewById(R.id.book_title_textview)
            bookAuthors = itemView.findViewById(R.id.book_authors_textview)
            bookFavorite = itemView.findViewById(R.id.book_favorite_textview)
            favoriteToggleButton = itemView.findViewById(R.id.book_favorite_togglebutton)
        }
    }

}