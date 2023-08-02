package com.minthanhtike.bookshop.bookstorfrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;

public class PoemDialogFrag extends DialogFragment {
    String name,price,id,category,authors;
    TextView authorsTv,priceTv;
    BagBooksViewModel bagBooksViewModel;
    WishListBookViewModel wishListBookViewModel;
    Button toWshBtn,toBagBtn;

    public PoemDialogFrag(BagBooksViewModel bagBooksViewModel, WishListBookViewModel wishListBookViewModel) {
        this.bagBooksViewModel=bagBooksViewModel;
        this.wishListBookViewModel=wishListBookViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.poems_dialog_frag,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorsTv=view.findViewById(R.id.authors_tv_dlg_poem);
        priceTv=view.findViewById(R.id.price_tv_dlg_poem);
        toBagBtn=view.findViewById(R.id.to_bag_dlg_poem);
        toWshBtn=view.findViewById(R.id.to_wish_dlg_poem);

        Bundle pArgs=getArguments();
        if (pArgs!=null){
           name= pArgs.getString(PoemsFragment.BOOKSNAME);
           authors=pArgs.getString(PoemsFragment.AUTHORS);
           price=pArgs.getString(PoemsFragment.PRICE);
           category=pArgs.getString(PoemsFragment.CATEGORY);
           id=pArgs.getString(PoemsFragment.BOOKSID);
        }

        authorsTv.setText(authors);
        priceTv.setText(price);

        toBagBtn.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(name,authors,price,id,category,"1"));
        });

        toWshBtn.setOnClickListener(v -> {
            wishListBookViewModel.insert(new WishListBooks(name,authors,id,category));
        });
    }
}
