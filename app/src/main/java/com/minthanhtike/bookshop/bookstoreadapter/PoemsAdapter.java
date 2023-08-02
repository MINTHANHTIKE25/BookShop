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

public class PoemsAdapter extends RecyclerView.Adapter<PoemsAdapter.ViewHolder> {
    Context context;
    List<Books> booksList;
    WishListBookViewModel wishListBookViewModel;
    BagBooksViewModel bagBooksViewModel;
    private PListener pListener;
    public PoemsAdapter(Context context, List<Books> booksList, WishListBookViewModel wishListBookViewModel,BagBooksViewModel bagBooksViewModel,PListener pListener) {
        this.context = context;
        this.booksList = booksList;
        this.wishListBookViewModel=wishListBookViewModel;
        this.bagBooksViewModel=bagBooksViewModel;
        this.pListener=pListener;
    }

    @NonNull
    @Override
    public PoemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.poems_recycle_items,parent,false);
        return new ViewHolder(view,pListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemsAdapter.ViewHolder holder, int position) {

        holder.poemsName.setText(booksList.get(position).getBooksName());
        holder.poemAuthors.setText(booksList.get(position).getAuthors());
        holder.toBagImgBtn.setOnClickListener(v -> {
            bagBooksViewModel.insert(new BagBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(), booksList.get(position).getPrice(),
                    booksList.get(position).getBooksId(), booksList.get(position).getCategory(),
                    String.valueOf(1)));
            Toast.makeText(context, "Items added to bag!", Toast.LENGTH_SHORT).show();

        });
        holder.toWishImgBtn.setOnClickListener(v -> {
            wishListBookViewModel.insert(new WishListBooks(booksList.get(position).getBooksName(),
                    booksList.get(position).getAuthors(),booksList.get(position).getBooksId(),
                    booksList.get(position).getCategory()));
            Toast.makeText(context, "Items added to wishlist!", Toast.LENGTH_SHORT).show();
            animateTo(holder.toWishImgBtn);
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
        PListener pListener;
        ImageView poemImg;
        TextView poemsName,poemAuthors;
        ImageButton toWishImgBtn;
        Button toBagImgBtn;
        public ViewHolder(@NonNull View itemView, PListener pListener) {
            super(itemView);
            poemImg=itemView.findViewById(R.id.poems_img);
            poemsName=itemView.findViewById(R.id.books_name_poems);
            poemsName.setSelected(true);
            poemAuthors=itemView.findViewById(R.id.authors_poems);
            toWishImgBtn=itemView.findViewById(R.id.to_wish_list_btn);
            toBagImgBtn=itemView.findViewById(R.id.to_bag_btn);
            this.pListener=pListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            pListener.pListen(getAdapterPosition(),booksList);
        }
    }

    public interface PListener{
        void pListen(int position,List<Books> booksList);
    }
}
