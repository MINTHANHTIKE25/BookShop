package com.minthanhtike.bookshop.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksDao;
import com.minthanhtike.bookshop.room.books.BooksData;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksDao;
import com.minthanhtike.bookshop.room.usersbooks.UsersBook;
import com.minthanhtike.bookshop.room.usersbooks.UsersBookDao;
import com.minthanhtike.bookshop.room.usersinfo.Users;
import com.minthanhtike.bookshop.room.usersinfo.UsersDao;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooksDao;

@Database(entities = {Users.class, Books.class, UsersBook.class, WishListBooks.class, BagBooks.class},version = 1)
public abstract class BookStoreDb extends RoomDatabase {
    public static BookStoreDb instance;
    public abstract UsersDao userDao();
    public abstract BooksDao bookDao();
    public abstract UsersBookDao usersBookDao();
    public abstract WishListBooksDao wishListBooksDao();
    public abstract BagBooksDao bagBooksDao();
    public static synchronized BookStoreDb getInstance(Context context){
        if (instance==null){
                instance= Room.databaseBuilder(context.getApplicationContext(),BookStoreDb.class,"books_database")
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        getInstance(context).bookDao()
                                                .insertAll(BooksData.populateBooksData());
                                    }
                                });
                            }
                        })
                        .build();
        }
        return instance;
    }

}
