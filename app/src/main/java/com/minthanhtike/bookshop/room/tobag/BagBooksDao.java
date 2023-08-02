package com.minthanhtike.bookshop.room.tobag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BagBooksDao {
    @Insert
    void Insert(BagBooks bagBooks);
    @Delete
    void DeleteOneItem(BagBooks bagBooks);
    @Update
    void Update(BagBooks bagBooks);

    @Query("select * from bag_table")
    LiveData<List<BagBooks>> getAllBagBooks();

    @Query("delete from bag_table")
    void deleteAllBagBooks();

}
