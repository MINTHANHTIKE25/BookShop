package com.minthanhtike.bookshop.fragments;

import android.app.Dialog;
import android.app.GameManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.adapter.BagVoucherAdapter;
import com.minthanhtike.bookshop.adapter.MyBooksAdapter;
import com.minthanhtike.bookshop.login.LogIn;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.usersbooks.UsersBook;
import com.minthanhtike.bookshop.room.usersbooks.UsersBookViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BagDiaLogFragments extends DialogFragment {
    private static final String TAG="MY BAG DIALOG";
    double totalMoney;
    Button confirmBtn,cancelBtn;
    RecyclerView voucherRecycle;
    BagBooksViewModel bagBooksViewModel;
    BagVoucherAdapter bagVoucherAdapter;
    UsersBookViewModel usersBookViewModel;
    TextView totalTv;
    List<BagBooks> booksList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bag_dialog_frag,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle mArgs=getArguments();
        if (mArgs != null) {
            totalMoney=mArgs.getInt("SUBTOTAL");
        }
        totalTv=view.findViewById(R.id.ttmoney_vc_tv);
        voucherRecycle=view.findViewById(R.id.voucher_recycler);
        confirmBtn=view.findViewById(R.id.confirm_voucher);
        cancelBtn=view.findViewById(R.id.cancel_voucher);
        usersBookViewModel=new ViewModelProvider(this).get(UsersBookViewModel.class);
        bagBooksViewModel=new ViewModelProvider(this).get(BagBooksViewModel.class);
        SharedPreferences sh= requireActivity().getSharedPreferences(LogIn.USERLOGIN, Context.MODE_PRIVATE);

        bagBooksViewModel.getBagBooksList().observe(getViewLifecycleOwner(),bagBooksList -> {
            booksList.addAll(bagBooksList);
            settingVoucher(view,bagBooksList);
        });
        cancelBtn.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Closing dialog");
            Objects.requireNonNull(getDialog()).dismiss();
        });
        confirmBtn.setOnClickListener(v -> {
            if (!sh.getBoolean(LogIn.ISREGISTER, false)){
                Toast.makeText(getActivity(), "You Have not registered!", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getDialog()).dismiss();
            }else {
                booksList.forEach(new Consumer<BagBooks>() {
                    @Override
                    public void accept(BagBooks bagBooks) {
                        usersBookViewModel.insert(new UsersBook(bagBooks.getBooksName(),
                                bagBooks.getAuthors(),bagBooks.getBooksId(),bagBooks.getCategory()));
                    }
                });
                bagBooksViewModel.deleteAllData();
                Objects.requireNonNull(getDialog()).dismiss();
                Toast.makeText(getActivity(), "Thank your for buying !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void settingVoucher(View view, List<BagBooks> bagBooksList){
        bagVoucherAdapter=new BagVoucherAdapter(view.getContext(), bagBooksList);
        voucherRecycle.setLayoutManager(new LinearLayoutManager(view.getContext()));
        voucherRecycle.setAdapter(bagVoucherAdapter);
        totalMoney=totalMoney+(totalMoney/100)*3.5;
        totalTv.setText(String.valueOf(totalMoney));
    }
}
