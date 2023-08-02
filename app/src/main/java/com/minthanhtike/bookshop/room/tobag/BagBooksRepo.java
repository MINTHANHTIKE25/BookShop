package com.minthanhtike.bookshop.room.tobag;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.BookStoreDb;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksDao;
import com.minthanhtike.bookshop.room.books.BooksRepo;

import java.util.List;

public class BagBooksRepo {
    private BagBooksDao bagBooksDao;
    private LiveData<List<BagBooks>> bagBooksList;

    public BagBooksRepo(Application application) {
        BookStoreDb bookStoreDb=BookStoreDb.getInstance(application);
        bagBooksDao=bookStoreDb.bagBooksDao();
        bagBooksList= bagBooksDao.getAllBagBooks();
    }

    public LiveData<List<BagBooks>> getBagBooksList() {return bagBooksList;}
    //deleting all//
    public static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private BagBooksDao bagBooksDao;
        private DeleteAllAsyncTask(BagBooksDao bagBooksDao){this.bagBooksDao=bagBooksDao;}
        @Override
        protected Void doInBackground(Void... voids) {
            bagBooksDao.deleteAllBagBooks();
            return null;
        }
    }
    public void deleteAll(){new BagBooksRepo.DeleteAllAsyncTask(bagBooksDao).execute();}
    //insert//
    public static class InsertBagBookAsyncTask extends AsyncTask<BagBooks,Void,Void> {
        private BagBooksDao bagBooksDao;

        private InsertBagBookAsyncTask(BagBooksDao bagBooksDao) {this.bagBooksDao = bagBooksDao;}

        @Override
        protected Void doInBackground(BagBooks... bagBooks) {
            bagBooksDao.Insert(bagBooks[0]);
            return null;
        }
    }
    public void insert(BagBooks bagBooks){new BagBooksRepo.InsertBagBookAsyncTask(bagBooksDao).execute(bagBooks);}

    //update

    public static class UpdateBagBookAsyncTask extends AsyncTask<BagBooks,Void,Void> {
        private BagBooksDao bagBooksDao;

        private UpdateBagBookAsyncTask(BagBooksDao bagBooksDao) {this.bagBooksDao = bagBooksDao;}

        @Override
        protected Void doInBackground(BagBooks... bagBooks) {
            bagBooksDao.Update(bagBooks[0]);
            return null;
        }
    }
    public void update(BagBooks bagBooks){new BagBooksRepo.UpdateBagBookAsyncTask(bagBooksDao).execute(bagBooks);}
    //delete
    public static class DeleteBookAsyncTask extends AsyncTask<BagBooks,Void,Void> {
        private BagBooksDao bagBooksDao;

        private DeleteBookAsyncTask(BagBooksDao bagBooksDao) {this.bagBooksDao = bagBooksDao;}

        @Override
        protected Void doInBackground(BagBooks... bagBooks) {
            bagBooksDao.DeleteOneItem(bagBooks[0]);
            return null;
        }
    }
    public void delete(BagBooks bagBooks){new BagBooksRepo.DeleteBookAsyncTask(bagBooksDao).execute(bagBooks);}
}
