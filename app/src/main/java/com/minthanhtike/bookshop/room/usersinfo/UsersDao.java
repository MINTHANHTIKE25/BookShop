package com.minthanhtike.bookshop.room.usersinfo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.minthanhtike.bookshop.room.usersinfo.Users;

import java.util.List;

@Dao
public interface UsersDao {
    @Insert
    void insert(Users users);

    @Update
    void update(Users users);

    @Delete
    void delete(Users users);

    @Query("select * from users_table")
    LiveData<List<Users>>getAllUser();
}
