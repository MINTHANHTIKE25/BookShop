package com.minthanhtike.bookshop.bookstorfrag;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BookStoreFragAdapter extends FragmentStateAdapter {


    public BookStoreFragAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new NovelFragment();
            case 1: return new PoemsFragment();
            case 2: return new ScienceFragments();
            case 3: return new MotivationFragments();
            case 4: return new PoliticsFragments();
            default:return new NovelFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
