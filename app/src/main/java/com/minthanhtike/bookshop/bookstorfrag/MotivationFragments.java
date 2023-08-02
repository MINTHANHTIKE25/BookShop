package com.minthanhtike.bookshop.bookstorfrag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.bookstoreadapter.MotivationAdapter;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;

import java.util.List;
import java.util.function.Predicate;


public class MotivationFragments extends Fragment implements MotivationAdapter.MtvListener{
    MotivationAdapter motivationAdapter;
    RecyclerView motivationRecycle;
    BooksViewModel booksViewModel;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;

    public static final String BOOKSNAME="BooksName",AUTHORS="Authors",PRICE="Price",BOOKSID="ID",
            CATEGORY="Category";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_motivation_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        motivationRecycle=view.findViewById(R.id.motivation_recycle);
        booksViewModel=new ViewModelProvider(this).get(BooksViewModel.class);
        booksViewModel.getBooklist().observe(getViewLifecycleOwner(), new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> booksList) {
                booksList.removeIf(new Predicate<Books>() {
                    @Override
                    public boolean test(Books books) {
                        if (books.getCategory().equalsIgnoreCase("science")|
                        books.getCategory().equalsIgnoreCase("novels")|
                        books.getCategory().equalsIgnoreCase("poems")|
                        books.getCategory().equalsIgnoreCase("politics")){
                            return true;
                        }
                        return false;
                    }
                });
                setMotivationAdapter(view,booksList);
            };
        });
    }

    public void setMotivationAdapter(View view,List<Books> booksList){
        bagBooksViewModel=new ViewModelProvider(this).get(BagBooksViewModel.class);
        wishListBookViewModel=new ViewModelProvider(this).get(WishListBookViewModel.class);
        motivationAdapter=new MotivationAdapter(view.getContext(),booksList,wishListBookViewModel,bagBooksViewModel,this::mtvListener);
        motivationRecycle.setLayoutManager(new GridLayoutManager(view.getContext(),3,
                LinearLayoutManager.VERTICAL,false));
        motivationRecycle.setAdapter(motivationAdapter);
    }

    @Override
    public void mtvListener(int position, List<Books> booksList) {
        MotivationFragmentDialog motivationFragmentDialog=new MotivationFragmentDialog(wishListBookViewModel,bagBooksViewModel);
        Bundle mArgs=new Bundle();
        mArgs.putString(MotivationFragments.BOOKSNAME,booksList.get(position).getBooksName());
        mArgs.putString(MotivationFragments.AUTHORS,booksList.get(position).getAuthors());
        mArgs.putString(MotivationFragments.BOOKSID,booksList.get(position).getBooksId());
        mArgs.putString(MotivationFragments.PRICE,booksList.get(position).getPrice());
        mArgs.putString(MotivationFragments.CATEGORY,booksList.get(position).getCategory());
        motivationFragmentDialog.setArguments(mArgs);
        motivationFragmentDialog.show(getChildFragmentManager(),"Motivation Fragments");
    }
}