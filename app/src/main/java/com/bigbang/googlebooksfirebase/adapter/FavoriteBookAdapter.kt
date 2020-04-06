package com.bigbang.googlebooksfirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bigbang.googlebooksfirebase.R
import com.bigbang.googlebooksfirebase.adapter.FavoriteBookAdapter.FavoriteBookViewHolder
import com.bigbang.googlebooksfirebase.model.Book
import com.bigbang.googlebooksfirebase.view.MainActivity
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel
import com.bumptech.glide.Glide

class FavoriteBookAdapter(private val bookResults: List<Book?>) : RecyclerView.Adapter<FavoriteBookViewHolder>() {
    private var googleBooksViewModel: GoogleBooksViewModel? = null
    private var theParent: ViewGroup? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBookViewHolder {
        theParent = parent
        googleBooksViewModel = ViewModelProviders.of((theParent!!.context as MainActivity)).get(GoogleBooksViewModel::class.java)
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_book_item_layout, parent, false)
        return FavoriteBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteBookViewHolder, position: Int) { //logDebug("title = " + bookResults.get(position).getTitle());
        val rotateSlideAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rotate_slide_animation)
        holder.itemView.startAnimation(rotateSlideAnimation)
        try {
            if (bookResults[position] != null) {
                Glide.with(theParent!!.context)
                        .load(bookResults[position]!!.imageURL)
                        .placeholder(R.drawable.button_background)
                        .into(holder.bookImage)
                holder.bookTitle.text = bookResults[position]!!.title
                holder.bookAuthors.text = bookResults[position]!!.authors
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return bookResults.size
    }

    inner class FavoriteBookViewHolder(itemView: View) : ViewHolder(itemView) {
        val bookImage: ImageView
        val bookTitle: TextView
        val bookAuthors: TextView

        init {
            bookImage = itemView.findViewById(R.id.fav_book_image)
            bookTitle = itemView.findViewById(R.id.fav_book_title_textview)
            bookAuthors = itemView.findViewById(R.id.fav_book_authors_textview)
        }
    }

}