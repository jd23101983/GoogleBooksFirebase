package com.bigbang.googlebooksfirebase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.bigbang.googlebooksfirebase.R;
import com.bigbang.googlebooksfirebase.adapter.GoogleBooksAdapter;
import com.bigbang.googlebooksfirebase.model.Item;
import com.bigbang.googlebooksfirebase.util.Constants;
import com.bigbang.googlebooksfirebase.util.DebugLogger;
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug;

public class MainActivity extends AppCompatActivity {

    private GoogleBooksViewModel googleBooksViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // RxJava

    private FavoritesFragment favoritesFragment = new FavoritesFragment();

    // TODO: remove
    private DatabaseReference reference;

    private RecyclerView bookResultsRecyclerView;
    private EditText searchEditText;
    private Button searchButton;
    private Button favoritesButton;
    private Button animationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleBooksViewModel = ViewModelProviders.of(this).get(GoogleBooksViewModel.class);
        favoritesFragment = new FavoritesFragment();
        bookResultsRecyclerView = findViewById(R.id.book_results_recycler_view);
        searchEditText = findViewById(R.id.search_edittext);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performSearch();
            }
        });

        favoritesButton = findViewById(R.id.favorites_button);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFavoritesFragment();
            }
        });

        animationButton = findViewById(R.id.animation_button);
        animationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { showAnimationActivity(); }
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

    public void showFavoritesFragment() {
        favoritesFragment = new FavoritesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.favorite_book_frame, favoritesFragment)
                .commit();
    }

    public void showAnimationActivity() {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
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

    public void backFromFavorites() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(favoritesFragment)
                .commit();
    }
}
