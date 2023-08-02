package com.minthanhtike.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.tobag.BagBooks;

import java.util.List;

public class BagVoucherAdapter extends RecyclerView.Adapter<BagVoucherAdapter.ViewHolder> {
    Context context;
    List<BagBooks> bagBooksList;

    public BagVoucherAdapter(Context context, List<BagBooks> bagBooksList) {
        this.context = context;
        this.bagBooksList = bagBooksList;
    }

    @NonNull
    @Override
    public BagVoucherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.voucher_recycler_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BagVoucherAdapter.ViewHolder holder, int position) {
        int itemPrice = Integer.parseInt(bagBooksList.get(position).getPrice()) *
                Integer.parseInt(bagBooksList.get(position).getAmount());
        holder.booksNameTv.setText(bagBooksList.get(position).getBooksName());
        holder.priceTv.setText(String.valueOf(itemPrice));
    }

    @Override
    public int getItemCount() {
        return bagBooksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView booksNameTv, priceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            booksNameTv = itemView.findViewById(R.id.name_voucher_tv);
            priceTv = itemView.findViewById(R.id.price_voucher_tv);
        }
    }
}
