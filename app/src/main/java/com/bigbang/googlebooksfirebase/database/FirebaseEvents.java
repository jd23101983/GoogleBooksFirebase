package com.bigbang.googlebooksfirebase.database;

import com.bigbang.googlebooksfirebase.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

import static com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug;

public class FirebaseEvents {

    private DatabaseReference booksReference;
    private PublishSubject<List<Book>> bookListObservable = PublishSubject.create();

    public FirebaseEvents() {
        this.booksReference = FirebaseDatabase.getInstance().getReference().child("books");
    }

    public void addNewBook(Book book){
        String bookKey = booksReference.push().getKey();
        if (bookKey != null)
            booksReference.child(bookKey).setValue(book);
        else
            logDebug("db update failed");
    }

    public Observable<List<Book>> getBookList(){
        List<Book> bookList = new ArrayList<>();

        booksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                logDebug("inside FirebaseHelper -> onDataChange()");

                for(DataSnapshot currentSnap : dataSnapshot.getChildren()){
                    Book currentBook = currentSnap.getValue(Book.class);
                    bookList.add(currentBook);
                }
                bookListObservable.onNext(bookList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                logDebug("db error");
            }
        });
        logDebug("Book List Size before return "+ bookList.size());
        return bookListObservable;
    }

}
