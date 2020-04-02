package com.bigbang.googlebooksfirebase.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bigbang.googlebooksfirebase.R;
import com.bigbang.googlebooksfirebase.database.BookEntity;
import com.bigbang.googlebooksfirebase.database.BooksDB;
import com.bigbang.googlebooksfirebase.model.Item;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug;

public class GoogleBooksAdapter extends RecyclerView.Adapter<GoogleBooksAdapter.BookViewHolder> {

    private List<Item> bookResults;
    private ViewGroup theParent;
    private BooksDB booksDB;

    //ImageView bookImage;
    //TextView bookTitle;

    public GoogleBooksAdapter(List<Item> bookResults) {
        this.bookResults = bookResults;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        theParent = parent;

        booksDB = Room.databaseBuilder(
                theParent.getContext(),
                BooksDB.class,
                "books.db")
                .allowMainThreadQueries()
                .build();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item_layout, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        //holder.bookTitle = bookTitle.findViewById(R.id.book_title_textview);
        Log.d("TAG_X", "Book Title: " + bookResults.get(position).getVolumeInfo().getTitle());

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
/*
                            booksDB.getBookDAO().addNewBook(new BookEntity (
                                    bookResults.get(position).getVolumeInfo().getImageLinks().getThumbnail(),
                                    bookResults.get(position).getVolumeInfo().getTitle(),
                                    bookResults.get(position).getVolumeInfo().getAuthors().toString()));
*/
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

        ImageView bookImage;
        TextView bookTitle;
        TextView bookAuthors;
        TextView bookFavorite;
        ToggleButton favoriteToggleButton;

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
