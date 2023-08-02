package com.minthanhtike.bookshop.room.wishlistbooks;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.minthanhtike.bookshop.room.BookStoreDb;

import java.util.List;

public class WishListBookRepo {
    private WishListBooksDao wishListBooksDao;
    private LiveData<List<WishListBooks>> wishListbooksList;

    public WishListBookRepo(Application application) {
        BookStoreDb bookStoreDb=BookStoreDb.getInstance(application);
        wishListBooksDao= bookStoreDb.wishListBooksDao();
        wishListbooksList= wishListBooksDao.getAllWishList();
    }

    public LiveData<List<WishListBooks>> getWishListbooksList() {return wishListbooksList;}
    //inserting
    public static class InsertWishListBookAsyncTask extends AsyncTask<WishListBooks,Void,Void> {
        private WishListBooksDao wishListBooksDao;

        private InsertWishListBookAsyncTask(WishListBooksDao wishListBooksDao) {this.wishListBooksDao = wishListBooksDao;}

        @Override
        protected Void doInBackground(WishListBooks... wishListBooks) {
            wishListBooksDao.insert(wishListBooks[0]);
            return null;
        }
    }
    public void insert(WishListBooks wishListBooks){new InsertWishListBookAsyncTask(wishListBooksDao).execute(wishListBooks);}
    //delete
    public static class DeleteWishListBookAsyncTask extends AsyncTask<WishListBooks,Void,Void>{
        private WishListBooksDao wishListBooksDao;
        private DeleteWishListBookAsyncTask(WishListBooksDao wishListBooksDao){this.wishListBooksDao=wishListBooksDao;}

        @Override
        protected Void doInBackground(WishListBooks... wishListBooks) {
            wishListBooksDao.delete(wishListBooks[0]);
            return null;
        }
    }
    public void delete(WishListBooks wishListBooks){new DeleteWishListBookAsyncTask(wishListBooksDao).execute(wishListBooks);}
}
