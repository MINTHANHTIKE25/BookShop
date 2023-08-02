package com.minthanhtike.bookshop.room.usersbooks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.books.BooksRepo;

import java.util.List;

public class UsersBookViewModel extends AndroidViewModel {
    UsersBookRepo usersBookRepo;
    private LiveData<List<UsersBook>>usersBookList;

    public UsersBookViewModel(@NonNull Application application) {
        super(application);
        usersBookRepo=new UsersBookRepo(application);
        usersBookList=usersBookRepo.getAllUsersBookList();
    }

    public LiveData<List<UsersBook>> getUsersBookList() {return usersBookList;}

    public void insert(UsersBook usersBook){usersBookRepo.insert(usersBook);}
    public void update(UsersBook usersBook){usersBookRepo.update(usersBook);}
    public void delete(UsersBook usersBook){usersBookRepo.delete(usersBook);}
}
