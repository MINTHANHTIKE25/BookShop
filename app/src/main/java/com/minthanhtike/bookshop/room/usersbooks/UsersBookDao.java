package com.minthanhtike.bookshop.room.usersbooks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UsersBookDao {

    @Insert
    public void insert(UsersBook usersBook);

    @Update
    public void update(UsersBook usersBook);

    @Delete
    public void delete(UsersBook usersBook);

    @Query("select * from users_book_tb")
    public LiveData<List<UsersBook>>getAllUserBooks();
}
