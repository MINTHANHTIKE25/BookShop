package com.minthanhtike.bookshop.bookstoreadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minthanhtike.bookshop.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class IndicatorAdapter extends SliderViewAdapter<IndicatorAdapter.IndicatorVH> {
    Context context;

    public IndicatorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public IndicatorAdapter.IndicatorVH onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.indicator_layout,null);
        return new IndicatorVH(view);
    }

    @Override
    public void onBindViewHolder(IndicatorAdapter.IndicatorVH viewHolder, int position) {

    }

    @Override
    public int getCount() {
        return 3;
    }

    public class IndicatorVH extends ViewHolder {
        public IndicatorVH(View itemView) {
            super(itemView);
        }
    }
}
