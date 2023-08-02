package com.minthanhtike.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.minthanhtike.bookshop.fragments.AccountFragments;
import com.minthanhtike.bookshop.fragments.BagFragments;
import com.minthanhtike.bookshop.fragments.BookShelfFragments;
import com.minthanhtike.bookshop.fragments.BookStoreFragment;
import com.minthanhtike.bookshop.fragments.BookShelfViewModelForClicking;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.usersbooks.UsersBook;
import com.minthanhtike.bookshop.room.usersbooks.UsersBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;
import com.minthanhtike.bookshop.searching.SearchingActivity;

import java.util.ArrayList;
import java.util.List;

public class BookStoreActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, BookShelfFragments.BsdataPassing {
    public BottomNavigationView bottomNavigationView;
    Fragment bookShelf, bookStore, bag, account;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    BookShelfViewModelForClicking bookShelfViewModelForClicking;
    WishListBookViewModel wishListBookViewModel;
    UsersBookViewModel usersBookViewModel;
    BooksViewModel booksViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store);
        bookShelf = new BookShelfFragments();
        bookStore = new BookStoreFragment();
        bag = new BagFragments();
        account = new AccountFragments();
        floatingActionButton = findViewById(R.id.delete_icons);
        floatingActionButton.setVisibility(View.GONE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.bookshelf);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        usersBookViewModel = new ViewModelProvider(this).get(UsersBookViewModel.class);
        bookShelfViewModelForClicking = new ViewModelProvider(this).get(BookShelfViewModelForClicking.class);
        wishListBookViewModel = new ViewModelProvider(this).get(WishListBookViewModel.class);
        booksViewModel=new ViewModelProvider(this).get(BooksViewModel.class);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search_icon) {
                startActivity(new Intent(BookStoreActivity.this, SearchingActivity.class));
            }
            return true;
        });
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bookshelf:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookShelf).commit();
                return true;
            case R.id.bookstore:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookStore).commit();
                floatingActionButton.setVisibility(View.GONE);
                return true;
            case R.id.bag:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bag).commit();
                floatingActionButton.setVisibility(View.GONE);
                return true;
            case R.id.account:
                floatingActionButton.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, account).commit();
                return true;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookShelf).commit();
        }
        return false;
    }


    @Override
    public void isWishEmpty(int wishSize) {
    }

    @Override
    public void isMyBookEmpty(int myBookSize, int wishSize) {
        bookShelfViewModelForClicking.getUsersListMutableLiveData().observe(this, usersBookList -> {
            bookShelfViewModelForClicking.getWishListBooksMutableLiveData().observe(this, wishListBooks -> {
                if (!wishListBooks.isEmpty() & !usersBookList.isEmpty()) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setOnClickListener(v -> {
                        wishListBooks.forEach(wishListBooks1 -> wishListBookViewModel.delete(wishListBooks1));
                        usersBookList.forEach(usersBook -> usersBookViewModel.delete(usersBook));
                        floatingActionButton.setVisibility(View.GONE);
                        usersBookList.clear();
                        wishListBooks.clear();
                    });
                }
            });
        });
        if (myBookSize == 0 & wishSize == 0) {
            floatingActionButton.setVisibility(View.GONE);
        }

        if (myBookSize != 0 & wishSize==0) {
            bookShelfViewModelForClicking.getUsersListMutableLiveData().observe(this, usersBookList -> {
                if (!usersBookList.isEmpty()) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setOnClickListener(v -> {
                        usersBookList.forEach(usersBook -> usersBookViewModel.delete(usersBook));
                        usersBookList.clear();
                        floatingActionButton.setVisibility(View.GONE);
                    });
                }
            });
        }
        if (wishSize != 0 & myBookSize==0) {
            bookShelfViewModelForClicking.getWishListBooksMutableLiveData().observe(this, wishListBooks -> {
                if (!wishListBooks.isEmpty()) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setOnClickListener(v -> {
                        wishListBooks.forEach(wishListBooks1 -> wishListBookViewModel.delete(wishListBooks1));
                        wishListBooks.clear();
                        floatingActionButton.setVisibility(View.GONE);
                    });
                }
            });
        }
        if (wishSize>0 & myBookSize>0){
            floatingActionButton.setVisibility(View.VISIBLE);
        }
    }
}