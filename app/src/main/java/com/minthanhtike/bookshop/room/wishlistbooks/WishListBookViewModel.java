package com.minthanhtike.bookshop.room.wishlistbooks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WishListBookViewModel extends AndroidViewModel {
    private WishListBookRepo wishListBookRepo;
    private LiveData<List<WishListBooks>> wishListBooks;
    public WishListBookViewModel(@NonNull Application application) {
        super(application);
        wishListBookRepo=new WishListBookRepo(application);
        wishListBooks= wishListBookRepo.getWishListbooksList();
    }

    public LiveData<List<WishListBooks>> getWishListBooks() {return wishListBooks;}

    public void insert(WishListBooks wishListBooks){wishListBookRepo.insert(wishListBooks);}
    public void delete(WishListBooks wishListBooks){wishListBookRepo.delete(wishListBooks);}
}
