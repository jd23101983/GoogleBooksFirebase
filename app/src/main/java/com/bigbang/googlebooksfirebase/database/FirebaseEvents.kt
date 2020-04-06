package com.bigbang.googlebooksfirebase.database

import com.bigbang.googlebooksfirebase.model.Book
import com.bigbang.googlebooksfirebase.util.DebugLogger.logDebug
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

class FirebaseEvents {
    private val booksReference: DatabaseReference
    private val bookListObservable = PublishSubject.create<List<Book>>()
    fun addNewBook(book: Book?) {
        val bookKey = booksReference.push().key
        if (bookKey != null) booksReference.child(bookKey).setValue(book) else logDebug("db update failed")
    }

    //logDebug("Book List Size before return " + bookList.size());
    val bookList: Observable<List<Book>>
        get() {
            val bookList: MutableList<Book> = ArrayList()
            booksReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    logDebug("inside FirebaseHelper -> onDataChange()")
                    for (currentSnap in dataSnapshot.children) {
                        val currentBook = currentSnap.getValue(Book::class.java)!!
                        bookList.add(currentBook)
                    }
                    bookListObservable.onNext(bookList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    logDebug("db error")
                }
            })
            //logDebug("Book List Size before return " + bookList.size());
            return bookListObservable
        }

    init {
        booksReference = FirebaseDatabase.getInstance().reference.child("books")
    }
}