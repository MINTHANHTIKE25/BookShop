package com.minthanhtike.bookshop.bookstoreadapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

public class MotivationAdapter extends RecyclerView.Adapter<MotivationAdapter.ViewHolder> {
    Context context;
    List<Books>booksList;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    private MtvListener mtvListener;
    public MotivationAdapter(Context context, List<Books> booksList, WishListBookViewModel wishListBookViewModel,BagBooksViewModel bagBooksViewModel,MtvListener mtvListener) {
        this.context = context;
        this.booksList = booksList;
        this.wishListBookViewModel=wishListBookViewModel;
        this.bagBooksViewModel=bagBooksViewModel;
        this.mtvListener=mtvListener;
    }

    @NonNull
    @Override
    public MotivationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.motivation_items,parent,false);
        return new ViewHolder(view,mtvListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationAdapter.ViewHolder holder, int position) {
        holder.nameMtv.setText(booksList.get(position).getBooksName());
        holder.authorsMtv.setText(booksList.get(position).getAuthors());
        holder.toBag.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(), booksList.get(position).getPrice(),
                    booksList.get(position).getBooksId(), booksList.get(position).getCategory(),
                    String.valueOf(1)));
            Toast.makeText(context, "Items added to bag!", Toast.LENGTH_SHORT).show();
        });
        holder.toWishlist.setOnClickListener(v -> {
            animateTo(holder.toWishlist);
            wishListBookViewModel.insert(new WishListBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(),booksList.get(position).getBooksId(),
                    booksList.get(position).getCategory()));
            Toast.makeText(context, "Items added to wishlist!", Toast.LENGTH_SHORT).show();

        });
    }
    private void animateTo(@NonNull ImageButton imageButton){
        int colorStart = imageButton.getSolidColor();
        int endColor = context.getColor(R.color.grey);
        ValueAnimator colorAnim = ObjectAnimator.ofInt(imageButton, "backgroundColor", colorStart, endColor);
        colorAnim.setDuration(500);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(1);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }
    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView  motivateImg;
        TextView nameMtv,authorsMtv;
        ImageButton toWishlist;
        Button toBag;
        MtvListener mtvListener;
        public ViewHolder(@NonNull View itemView, MtvListener mtvListener) {
            super(itemView);
            motivateImg=itemView.findViewById(R.id.motivation_img);
            nameMtv=itemView.findViewById(R.id.books_name_motivation);
            authorsMtv=itemView.findViewById(R.id.authors_motivation);
            toBag=itemView.findViewById(R.id.to_bag_mvt);
            toWishlist=itemView.findViewById(R.id.to_wish_list_mvt);
            this.mtvListener=mtvListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            mtvListener.mtvListener(getAbsoluteAdapterPosition(),booksList);
        }
    }

    public interface MtvListener{
        void mtvListener(int position,List<Books> booksList);
    }
}
