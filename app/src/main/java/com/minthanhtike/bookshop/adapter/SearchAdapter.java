package com.minthanhtike.bookshop.adapter;

import android.annotation.SuppressLint;
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

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    List<Books> booksList;
    SClickListener clickListener;
    @SuppressLint("NotifyDataSetChanged")
    public void setFilterList(List<Books> booksList){
        this.booksList=booksList;
        notifyDataSetChanged();
    }
    public SearchAdapter(Context context, List<Books> booksList,SClickListener clickListener) {
        this.context = context;
        this.booksList = booksList;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_recycle_items,parent,false);
        return new ViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        holder.bookName.setText(booksList.get(position).getBooksName());
        holder.authors.setText(booksList.get(position).getAuthors());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bookName,authors;
        ImageView imageView;
        SClickListener clickListener;
        public ViewHolder(@NonNull View itemView,SClickListener clickListener) {
            super(itemView);

            bookName=itemView.findViewById(R.id.textView_booksname);
            authors=itemView.findViewById(R.id.textView_authors);
            this.clickListener=clickListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            clickListener.clickItems(getAbsoluteAdapterPosition(),booksList);
        }
    }
    public interface SClickListener{
        void clickItems(int position,List<Books>booksList);
    }
}
