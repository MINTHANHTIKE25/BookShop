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
import com.minthanhtike.bookshop.bookstoreadapter.PoemsAdapter;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;

import java.util.List;
import java.util.function.Predicate;

public class PoemsFragment extends Fragment implements PoemsAdapter.PListener{
    BooksViewModel booksViewModel;
    RecyclerView poemsRecycler;
    PoemsAdapter poemsAdapter;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    public static final String BOOKSNAME="BooksName",AUTHORS="Authors",PRICE="Price",BOOKSID="ID",
            CATEGORY="Category";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poems, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        poemsRecycler = view.findViewById(R.id.poems_recycle);
        booksViewModel=new ViewModelProvider(this).get(BooksViewModel.class);
        booksViewModel.getBooklist().observe(getViewLifecycleOwner(), new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> books) {
                books.removeIf(new Predicate<Books>() {
                    @Override
                    public boolean test(Books books) {
                        if (books.getCategory().equalsIgnoreCase("novels") |
                                books.getCategory().equalsIgnoreCase("politics") |
                                books.getCategory().equalsIgnoreCase("motivation") |
                                books.getCategory().equalsIgnoreCase("science")) {
                            return true;
                        }
                        return false;
                    }
                });
                setPoemsAdapter(view,books);
            }
        });
    }

    public void setPoemsAdapter(View view,List<Books>booksList){
        poemsRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 3,
                LinearLayoutManager.VERTICAL,false));
        bagBooksViewModel=new ViewModelProvider(this).get(BagBooksViewModel.class);
        wishListBookViewModel=new ViewModelProvider(this).get(WishListBookViewModel.class);
        poemsAdapter=new PoemsAdapter(view.getContext(), booksList,wishListBookViewModel,bagBooksViewModel,this::pListen);
        poemsRecycler.setAdapter(poemsAdapter);
    }

    @Override
    public void pListen(int position, List<Books> booksList) {
        PoemDialogFrag poemDialogFrag=new PoemDialogFrag(bagBooksViewModel,wishListBookViewModel);
        Bundle pArgs=new Bundle();
        pArgs.putString(BOOKSNAME,booksList.get(position).getBooksName());
        pArgs.putString(BOOKSID,booksList.get(position).getBooksId());
        pArgs.putString(PRICE,booksList.get(position).getPrice());
        pArgs.putString(AUTHORS,booksList.get(position).getAuthors());
        pArgs.putString(CATEGORY,booksList.get(position).getCategory());
        poemDialogFrag.setArguments(pArgs);
        poemDialogFrag.show(getChildFragmentManager(),"Poem Dialog");
    }
}