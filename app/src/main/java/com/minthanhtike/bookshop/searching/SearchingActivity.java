package com.minthanhtike.bookshop.searching;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.minthanhtike.bookshop.BookStoreActivity;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.adapter.SearchAdapter;
import com.minthanhtike.bookshop.bookstorfrag.MotivationFragmentDialog;
import com.minthanhtike.bookshop.bookstorfrag.MotivationFragments;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;
import com.smarteist.autoimageslider.IndicatorView.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SearchingActivity extends AppCompatActivity implements SearchAdapter.SClickListener {
    ImageButton toBack;
    RecyclerView searchRecycle;
    SearchAdapter searchAdapter;
    BooksViewModel booksViewModel;
    SearchView searchView;
    List<Books> filterBooksList = new ArrayList<>();
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        initView();
        initEvent();
    }

    private void initView() {
        toBack = findViewById(R.id.to_back_btn);
        toBack.setOnClickListener(v -> {
            startActivity(new Intent(this, BookStoreActivity.class));
            finish();
        });
        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();
        searchRecycle = findViewById(R.id.search_recycle);
        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
    }

    private void initEvent() {
        booksViewModel.getBooklist().observe(this, new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> booksList) {
                setSearchAdapter(booksList);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                settingSearch(newText);
                return true;
            }
        });


    }

    public void setSearchAdapter(List<Books> booksList) {
        filterBooksList.addAll(booksList);
        searchAdapter = new SearchAdapter(this, booksList, this::clickItems);
        searchRecycle.setLayoutManager(new LinearLayoutManager(this));
        searchRecycle.setAdapter(searchAdapter);
    }

//    public void setSearchAdapter(List<Books> booksList, String searchText) {
//        if (!searchText.isEmpty()) {
//            booksList.removeIf(books -> !books.getBooksName().contains(searchText));
//            searchAdapter = new SearchAdapter(this, booksList);
//            searchRecycle.setLayoutManager(new LinearLayoutManager(this));
//            searchRecycle.setAdapter(searchAdapter);
//        } else {
//            searchAdapter = new SearchAdapter(this, filterBooksList);
//            searchRecycle.setLayoutManager(new LinearLayoutManager(this));
//            searchRecycle.setAdapter(searchAdapter);
//        }
//    }

    public void settingSearch(String text) {
        List<Books> filterList = new ArrayList<>();
        for (Books books : filterBooksList) {
            if (books.getBooksName().toLowerCase().contains(text.toLowerCase()) |
                    books.getBooksName().equalsIgnoreCase(text)) {
                filterList.add(books);
            }
        }

        if (filterList.isEmpty()) {
//            Toast.makeText(this, "This books is not available!", Toast.LENGTH_SHORT).show();
        } else {
            searchAdapter.setFilterList(filterList);
        }
    }

    @Override
    public void clickItems(int position, List<Books> booksList) {

        wishListBookViewModel = new ViewModelProvider(this).get(WishListBookViewModel.class);
        bagBooksViewModel = new ViewModelProvider(this).get(BagBooksViewModel.class);
        MotivationFragmentDialog motivationFragmentDialog = new MotivationFragmentDialog(wishListBookViewModel, bagBooksViewModel);
        Bundle mArgs = new Bundle();
        mArgs.putString(MotivationFragments.BOOKSNAME, booksList.get(position).getBooksName());
        mArgs.putString(MotivationFragments.AUTHORS, booksList.get(position).getAuthors());
        mArgs.putString(MotivationFragments.BOOKSID, booksList.get(position).getBooksId());
        mArgs.putString(MotivationFragments.PRICE, booksList.get(position).getPrice());
        mArgs.putString(MotivationFragments.CATEGORY, booksList.get(position).getCategory());
        motivationFragmentDialog.setArguments(mArgs);
        motivationFragmentDialog.show(getSupportFragmentManager(), "Motivation Fragments");
    }
}