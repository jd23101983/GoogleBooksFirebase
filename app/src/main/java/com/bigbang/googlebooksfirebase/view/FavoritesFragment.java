package com.bigbang.googlebooksfirebase.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigbang.googlebooksfirebase.R;
import com.bigbang.googlebooksfirebase.adapter.FavoriteBookAdapter;
import com.bigbang.googlebooksfirebase.model.Book;
import com.bigbang.googlebooksfirebase.util.DebugLogger;
import com.bigbang.googlebooksfirebase.viewmodel.GoogleBooksViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug;

public class FavoritesFragment extends Fragment {

    private GoogleBooksViewModel googleBooksViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Book> bookList = new ArrayList<>();

    TextView favoriteBookTitle;
    RecyclerView favoriteBookRecyclerView;
    Button favoriteBackButton;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        googleBooksViewModel = ViewModelProviders.of(this).get(GoogleBooksViewModel.class);

        compositeDisposable.add(googleBooksViewModel.getBookList().subscribe(bookList -> {
            Log.d("TAG_X", "BookResults size() = " + bookList.size());
            displayBooks(bookList);
        }, throwable -> {
            DebugLogger.logError(throwable);
        }));


        favoriteBookTitle = view.findViewById(R.id.favorite_books_textView);
        favoriteBookRecyclerView = view.findViewById(R.id.favorite_books_recycler_view);
        favoriteBackButton = view.findViewById(R.id.back_button);
        favoriteBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).backFromFavorites();
            }
        });

        //List<Result> results = (((TransportObj)getArguments().getParcelable("FRAG_KEY")).getMovieResults()).getResults();
        //MovieResultSet results = ((TransportObj)getArguments().getParcelable("FRAG_KEY")).getMovieResults();

        //MovieAdapter movieAdapter = new MovieAdapter(results);
        //movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //movieRecyclerView.setAdapter(movieAdapter);
    }

    public void displayBooks(List<Book> newBookList) {

        logDebug("newBookList size = " + newBookList.size());

        FavoriteBookAdapter favoriteBookAdapter = new FavoriteBookAdapter(newBookList);
        favoriteBookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteBookRecyclerView.setAdapter(favoriteBookAdapter);
    }

}
