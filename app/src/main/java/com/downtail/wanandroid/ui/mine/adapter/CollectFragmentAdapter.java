package com.downtail.wanandroid.ui.mine.adapter;

import com.downtail.wanandroid.ui.mine.fragment.CollectFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CollectFragmentAdapter extends FragmentStateAdapter {

    public CollectFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return CollectFragment.getInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
