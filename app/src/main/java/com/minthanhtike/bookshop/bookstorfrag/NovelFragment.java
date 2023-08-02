package com.minthanhtike.bookshop.bookstorfrag;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.bookstoreadapter.NovelAdapter;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;

import java.util.List;
import java.util.function.Predicate;

public class NovelFragment extends Fragment implements NovelAdapter.NovelItemListener{
    BagBooksViewModel bagBooksViewModel;
    BooksViewModel booksViewModel;
    RecyclerView novelRecycler;
    NovelAdapter novelAdapter;
    WishListBookViewModel wishListBookViewModel;
    public static final String BOOKSNAME="BooksName",AUTHORS="Authors",PRICE="Price",BOOKSID="ID",
    CATEGORY="Category";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_novel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        novelRecycler=view.findViewById(R.id.novel_recycle);
        booksViewModel=new ViewModelProvider(this).get(BooksViewModel.class);
        booksViewModel.getBooklist().observe(getViewLifecycleOwner(), new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> booksList) {
                booksList.removeIf(new Predicate<Books>() {
                    @Override
                    public boolean test(Books books) {
                        if (books.getCategory().equalsIgnoreCase("poems") |
                        books.getCategory().equalsIgnoreCase("science") |
                        books.getCategory().equalsIgnoreCase("motivation")|
                        books.getCategory().equalsIgnoreCase("politics")){
                            return true;
                        }
                        return false;
                    }
                });
                setNovelAdapter(view,booksList);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNovelAdapter(View view, List<Books>booksList){
        novelRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 3,
                LinearLayoutManager.VERTICAL,false));
        wishListBookViewModel=new ViewModelProvider(this).get(WishListBookViewModel.class);
        bagBooksViewModel=new ViewModelProvider(this).get(BagBooksViewModel.class);
        novelAdapter=new NovelAdapter(view.getContext(),booksList,wishListBookViewModel,bagBooksViewModel, this);
        novelAdapter.notifyDataSetChanged();
        novelRecycler.setAdapter(novelAdapter);
    }
    @Override
    public void nClick(int position, List<Books> booksList) {
        NovelDialogFragments novelDialogFragments=new NovelDialogFragments(bagBooksViewModel,wishListBookViewModel);
        Bundle args=new Bundle();
        args.putString(BOOKSNAME,booksList.get(position).getBooksName());
        args.putString(AUTHORS,booksList.get(position).getAuthors());
        args.putString(PRICE,booksList.get(position).getPrice());
        args.putString(CATEGORY,booksList.get(position).getCategory());
        args.putString(BOOKSID,booksList.get(position).getBooksId());
        novelDialogFragments.setArguments(args);
        novelDialogFragments.show(getChildFragmentManager(),"Novel Dialog Fragments !");
    }
}