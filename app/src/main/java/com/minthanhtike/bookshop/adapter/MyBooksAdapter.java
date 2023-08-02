package com.minthanhtike.bookshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.usersbooks.UsersBook;
import com.minthanhtike.bookshop.room.usersbooks.UsersBookViewModel;
import com.minthanhtike.bookshop.room.usersinfo.Users;

import java.util.List;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.ViewHolder> {
    Context context;
    List<UsersBook> usersBooksList;
    MyBooksClickListener myBooksClickListener;

    public MyBooksAdapter(Context context, List<UsersBook> usersBooksList,MyBooksClickListener myBooksClickListener) {
        this.context = context;
        this.usersBooksList = usersBooksList;
        this.myBooksClickListener= myBooksClickListener;
    }

    @NonNull
    @Override
    public MyBooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mybooks_items,parent,false);
        return new ViewHolder(view,myBooksClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBooksAdapter.ViewHolder holder, int position) {
        holder.booksName.setText(usersBooksList.get(position).getBooksName());
        holder.authors.setText(usersBooksList.get(position).getAuthors());
    }

    @Override
    public int getItemCount() {
        return usersBooksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView booksName,authors;
        ImageView selectImg;
        MyBooksClickListener myBooksClickListener;
        public ViewHolder(@NonNull View itemView,MyBooksClickListener myBooksClickListener) {
            super(itemView);
            this.myBooksClickListener=myBooksClickListener;
            booksName=itemView.findViewById(R.id.books_nametv_mybooks);
            authors=itemView.findViewById(R.id.authors_tv_mybooks);
            selectImg=itemView.findViewById(R.id.select_img_user_books);
            selectImg.setVisibility(View.GONE);
            itemView.setOnClickListener(v -> {myBooksClickListener.myBooksOnClick(getAbsoluteAdapterPosition(),usersBooksList);});
            itemView.setOnLongClickListener(v -> {myBooksClickListener.myBooksOnLongClick(getAbsoluteAdapterPosition(),usersBooksList,selectImg);
            return true;});
        }
    }

    public interface MyBooksClickListener{
        void myBooksOnClick(int position,List<UsersBook> usersBookList);
        void myBooksOnLongClick(int position, List<UsersBook> usersBookList,ImageView selectImg);
    }
}
