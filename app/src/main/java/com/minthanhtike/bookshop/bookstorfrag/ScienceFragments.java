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
import com.minthanhtike.bookshop.bookstoreadapter.ScienceAdapter;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;

import java.util.List;
import java.util.function.Predicate;

public class ScienceFragments extends Fragment implements ScienceAdapter.SListener{
    RecyclerView scienceRecycle;
    BooksViewModel booksViewModel;
    ScienceAdapter scienceAdapter;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_science_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scienceRecycle = view.findViewById(R.id.science_recycle);

        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        booksViewModel.getBooklist().observe(getViewLifecycleOwner(), new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> booksList) {
                booksList.removeIf(books -> {
                    if (books.getCategory().equalsIgnoreCase("novels") |
                            books.getCategory().equalsIgnoreCase("poems") |
                            books.getCategory().equalsIgnoreCase("motivation") |
                            books.getCategory().equalsIgnoreCase("politics")) {
                        return true;
                    }
                    return false;
                });
                setScienceAdapter(view,booksList);
            }
        });
    }

    public void setScienceAdapter(View view, List<Books> booksList) {
        wishListBookViewModel=new ViewModelProvider(this).get(WishListBookViewModel.class);
        bagBooksViewModel=new ViewModelProvider(this).get(BagBooksViewModel.class);
        scienceRecycle.setLayoutManager(new GridLayoutManager(view.getContext(), 3,
                LinearLayoutManager.VERTICAL, false));
        scienceAdapter = new ScienceAdapter(view.getContext(), booksList,wishListBookViewModel,bagBooksViewModel,this::sListen);
        scienceRecycle.setAdapter(scienceAdapter);
    }

    @Override
    public void sListen(int position, List<Books> booksList) {
        ScienceDialogFrag scienceDialogFrag=new ScienceDialogFrag(bagBooksViewModel,wishListBookViewModel);
        Bundle sargs=new Bundle();
        sargs.putString(NovelFragment.BOOKSNAME,booksList.get(position).getBooksName());
        sargs.putString(NovelFragment.AUTHORS,booksList.get(position).getAuthors());
        sargs.putString(NovelFragment.BOOKSID,booksList.get(position).getBooksId());
        sargs.putString(NovelFragment.PRICE,booksList.get(position).getPrice());
        sargs.putString(NovelFragment.CATEGORY,booksList.get(position).getCategory());
        scienceDialogFrag.setArguments(sargs);
        scienceDialogFrag.show(getChildFragmentManager(),"Science Dialog.");
    }
}