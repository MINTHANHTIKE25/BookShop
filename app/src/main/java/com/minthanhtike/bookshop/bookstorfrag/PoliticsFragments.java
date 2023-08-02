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
import com.minthanhtike.bookshop.bookstoreadapter.PoliticsAdapter;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;

import java.util.List;
import java.util.function.Predicate;

public class PoliticsFragments extends Fragment implements PoliticsAdapter.PListener{
    BooksViewModel booksViewModel;
    RecyclerView politicRecycle;
    PoliticsAdapter politicsAdapter;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_politics_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        politicRecycle=view.findViewById(R.id.politics_recycle);
        booksViewModel=new ViewModelProvider(this).get(BooksViewModel.class);
        booksViewModel.getBooklist().observe(getViewLifecycleOwner(), new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> booksList) {
                booksList.removeIf(books -> {
                    if (books.getCategory().equalsIgnoreCase("novels")|
                    books.getCategory().equalsIgnoreCase("poems")|
                    books.getCategory().equalsIgnoreCase("science")|
                    books.getCategory().equalsIgnoreCase("motivation")){
                        return true;
                    }
                    return false;
                });
                setPoliticsAdapter(view,booksList);
            }
        });
    }
    public void setPoliticsAdapter(View view,List<Books>booksList){
        politicRecycle.setLayoutManager(new GridLayoutManager(view.getContext(),3,
                LinearLayoutManager.VERTICAL,false));
        bagBooksViewModel=new ViewModelProvider(this).get(BagBooksViewModel.class);
        wishListBookViewModel=new ViewModelProvider(this).get(WishListBookViewModel.class);
        politicsAdapter=new PoliticsAdapter(view.getContext(), booksList,wishListBookViewModel,bagBooksViewModel,this::plistener);
        politicRecycle.setAdapter(politicsAdapter);
    }

    @Override
    public void plistener(int position, List<Books> booksList) {
        PoliticDialogFrag politicDialogFrag=new PoliticDialogFrag(wishListBookViewModel,bagBooksViewModel);
        Bundle bundle=new Bundle();
        bundle.putString(NovelFragment.BOOKSNAME,booksList.get(position).getBooksName());
        bundle.putString(NovelFragment.BOOKSID,booksList.get(position).getBooksId());
        bundle.putString(NovelFragment.AUTHORS,booksList.get(position).getAuthors());
        bundle.putString(NovelFragment.PRICE,booksList.get(position).getPrice());
        bundle.putString(NovelFragment.CATEGORY,booksList.get(position).getCategory());
        politicDialogFrag.setArguments(bundle);
        politicDialogFrag.show(getChildFragmentManager(),"Politic Dialog");
    }
}