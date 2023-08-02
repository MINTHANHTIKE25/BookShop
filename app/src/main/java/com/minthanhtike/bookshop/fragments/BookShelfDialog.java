package com.minthanhtike.bookshop.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

public class BookShelfDialog extends DialogFragment {
    BagBooksViewModel bagBooksViewModel;
    Button toBagBtn;
    TextView authorsTv, priceTv, priceText;
    public static String BOOKSNAME = "BooksName", AUTHORS = "Authors", PRICE = "Price", BOOKSID = "ID",
            CATEGORY = "Category";
    String name, authors, price, id, category, isMybooks;

    public BookShelfDialog(BagBooksViewModel bagBooksViewModel) {
        this.bagBooksViewModel = bagBooksViewModel;
    }

    public BookShelfDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookshelf_dialog_fragments, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorsTv = view.findViewById(R.id.authors_tv_dlg_bs);
        priceText = view.findViewById(R.id.price_dlg_bs);
        priceTv = view.findViewById(R.id.price_tv_dlg_bs);
        toBagBtn = view.findViewById(R.id.to_bag_dlg_bs);
        Bundle arguments = getArguments();
        if (arguments != null) {
            name = arguments.getString(BOOKSNAME);
            authors = arguments.getString(AUTHORS);
            id = arguments.getString(BOOKSID);
            price = arguments.getString(PRICE);
            category = arguments.getString(CATEGORY);
            isMybooks = arguments.getString("MYBOOKS");
        }
        if (isMybooks!=null & price==null ) {

            toBagBtn.setText("Open Books");
            priceTv.setVisibility(View.GONE);
            priceText.setVisibility(View.GONE);
            authorsTv.setText(authors);
        } else {
            authorsTv.setText(authors);
            priceTv.setText(price);
        }
    }
}
