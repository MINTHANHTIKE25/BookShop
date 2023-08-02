package com.minthanhtike.bookshop.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minthanhtike.bookshop.room.usersbooks.UsersBook;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;

import java.util.List;

public class BookShelfViewModelForClicking extends ViewModel {
    private final MutableLiveData<List<WishListBooks>> wishListBooksMutableLiveData=new MutableLiveData<>();
    private final MutableLiveData<List<UsersBook>> usersListMutableLiveData=new MutableLiveData<>();

    public LiveData<List<WishListBooks>> getWishListBooksMutableLiveData() {
        return wishListBooksMutableLiveData;
    }
    public void setWishListBooksMutableLiveData(List<WishListBooks> wishListBooks){
        wishListBooksMutableLiveData.setValue(wishListBooks);
    }

    public LiveData<List<UsersBook>> getUsersListMutableLiveData() {
        return usersListMutableLiveData;
    }
    public void setUsersListMutableLiveData(List<UsersBook> usersBooksList){
       usersListMutableLiveData.setValue(usersBooksList);
    }
}
