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

public class ScienceAdapter extends RecyclerView.Adapter<ScienceAdapter.ViewHolder> {
    Context context;
    List<Books> booksList;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    private SListener sListener;

    public ScienceAdapter(Context context, List<Books> booksList,WishListBookViewModel wishListBookViewModel,BagBooksViewModel bagBooksViewModel,SListener sListener) {
        this.context = context;
        this.booksList = booksList;
        this.wishListBookViewModel = wishListBookViewModel;
        this.bagBooksViewModel=bagBooksViewModel;
        this.sListener=sListener;
    }

    @NonNull
    @Override
    public ScienceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.science_items,parent,false);
        return new ViewHolder(view,sListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScienceAdapter.ViewHolder holder, int position) {
        holder.nameScn.setText(booksList.get(position).getBooksName());
        holder.authorsScn.setText(booksList.get(position).getAuthors());
        holder.toBag.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(), booksList.get(position).getPrice(),
                    booksList.get(position).getBooksId(), booksList.get(position).getCategory(),
                    String.valueOf(1)));
            Toast.makeText(context, "Items added to bag!", Toast.LENGTH_SHORT).show();
        });
        holder.toWishlist.setOnClickListener(v -> {
            wishListBookViewModel.insert(new WishListBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(),booksList.get(position).getBooksId(),
                    booksList.get(position).getCategory()));
            Toast.makeText(context, "Items added to wishlist!", Toast.LENGTH_SHORT).show();
            animateTo(holder.toWishlist);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SListener sListener;
        ImageView scienceImg;
        TextView nameScn,authorsScn;
        ImageButton toWishlist;
        Button toBag;
        public ViewHolder(@NonNull View itemView, SListener sListener) {
            super(itemView);
            scienceImg=itemView.findViewById(R.id.science_img);
            nameScn=itemView.findViewById(R.id.books_name_science);
            nameScn.setSelected(true);
            authorsScn=itemView.findViewById(R.id.authors_science);
            toBag=itemView.findViewById(R.id.to_bag_scn);
            toWishlist=itemView.findViewById(R.id.to_wish_list_scn);
            this.sListener=sListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            sListener.sListen(getAdapterPosition(),booksList);
        }
    }
    public interface SListener{
        void sListen(int position,List<Books> booksList);
    }
}
