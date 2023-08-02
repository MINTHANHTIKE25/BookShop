package com.minthanhtike.bookshop.room.usersinfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.usersinfo.Users;
import com.minthanhtike.bookshop.room.usersinfo.UsersRepo;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {
    private UsersRepo usersRepo;
    private LiveData<List<Users>> usersList;
    public UsersViewModel(@NonNull Application application) {
        super(application);
        usersRepo=new UsersRepo(application);
        usersList= usersRepo.getUserList();
    }

    public LiveData<List<Users>> getUsersList() {
        return usersList;
    }

    public void insert(Users users){usersRepo.insert(users);}
    public void update(Users users){usersRepo.update(users);}
    public void delete(Users users){usersRepo.delete(users);}
}
