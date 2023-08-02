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

public class ScienceDialogFrag extends DialogFragment {
    BagBooksViewModel bagBooksViewModel;
    WishListBookViewModel wishListBookViewModel;
    TextView authorTv,priceTv;
    Button toBagBtn,toWshBtn;
    String name,author,id,price,category;
    public ScienceDialogFrag(BagBooksViewModel bagBooksViewModel, WishListBookViewModel wishListBookViewModel) {
        this.bagBooksViewModel = bagBooksViewModel;
        this.wishListBookViewModel = wishListBookViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_science,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorTv=view.findViewById(R.id.authors_tv_dlg_scn);
        priceTv=view.findViewById(R.id.price_tv_dlg_scn);
        toBagBtn=view.findViewById(R.id.to_bag_dlg_scn);
        toWshBtn=view.findViewById(R.id.to_wish_dlg_scn);
        Bundle args=getArguments();
        if (args!=null){
            name=args.getString(NovelFragment.BOOKSNAME);
            author=args.getString(NovelFragment.AUTHORS);
            id=args.getString(NovelFragment.BOOKSID);
            price=args.getString(NovelFragment.PRICE);
            category=args.getString(NovelFragment.CATEGORY);
        }
        if (!author.isEmpty() & !price.isEmpty()){
            authorTv.setText(author);
            priceTv.setText(price);
        }

        toBagBtn.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(name,author,price,id,category,"1"));
        });

        toWshBtn.setOnClickListener(v -> {
            wishListBookViewModel.insert(new WishListBooks(name,author,id,category));
        });
    }
}
