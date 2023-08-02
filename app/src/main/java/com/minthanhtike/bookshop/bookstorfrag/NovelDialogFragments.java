package com.minthanhtike.bookshop.bookstorfrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;

public class NovelDialogFragments extends DialogFragment {
    ImageView booksImg;
    TextView booksTv,authorsTv,priceTv;
    Button toBagBtn,toWishBtn;
    String name,authors,price,id,category;
    BagBooksViewModel bagBooksViewModel;
    WishListBookViewModel wishListBookViewModel;

    public NovelDialogFragments(BagBooksViewModel bagBooksViewModel, WishListBookViewModel wishListBookViewModel) {
        this.bagBooksViewModel = bagBooksViewModel;
        this.wishListBookViewModel = wishListBookViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.novel_dialog,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle gargs=getArguments();
        if (gargs!=null){
            name=gargs.getString(NovelFragment.BOOKSNAME);
            authors= gargs.getString(NovelFragment.AUTHORS);
            price=gargs.getString(NovelFragment.PRICE);
            id=gargs.getString(NovelFragment.BOOKSID);
            category= gargs.getString(NovelFragment.CATEGORY);

        }
        authorsTv=view.findViewById(R.id.authors_tv_dlg);
        priceTv=view.findViewById(R.id.price_tv_dlg);
        booksImg=view.findViewById(R.id.books_img_dlg);
        booksTv=view.findViewById(R.id.books_review_dlg);
        toBagBtn=view.findViewById(R.id.to_bag_dlg);
        toWishBtn=view.findViewById(R.id.to_wish_dlg);

       authorsTv.setText(authors);
       priceTv.setText(price);

       toBagBtn.setOnClickListener(v -> {
           bagBooksViewModel.insert(new BagBooks(name,authors,price,id,category,"1"));
       });

       toWishBtn.setOnClickListener(v -> {
           wishListBookViewModel.insert(new WishListBooks(name,authors,id,category));
       });
    }
}
