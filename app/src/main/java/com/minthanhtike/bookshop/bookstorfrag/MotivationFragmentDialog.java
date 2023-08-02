package com.minthanhtike.bookshop.bookstorfrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;

public class MotivationFragmentDialog extends DialogFragment {
    String name,authors,price,id,category;
    TextView authorsTv,priceTv;
    Button toBagBtn,toWshBtn;

    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;

    public MotivationFragmentDialog(WishListBookViewModel wishListBookViewModel, BagBooksViewModel bagBooksViewModel) {
        this.wishListBookViewModel = wishListBookViewModel;
        this.bagBooksViewModel = bagBooksViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.motivation_dialog_frag,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorsTv=view.findViewById(R.id.authors_tv_dlg_mtv);
        priceTv=view.findViewById(R.id.price_tv_dlg_mtv);
        toBagBtn=view.findViewById(R.id.to_bag_dlg_mtv);
        toWshBtn=view.findViewById(R.id.to_wish_dlg_mtv);

        Bundle bundle=getArguments();
        if (bundle!=null){
            name=bundle.getString(MotivationFragments.BOOKSNAME);
            authors=bundle.getString(MotivationFragments.AUTHORS);
            price=bundle.getString(MotivationFragments.PRICE);
            id=bundle.getString(MotivationFragments.BOOKSID);
            category=bundle.getString(MotivationFragments.CATEGORY);
        }

        authorsTv.setText(authors);
        priceTv.setText(price);

        toBagBtn.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(name,authors,price,id,category,"1"));
            Toast.makeText(getContext(), "item added to bag!", Toast.LENGTH_SHORT).show();
        });

        toWshBtn.setOnClickListener(v -> {
            wishListBookViewModel.insert(new WishListBooks(name,authors,id,category));
            Toast.makeText(getContext(), "item added to wishlist!", Toast.LENGTH_SHORT).show();
        });
    }
}
