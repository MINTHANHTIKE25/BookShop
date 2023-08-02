package com.minthanhtike.bookshop.room.books;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksRepo;

import java.util.List;

public class BooksViewModel extends AndroidViewModel {
    private BooksRepo booksRepo;
    private LiveData<List<Books>> booklist;
    public BooksViewModel(@NonNull Application application) {
        super(application);
        booksRepo=new BooksRepo(application);
        booklist= booksRepo.getBooksList();
    }

    public LiveData<List<Books>> getBooklist() {
        return booklist;
    }

    public void insert(Books books){booksRepo.insert(books);}
    public void update(Books books){booksRepo.update(books);}
    public void delete(Books books){booksRepo.delete(books);}
}
