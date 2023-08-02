package com.minthanhtike.bookshop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.adapter.BagAdapter;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class BagFragments extends Fragment implements BagAdapter.IBagAdapter {
    public TextView reMoneyTv;
    RecyclerView bagRecycler;
    BagAdapter bagAdapter;
    BagBooksViewModel bagBooksViewModel;
    Button confirmBtn;
    int reMoney = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bag_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reMoneyTv = view.findViewById(R.id.fianl_money_tv);
        confirmBtn = view.findViewById(R.id.final_bag_btn);
        reMoneyTv.setText(String.valueOf(0));
        bagRecycler = view.findViewById(R.id.bag_recycler);
        bagBooksViewModel = new ViewModelProvider(this).get(BagBooksViewModel.class);
        bagBooksViewModel.getBagBooksList().observe(getViewLifecycleOwner(), new Observer<List<BagBooks>>() {
            @Override
            public void onChanged(List<BagBooks> bagBooks) {
                setBagRecycler(view, bagBooks);
//                if (!bagBooks.isEmpty()){
//                    bagBooks.stream().map(new Function<BagBooks, Object>() {
//                        @Override
//                        public Object apply(BagBooks bagBooks) {
//                            return Integer.parseInt(bagBooks.getPrice())*Integer.parseInt(bagBooks.getAmount());
//                        }
//                    }).collect(Collectors.toList()).forEach(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object o) {
//                            resultMoney.add(o);
//                        }
//                    });
//
//                    for (int i = 0; i < resultMoney.size(); i++) {
//                        reMoney+=Integer.parseInt(resultMoney.get(i).toString());
//                    }
//                    reMoneyTv.setText(String.valueOf(reMoney));
//                    resultMoney.clear();
//                }

//                for (int i = 0; i < bagBooks.size(); i++) {
//                    result=Integer.parseInt(bagBooks.get(i).getPrice())
//                            *Integer.parseInt(bagBooks.get(i).getAmount());
//                }
//                reMoneyTv.setText(String.valueOf(result));
            }
        });

    }

    void setBagRecycler(View view, List<BagBooks> bagBooksList) {
        bagAdapter = new BagAdapter(bagBooksList, view.getContext(), bagBooksViewModel, this);
        bagRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        bagRecycler.setAdapter(bagAdapter);
        confirmBtn.setOnClickListener(v -> {
            Bundle args=new Bundle();
            args.putInt("SUBTOTAL",reMoney);
            BagDiaLogFragments bagDiaLogFragments=new BagDiaLogFragments();
            bagDiaLogFragments.setArguments(args);
            bagDiaLogFragments.show(getChildFragmentManager(),"Bag Fragments");
        });
    }

    @Override
    public void mListener(int[] result, int position) {
        reMoney= Arrays.stream(result).sum();
        reMoneyTv.setText(String.valueOf(Arrays.stream(result).sum()));
    }
    
}