package com.bigbang.googlebooksfirebase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bigbang.googlebooksfirebase.R;
import com.bigbang.googlebooksfirebase.model.Book;
import com.bigbang.googlebooksfirebase.view.MainActivity;
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug;

public class FavoriteBookAdapter extends RecyclerView.Adapter<FavoriteBookAdapter.FavoriteBookViewHolder> {

    private GoogleBooksViewModel googleBooksViewModel;
    private List<Book> bookResults;
    private ViewGroup theParent;

    public FavoriteBookAdapter(List<Book> bookResults) { this.bookResults = bookResults; }

    @NonNull
    @Override
    public FavoriteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        theParent = parent;
        googleBooksViewModel = ViewModelProviders.of((MainActivity)theParent.getContext()).get(GoogleBooksViewModel.class);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item_layout, parent, false);

        return new FavoriteBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteBookViewHolder holder, int position) {

        logDebug("title = " + bookResults.get(position).getTitle());

        try {

            if (bookResults.get(position) != null) {

                Glide.with(theParent.getContext())
                        .load(bookResults.get(position).getImageURL())
                        .placeholder(R.drawable.button_background)
                        .into(holder.bookImage);

                holder.bookTitle.setText(bookResults.get(position).getTitle());
                holder.bookAuthors.setText(bookResults.get(position).getAuthors());
            }
        }
        catch(Exception e){  e.printStackTrace(); }

    }

    @Override
    public int getItemCount() { return bookResults.size(); }


    class FavoriteBookViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView bookTitle;
        TextView bookAuthors;

        public FavoriteBookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title_textview);
            bookAuthors = itemView.findViewById(R.id.book_authors_textview);
        }
    }

}
