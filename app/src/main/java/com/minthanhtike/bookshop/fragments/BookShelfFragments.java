package com.minthanhtike.bookshop.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.minthanhtike.bookshop.BookStoreActivity;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.adapter.MyBooksAdapter;
import com.minthanhtike.bookshop.adapter.WishListBooksAdapter;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.usersbooks.UsersBook;
import com.minthanhtike.bookshop.room.usersbooks.UsersBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;

import java.util.ArrayList;
import java.util.List;

public class BookShelfFragments extends Fragment implements WishListBooksAdapter.BSItemListener, MyBooksAdapter.MyBooksClickListener {
    BsdataPassing bsdataPassing;
    //ViewModels
    UsersBookViewModel usersBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    WishListBookViewModel wishListBookViewModel;
    BooksViewModel booksViewModel;
    //Adapter
    MyBooksAdapter myBooksAdapter;
    WishListBooksAdapter wishListBooksAdapter;
    RecyclerView recyclerView, wishBookrecycle;
    RelativeLayout toDiscoverLayout;
    //this view-models used to store the user long clicking data
    BookShelfViewModelForClicking bookShelfViewModelForClicking;
    List<WishListBooks> tempList = new ArrayList<>();
    List<UsersBook> tempUsersList = new ArrayList<>();

    //    BookStoreFragment bookStoreFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_shelf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //for users books//
        bookShelfViewModelForClicking = new ViewModelProvider(requireActivity()).get(BookShelfViewModelForClicking.class);
        bagBooksViewModel = new ViewModelProvider(requireActivity()).get(BagBooksViewModel.class);
        booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);
        mybooks(view);
        shopBooks(view);
    }

    public void mybooks(View view) {
        usersBookViewModel = new ViewModelProvider(this).get(UsersBookViewModel.class);
        toDiscoverLayout = view.findViewById(R.id.to_discover_layout);
        toDiscoverLayout.setOnClickListener(v -> {
//            bookStoreFragment=new BookStoreFragment();
//            FragmentManager fragmentManager= bookStoreActivity.getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,bookStoreFragment);
//            fragmentTransaction.commit();
            BookStoreActivity bookStoreActivity = (BookStoreActivity) requireActivity();
            bookStoreActivity.bottomNavigationView.setSelectedItemId(R.id.bookstore);
        });
        recyclerView = view.findViewById(R.id.my_books_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        usersBookViewModel.getUsersBookList().observe(getViewLifecycleOwner(), usersBooks -> settingMyBooksAdapter(usersBooks, view));
    }

    private void settingMyBooksAdapter(List<UsersBook> usersBookList, View view) {
        myBooksAdapter = new MyBooksAdapter(view.getContext(), usersBookList, this);
        recyclerView.setAdapter(myBooksAdapter);
    }

    public void shopBooks(View view) {
        wishListBookViewModel = new ViewModelProvider(this).get(WishListBookViewModel.class);
        wishBookrecycle = view.findViewById(R.id.wish_listed_books);
        wishBookrecycle.setLayoutManager(new GridLayoutManager(
                view.getContext(), 2, LinearLayoutManager.VERTICAL, false));
        wishListBookViewModel.getWishListBooks().observe(getViewLifecycleOwner(), new Observer<List<WishListBooks>>() {
            @Override
            public void onChanged(List<WishListBooks> wishListBooks) {
                settingWishlistAdapter(wishListBooks, view);
            }
        });
    }

    //setting the wishListAdapter
    private void settingWishlistAdapter(List<WishListBooks> wishListBooks, View view) {
        wishListBooksAdapter = new WishListBooksAdapter(view.getContext(), wishListBooks, this);
        wishBookrecycle.setAdapter(wishListBooksAdapter);
    }

    //implementing the click events of the wishlist books adapter....
    @Override
    public void onItemClick(int position, List<WishListBooks> wishListBooks) {
        List<Books> tempList = new ArrayList<>();
        BookShelfDialog bookShelfDialog = new BookShelfDialog(bagBooksViewModel);

        booksViewModel.getBooklist().observe(getViewLifecycleOwner(), new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> booksList) {
                booksList.forEach(books -> {
                    if (books.getBooksName().equals(wishListBooks.get(position).getBooksName()) &
                            books.getAuthors().equals(wishListBooks.get(position).getAuthors()) &
                            books.getBooksId().equals(wishListBooks.get(position).getBooksId()) &
                            books.getCategory().equals(wishListBooks.get(position).getCategory())) {
                        tempList.add(books);
                    }
                });
                if (!tempList.isEmpty()) {
                    Bundle bundle = new Bundle();
                    Toast.makeText(getActivity(), String.valueOf(tempList.size()), Toast.LENGTH_SHORT).show();
                    bundle.putString(BookShelfDialog.BOOKSNAME, tempList.get(0).getBooksName());
                    bundle.putString(BookShelfDialog.AUTHORS, tempList.get(0).getAuthors());
                    bundle.putString(BookShelfDialog.BOOKSID, tempList.get(0).getBooksId());
                    bundle.putString(BookShelfDialog.PRICE, tempList.get(0).getPrice());
                    bundle.putString(BookShelfDialog.CATEGORY, tempList.get(0).getCategory());
                    bookShelfDialog.setArguments(bundle);
                    bookShelfDialog.show(getChildFragmentManager(), "Bookshelf_Dialog.");
                }
            }
        });
    }

    //implementing the long click events of the wishList books adapter....
    @Override
    public void onItemLongClick(int position, List<WishListBooks> wishListBooks, ImageView imageView) {
        int visibility = imageView.getVisibility();
        if (visibility == View.GONE) {
            tempList.add(wishListBooks.get(position));
            imageView.setVisibility(View.VISIBLE);
        } else {
            if (!tempList.isEmpty()) {
                tempList.remove(wishListBooks.get(position));
            }
            imageView.setVisibility(View.GONE);
        }
        bsdataPassing.isMyBookEmpty(tempUsersList.size(), tempList.size());
        bookShelfViewModelForClicking.setWishListBooksMutableLiveData(tempList);
    }

    //implementing the my books click listening events
    @Override
    public void myBooksOnClick(int position, List<UsersBook> usersBookList) {
        BookShelfDialog bookShelfDialog = new BookShelfDialog();
        usersBookViewModel.getUsersBookList().observe(getViewLifecycleOwner(), new Observer<List<UsersBook>>() {
            @Override
            public void onChanged(List<UsersBook> usersBookList) {
                Bundle bundle = new Bundle();
                bundle.putString(BookShelfDialog.BOOKSNAME, usersBookList.get(position).getBooksName());
                bundle.putString(BookShelfDialog.AUTHORS, usersBookList.get(position).getAuthors());
                bundle.putString(BookShelfDialog.BOOKSID, usersBookList.get(position).getBooksId());
                bundle.putString(BookShelfDialog.CATEGORY, usersBookList.get(position).getCategory());
                bundle.putString("MYBOOKS", "MYBOOKSDIALOG");
                bookShelfDialog.setArguments(bundle);
                bookShelfDialog.show(getChildFragmentManager(), "USERS_DIALOG");
            }
        });
    }

    //implementing the my books long click events
    @Override
    public void myBooksOnLongClick(int position, List<UsersBook> usersBookList, ImageView selectImg) {
        int visibility = selectImg.getVisibility();
        if (visibility == View.GONE) {
            tempUsersList.add(usersBookList.get(position));
            selectImg.setVisibility(View.VISIBLE);
        } else {
            if (!tempUsersList.isEmpty()) {
                tempUsersList.remove(usersBookList.get(position));
            }
            selectImg.setVisibility(View.GONE);
        }
        bsdataPassing.isMyBookEmpty(tempUsersList.size(), tempList.size());
        bookShelfViewModelForClicking.setUsersListMutableLiveData(tempUsersList);
    }

    //the interface to data passing to the bookstore main activity to show and hide delete btn
    public interface BsdataPassing {
        void isWishEmpty(int wishSize);

        void isMyBookEmpty(int bookSize,int wishSize);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bsdataPassing = (BsdataPassing) context;
        tempList.clear();
        tempUsersList.clear();
    }
}