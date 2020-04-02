package com.bigbang.googlebooksfirebase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigbang.googlebooksfirebase.R;
import com.bigbang.googlebooksfirebase.adapter.GoogleBooksAdapter;
import com.bigbang.googlebooksfirebase.model.Book;
import com.bigbang.googlebooksfirebase.model.Item;
import com.bigbang.googlebooksfirebase.util.Constants;
import com.bigbang.googlebooksfirebase.util.DebugLogger;
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug;

public class MainActivity extends AppCompatActivity {

    private GoogleBooksViewModel googleBooksViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // RxJava

    private DatabaseReference reference;

    //private BooksDB booksDB;

    RecyclerView bookResultsRecyclerView;
    EditText searchEditText;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleBooksViewModel = ViewModelProviders.of(this).get(GoogleBooksViewModel.class);

        // Firebase
        reference = FirebaseDatabase.getInstance().getReference().child("books");

        Book book = new Book(
            "https://books.google.com/books/content?id=BjQ_HYN956gC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
            "Nerds",
            "[David Anderegg]");

        String pushValue = reference.push().getKey();
        if (pushValue != null)
            reference.child(pushValue).setValue(book);
        else
            Log.d("TAG_X", "db update failed");


        /*
        booksDB = Room.databaseBuilder(
                this,
                BooksDB.class,
                "books.db")
                .allowMainThreadQueries()
                .build();
        */

        bookResultsRecyclerView = findViewById(R.id.book_results_recycler_view);
        searchEditText = findViewById(R.id.search_edittext);
        searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performSearch();
            }
        });
    }


    public void performSearch() {

        String search_terms = searchEditText.getText().toString().trim();
        searchEditText.setText("");
        searchEditText.clearFocus();

        // RxJava
        compositeDisposable.add(googleBooksViewModel.getGoogleBooksRx(search_terms, Constants.API_KEY).subscribe(googleBookResults -> {
            displayInformationRx(googleBookResults.getItems());
        }, throwable -> {
            DebugLogger.logError(throwable);
        }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    // RxJava
    private void displayInformationRx(List<Item> googleBookResults) {

        updateRecyclerView(googleBookResults);

        for (int i = 0; i < googleBookResults.size(); i++) {
            //DebugLogger.logDebug("RxJava : " + googleBookResults.get(i).getVolumeInfo().getImageLinks().getThumbnail());
            if (googleBookResults.get(i) != null) {
                logDebug("RxJava : " + googleBookResults.get(i).getVolumeInfo().getTitle());
                logDebug("RxJava : " + googleBookResults.get(i).getVolumeInfo().getAuthors());
                logDebug("RxJava : " + googleBookResults.get(i).getVolumeInfo().getDescription());
            }
        }
    }

    private void updateRecyclerView(List<Item> googleBookResults) {
        GoogleBooksAdapter adapter = new GoogleBooksAdapter(googleBookResults);
        bookResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookResultsRecyclerView.setAdapter(adapter);
    }

}
