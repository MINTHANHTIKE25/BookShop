package com.minthanhtike.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;

import java.util.List;

public class WishListBooksAdapter extends RecyclerView.Adapter<WishListBooksAdapter.ViewHolder> {
    Context context;
    List<WishListBooks> wishListBooksList;
    BSItemListener bsItemListener;

    public WishListBooksAdapter(Context context, List<WishListBooks> wishListBooksList, BSItemListener bsItemListener) {
        this.context = context;
        this.wishListBooksList = wishListBooksList;
        this.bsItemListener = bsItemListener;
    }

    @NonNull
    @Override
    public WishListBooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_items, parent, false);
        return new ViewHolder(view, bsItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListBooksAdapter.ViewHolder holder, int position) {
        holder.bookTv.setText(wishListBooksList.get(position).getBooksName());
        holder.authorTv.setText(wishListBooksList.get(position).getAuthors());
    }

    @Override
    public int getItemCount() {
        return wishListBooksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BSItemListener bsItemListener;
        ImageView imageView, selectedImg;
        TextView bookTv, authorTv;

        public ViewHolder(@NonNull View itemView, BSItemListener bsItemListener) {
            super(itemView);
            this.bsItemListener = bsItemListener;
            imageView = itemView.findViewById(R.id.img_wish);
            bookTv = itemView.findViewById(R.id.book_name_tv_wish);
            authorTv = itemView.findViewById(R.id.author_wish);
            selectedImg = itemView.findViewById(R.id.selected_img);
            selectedImg.setVisibility(View.GONE);
            itemView.setOnClickListener(v -> {
                bsItemListener.onItemClick(getAbsoluteAdapterPosition(), wishListBooksList);
            });
            itemView.setOnLongClickListener(v -> {
                bsItemListener.onItemLongClick(getAbsoluteAdapterPosition(), wishListBooksList,selectedImg);
                return true;
            });
        }
    }

    public interface BSItemListener {
        void onItemClick(int position, List<WishListBooks> wishListBooks);

        void onItemLongClick(int position, List<WishListBooks> wishListBooks,ImageView selectedImg);
    }
}
