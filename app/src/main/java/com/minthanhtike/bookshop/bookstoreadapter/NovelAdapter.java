package com.minthanhtike.bookshop.bookstoreadapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.tobag.BagBooks;
import com.minthanhtike.bookshop.room.tobag.BagBooksViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBookViewModel;
import com.minthanhtike.bookshop.room.wishlistbooks.WishListBooks;

import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.ViewHolder> {
    private NovelItemListener novelItemListener;
    Context context;
    List<Books> booksList;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;

    public NovelAdapter(Context context, List<Books> booksList, WishListBookViewModel wishListBookViewModel, BagBooksViewModel bagBooksViewModel, NovelItemListener novelItemListener) {
        this.context = context;
        this.booksList = booksList;
        this.wishListBookViewModel = wishListBookViewModel;
        this.bagBooksViewModel = bagBooksViewModel;
        this.novelItemListener = novelItemListener;
    }

    @NonNull
    @Override
    public NovelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.novel_items, parent, false);
        return new ViewHolder(view, novelItemListener);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull NovelAdapter.ViewHolder holder, int position) {
        holder.novelName.setText(booksList.get(position).getBooksName());
        holder.authorNvl.setText(booksList.get(position).getAuthors());
        holder.toBag.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(), booksList.get(position).getPrice(),
                    booksList.get(position).getBooksId(), booksList.get(position).getCategory(),
                    String.valueOf(1)));
            Toast.makeText(context, "Items added to bag!", Toast.LENGTH_SHORT).show();
        });
        holder.toWshLt.setOnClickListener(v -> {
            wishListBookViewModel.insert(new WishListBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(), booksList.get(position).getBooksId(),
                    booksList.get(position).getCategory()));
            animateTo(holder.toWshLt);
        });
    }

    private void animateTo(@NonNull ImageButton imageButton) {
        int colorStart = imageButton.getSolidColor();
        int endColor = context.getColor(R.color.grey);
        ValueAnimator colorAnim = ObjectAnimator.ofInt(imageButton, "backgroundColor", colorStart, endColor);
        colorAnim.setDuration(500);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(1);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
        Toast.makeText(context, "Items added to bag!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView novelName, authorNvl;
        ImageButton toWshLt;
        Button toBag;
        NovelItemListener novelItemListener;

        public ViewHolder(@NonNull View itemView, NovelItemListener novelItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.novel_img);
            novelName = itemView.findViewById(R.id.books_name_novels);
            novelName.setSelected(true);
            authorNvl = itemView.findViewById(R.id.authors_novels);
            toBag = itemView.findViewById(R.id.to_bag_nvl);
            toWshLt = itemView.findViewById(R.id.to_wish_list_nvl);
            this.novelItemListener = novelItemListener;
            itemView.setOnClickListener(this::onClick);

        }

        @Override
        public void onClick(View v) {
            novelItemListener.nClick(getAbsoluteAdapterPosition(),booksList);
        }
    }

    public interface NovelItemListener {
        void nClick(int position, List<Books> booksList);
    }
}
