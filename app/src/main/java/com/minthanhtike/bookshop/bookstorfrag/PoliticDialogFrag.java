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

public class PoliticDialogFrag extends DialogFragment {
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    TextView authorsTv,priceTv;
    Button toBagBtn,toWshBtn;
    String name,price,id,category,author;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_politics,container,false);
    }

    public PoliticDialogFrag(WishListBookViewModel wishListBookViewModel, BagBooksViewModel bagBooksViewModel) {
        this.wishListBookViewModel = wishListBookViewModel;
        this.bagBooksViewModel = bagBooksViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorsTv=view.findViewById(R.id.authors_tv_dlg_plt);
        priceTv=view.findViewById(R.id.price_tv_dlg_plt);
        toBagBtn=view.findViewById(R.id.to_bag_dlg_plt);
        toWshBtn=view.findViewById(R.id.to_wish_dlg_plt);

        Bundle args=getArguments();
        if (args!=null){
            name=args.getString(NovelFragment.BOOKSNAME);
            price=args.getString(NovelFragment.PRICE);
            id= args.getString(NovelFragment.BOOKSID);
            category= args.getString(NovelFragment.CATEGORY);
            author= args.getString(NovelFragment.AUTHORS);
        }

        authorsTv.setText(author);
        priceTv.setText(price);

        toBagBtn.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(name,author,price,id,category,"1"));
        });

        toWshBtn.setOnClickListener(v -> {
            wishListBookViewModel.insert(new WishListBooks(name,author,id,category));
        });
    }
}
