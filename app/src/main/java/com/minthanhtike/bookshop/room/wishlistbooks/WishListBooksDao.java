package com.minthanhtike.bookshop.room.wishlistbooks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishListBooksDao {
    @Insert
    public void insert(WishListBooks wishListBooks);

    @Delete
    public void delete(WishListBooks wishListBooks);

    @Query("select * from wishList_book_tb")
    public LiveData<List<WishListBooks>> getAllWishList();
}
