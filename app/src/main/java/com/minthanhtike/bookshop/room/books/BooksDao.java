package com.minthanhtike.bookshop.room.books;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.usersbooks.UsersBook;

import java.util.List;

@Dao
public interface BooksDao {
    @Insert
    void insert(Books books);

    @Update
    void update(Books books);

    @Delete
    void delete(Books books);

    @Query("select * from books_table")
    LiveData<List<Books>>getAllBooks();

    @Insert
    void insertAll(Books[] books);
}
