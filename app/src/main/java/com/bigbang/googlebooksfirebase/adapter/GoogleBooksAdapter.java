package com.bigbang.googlebooksfirebase.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bigbang.googlebooksfirebase.R;
import com.bigbang.googlebooksfirebase.model.Book;
import com.bigbang.googlebooksfirebase.model.Item;
import com.bigbang.googlebooksfirebase.view.MainActivity;
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel;
import com.bumptech.glide.Glide;
import java.util.List;
import static com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug;

public class GoogleBooksAdapter extends RecyclerView.Adapter<GoogleBooksAdapter.BookViewHolder> {

    private GoogleBooksViewModel googleBooksViewModel;
    private List<Item> bookResults;
    private ViewGroup theParent;

    public GoogleBooksAdapter(List<Item> bookResults) {
        this.bookResults = bookResults;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        theParent = parent;
        googleBooksViewModel = ViewModelProviders.of((MainActivity)theParent.getContext()).get(GoogleBooksViewModel.class);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item_layout, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        //logDebug("Book ImageURL: " + bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail());
        //logDebug("Book Title: " + bookResults.get(position).getVolumeInfo().getTitle());
        //logDebug("Book Authors: " + bookResults.get(position).getVolumeInfo().getAuthors().toString());
        Animation slideInAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_in_animation);
        holder.itemView.startAnimation(slideInAnimation);

        //if (bookResults.get(position) != null) {
        try {
            //if (!bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail().equals(null)) {
            if (bookResults.get(position) != null) {

                Glide.with(theParent.getContext())
                        .load(bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail())
                        .placeholder(R.drawable.button_background)
                        .into(holder.bookImage);

                holder.bookTitle.setText(bookResults.get(position).getVolumeInfo().getTitle());
                holder.bookAuthors.setText(bookResults.get(position).getVolumeInfo().getAuthors().toString());
                holder.bookFavorite.setText("Favorite: ");

                holder.favoriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            logDebug("Favorite Selected");
                            googleBooksViewModel.addBookToFavorites(new Book(
                                    bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail(),
                                    bookResults.get(position).getVolumeInfo().getTitle(),
                                    bookResults.get(position).getVolumeInfo().getAuthors().toString() ));
                        }
                        else
                        {
                            logDebug("Favorite De-Selected");
/*
                            booksDB.getBookDAO().deleteBook(new BookEntity (
                                    bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail(),
                                    bookResults.get(position).getVolumeInfo().getTitle(),
                                    bookResults.get(position).getVolumeInfo().getAuthors().toString()));
*/
                        }
                    }
                });
            }
        }
        catch (Exception e) {e.printStackTrace(); }

    }

    @Override
    public int getItemCount() {
        return bookResults.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        private ImageView bookImage;
        private TextView bookTitle;
        private TextView bookAuthors;
        private TextView bookFavorite;
        private ToggleButton favoriteToggleButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title_textview);
            bookAuthors = itemView.findViewById(R.id.book_authors_textview);
            bookFavorite = itemView.findViewById(R.id.book_favorite_textview);
            favoriteToggleButton = itemView.findViewById(R.id.book_favorite_togglebutton);
        }
    }

}
