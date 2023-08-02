package com.minthanhtike.bookshop.room.books;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.BookStoreDb;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksDao;
import com.minthanhtike.bookshop.room.usersbooks.UsersBook;

import java.util.List;

public class BooksRepo {
    private BooksDao booksDao;
    private LiveData<List<Books>> booksList;

    public BooksRepo(Application application){
        BookStoreDb bookStoreDb=BookStoreDb.getInstance(application);
        booksDao=bookStoreDb.bookDao();
        booksList= booksDao.getAllBooks();
    }

    public LiveData<List<Books>> getBooksList() {
        return booksList;
    }
    //insert//
    public static class InsertBookAsyncTask extends AsyncTask<Books,Void,Void> {
        private BooksDao booksDao;

        private InsertBookAsyncTask(BooksDao booksDao) {this.booksDao = booksDao;}

        @Override
        protected Void doInBackground(Books... books) {
            booksDao.insert(books[0]);
            return null;
        }
    }
    public void insert(Books books){new InsertBookAsyncTask(booksDao).execute(books);}
    //update//
    public static class UpdateBookAsyncTask extends AsyncTask<Books,Void,Void> {
        private BooksDao booksDao;

        private UpdateBookAsyncTask(BooksDao booksDao) {this.booksDao = booksDao;}

        @Override
        protected Void doInBackground(Books... books) {
            booksDao.update(books[0]);
            return null;
        }
    }
    public void update(Books books){new UpdateBookAsyncTask(booksDao).execute(books);}
    //delete//
    public static class DeleteBookAsyncTask extends AsyncTask<Books,Void,Void> {
        private BooksDao booksDao;

        private DeleteBookAsyncTask(BooksDao booksDao) {this.booksDao = booksDao;}

        @Override
        protected Void doInBackground(Books... books) {
            booksDao.delete(books[0]);
            return null;
        }
    }
    public void delete(Books books){new DeleteBookAsyncTask(booksDao).execute(books);}
}
