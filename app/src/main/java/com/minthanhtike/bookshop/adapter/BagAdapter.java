package com.minthanhtike.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;

import java.util.Arrays;
import java.util.List;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ViewHolder> {
    List<BagBooks> booksList;
    Context context;
    BagBooksViewModel bagBooksViewModel;
    int resultPrice = 0;

    IBagAdapter mListener;

    public BagAdapter(List<BagBooks> booksList, Context context, BagBooksViewModel bagBooksViewModel, IBagAdapter mListener) {
        this.booksList = booksList;
        this.context = context;
        this.bagBooksViewModel = bagBooksViewModel;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public BagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bag_recycle_items, parent, false);
        return new ViewHolder(view);
    }

    private int originalPrice(int position) {
        return Integer.parseInt(booksList.get(position).getPrice());
    }

    @Override
    public void onBindViewHolder(@NonNull BagAdapter.ViewHolder holder, int position) {
        holder.nameTv.setText(booksList.get(position).getBooksName());
        holder.authorsTv.setText(booksList.get(position).getAuthors());
        holder.amountTv.setText(booksList.get(position).getAmount());
        holder.priceTv.setText(String.valueOf(Integer.parseInt(booksList.get(position).getAmount())
                * Integer.parseInt(booksList.get(position).getPrice())));
        holder.plusFlBtn.setOnClickListener(v -> {
            String amount = booksList.get(position).getAmount();
            int tempAmt = Integer.parseInt(amount) + 1;
            bagBooksViewModel.update(new BagBooks(booksList.get(position).getTbId(),
                    booksList.get(position).getBooksName(), booksList.get(position).getAuthors(),
                    booksList.get(position).getPrice(), booksList.get(position).getBooksId(),
                    booksList.get(position).getCategory(), String.valueOf(tempAmt)));
        });
        holder.minusFlBtn.setOnClickListener(v -> {
            String amount = booksList.get(position).getAmount();
            int amtRe = Integer.parseInt(amount) - 1;
            if (amtRe <= 0) {
                bagBooksViewModel.delete(booksList.get(position));
            } else {
                bagBooksViewModel.update(new BagBooks(booksList.get(position).getTbId(),
                        booksList.get(position).getBooksName(), booksList.get(position).getAuthors(),
                        booksList.get(position).getPrice(), booksList.get(position).getBooksId(),
                        booksList.get(position).getCategory(), String.valueOf(amtRe)));
            }
        });
        //calculating the total price for the books
        int[] total = new int[booksList.size()];
        for (int i = 0; i < booksList.size(); i++) {
          total[i]= Integer.parseInt(booksList.get(i).getAmount()) *
                  Integer.parseInt(booksList.get(i).getPrice());
        }
        mListener.mListener(total, booksList.size());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FloatingActionButton minusFlBtn, plusFlBtn;
        TextView amountTv, priceTv, nameTv, authorsTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTv = itemView.findViewById(R.id.bag_items_amount);
            priceTv = itemView.findViewById(R.id.books_price_tv);
            nameTv = itemView.findViewById(R.id.books_name_bag);
            authorsTv = itemView.findViewById(R.id.authors_bag);
            minusFlBtn = itemView.findViewById(R.id.minus_button);
            plusFlBtn = itemView.findViewById(R.id.plus_button);
        }
    }

    public interface IBagAdapter {
        void mListener(int[] result, int count);
    }
}
