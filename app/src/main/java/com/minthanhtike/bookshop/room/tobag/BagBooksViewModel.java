package com.minthanhtike.bookshop.room.tobag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.books.Books;

import java.util.List;

public class BagBooksViewModel extends AndroidViewModel {
    private BagBooksRepo booksRepo;
    private LiveData<List<BagBooks>> bagBooksList;
    public BagBooksViewModel(@NonNull Application application) {
        super(application);
        booksRepo=new BagBooksRepo(application);
        bagBooksList= booksRepo.getBagBooksList();
    }

    public LiveData<List<BagBooks>> getBagBooksList() {return bagBooksList;}

    public void insert(BagBooks bagBooks){booksRepo.insert(bagBooks);}
    public void update(BagBooks bagBooks){booksRepo.update(bagBooks);}
    public void delete(BagBooks bagBooks){booksRepo.delete(bagBooks);}
    public void deleteAllData(){booksRepo.deleteAll();}
}
