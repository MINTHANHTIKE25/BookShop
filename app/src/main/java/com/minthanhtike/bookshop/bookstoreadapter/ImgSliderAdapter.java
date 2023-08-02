package com.minthanhtike.bookshop.bookstoreadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.minthanhtike.bookshop.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class ImgSliderAdapter extends SliderViewAdapter<ImgSliderAdapter.ImgSliderViewHolder> {
    Context context;
    int []img;

    public ImgSliderAdapter(Context context, int[] img) {
        this.context = context;
        this.img = img;
    }

    @Override
    public ImgSliderAdapter.ImgSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.slider_layout,null);
        return new ImgSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImgSliderAdapter.ImgSliderViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(img[position]);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    public class ImgSliderViewHolder extends ViewHolder {
        ImageView imageView;
        public ImgSliderViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.slider_image);
        }
    }
}
