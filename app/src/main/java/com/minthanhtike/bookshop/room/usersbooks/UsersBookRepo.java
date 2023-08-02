package com.minthanhtike.bookshop.room.usersbooks;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.minthanhtike.bookshop.room.BookStoreDb;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksDao;
import com.minthanhtike.bookshop.room.books.BooksRepo;

import java.util.List;

public class UsersBookRepo {
    private UsersBookDao usersBookDao;
    private LiveData<List<UsersBook>>usersBookList;

    public UsersBookRepo(Application application) {
        BookStoreDb bookStoreDb=BookStoreDb.getInstance(application);
        usersBookDao= bookStoreDb.usersBookDao();
        usersBookList= usersBookDao.getAllUserBooks();
    }

    public LiveData<List<UsersBook>> getAllUsersBookList() {return usersBookList;}

    //insert//
    public static class InsertBookAsyncTask extends AsyncTask<UsersBook,Void,Void> {
        private UsersBookDao usersBookDao;

        private InsertBookAsyncTask(UsersBookDao usersBookDao) {this.usersBookDao = usersBookDao;}

        @Override
        protected Void doInBackground(UsersBook... usersBooks) {
            usersBookDao.insert(usersBooks[0]);
            return null;
        }
    }
    public void insert(UsersBook usersBook){new InsertBookAsyncTask(usersBookDao).execute(usersBook);}

    public static class UpdateBookAsyncTask extends AsyncTask<UsersBook,Void,Void> {
        private UsersBookDao usersBookDao;

        private UpdateBookAsyncTask(UsersBookDao usersBookDao) {this.usersBookDao = usersBookDao;}

        @Override
        protected Void doInBackground(UsersBook... usersBooks) {
            usersBookDao.update(usersBooks[0]);
            return null;
        }
    }
    public void update(UsersBook usersBook){new UpdateBookAsyncTask(usersBookDao).execute(usersBook);}

    public static class DeleteBookAsyncTask extends AsyncTask<UsersBook,Void,Void> {
        private UsersBookDao usersBookDao;

        private DeleteBookAsyncTask(UsersBookDao usersBookDao) {this.usersBookDao = usersBookDao;}

        @Override
        protected Void doInBackground(UsersBook... usersBooks) {
            usersBookDao.delete(usersBooks[0]);
            return null;
        }
    }
    public void delete(UsersBook usersBook){new DeleteBookAsyncTask(usersBookDao).execute(usersBook);}
}
