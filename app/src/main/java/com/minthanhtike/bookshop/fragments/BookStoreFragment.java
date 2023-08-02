package com.minthanhtike.bookshop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.bookstoreadapter.ImgSliderAdapter;
import com.minthanhtike.bookshop.bookstoreadapter.IndicatorAdapter;
import com.minthanhtike.bookshop.bookstorfrag.BookStoreFragAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;


public class BookStoreFragment extends Fragment {
    TabLayout tabLayout;
    private SliderView sliderView,indicatorSlider;
    private ImgSliderAdapter imgSliderAdapter;
    private IndicatorAdapter indicatorAdapter;
    private int []image;
    ViewPager2 viewPager2;
    BookStoreFragAdapter bookStoreFragAdapter;

    public BookStoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout=view.findViewById(R.id.tab_layout_bookstore);
        viewPager2=view.findViewById(R.id.view_pager_bookstore);
        bookStoreFragAdapter=new BookStoreFragAdapter(getChildFragmentManager(),getViewLifecycleOwner().getLifecycle());
        viewPager2.setAdapter(bookStoreFragAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });

        //for sliding image
        image=new int[]{R.drawable.img_sliding_lowqty,R.drawable.read_books_lowqty,R.drawable.books_photo};
        sliderView=view.findViewById(R.id.image_slider_bookstore);
        indicatorSlider=view.findViewById(R.id.slider_indicator);
        imgSliderAdapter=new ImgSliderAdapter(view.getContext(),image);
        //image slider
        sliderView.setSliderAdapter(imgSliderAdapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        //slider indicators
        indicatorSlider=view.findViewById(R.id.slider_indicator);
        indicatorAdapter=new IndicatorAdapter(view.getContext());
        indicatorSlider.setSliderAdapter(indicatorAdapter);
        indicatorSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        indicatorSlider.setAutoCycle(true);
        indicatorSlider.startAutoCycle();
    }

}