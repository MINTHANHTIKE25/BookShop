package com.minthanhtike.bookshop.room.usersinfo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.BookStoreDb;
import com.minthanhtike.bookshop.room.usersinfo.Users;
import com.minthanhtike.bookshop.room.usersinfo.UsersDao;

import java.util.List;

public class UsersRepo {
    private UsersDao userDao;
    private LiveData<List<Users>> userList;

    public UsersRepo(Application application) {
        BookStoreDb bookStoreDb = BookStoreDb.getInstance(application);
        userDao = bookStoreDb.userDao();
        userList = userDao.getAllUser();
    }

    public LiveData<List<Users>> getUserList() {
        return userList;
    }

    //insert//
    public void insert(Users users) {
        new InsertUserAsyncTask(userDao).execute(users);
    }

    public static class InsertUserAsyncTask extends AsyncTask<Users, Void, Void> {
        private UsersDao userDao;

        private InsertUserAsyncTask(UsersDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    //update//
    public void update(Users users) {
        new UpdateUserAsyncTask(userDao).execute(users);
    }

    public static class UpdateUserAsyncTask extends AsyncTask<Users, Void, Void> {
        private UsersDao userDao;

        private UpdateUserAsyncTask(UsersDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    //delete//
    public void delete(Users users) {
        new DeleteUserAsyncTask(userDao).execute(users);
    }

    public static class DeleteUserAsyncTask extends AsyncTask<Users, Void, Void> {
        private UsersDao userDao;

        private DeleteUserAsyncTask(UsersDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

}
