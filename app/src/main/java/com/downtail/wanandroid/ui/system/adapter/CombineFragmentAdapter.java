package com.downtail.wanandroid.ui.system.adapter;

import com.downtail.wanandroid.ui.system.fragment.NavigationFragment;
import com.downtail.wanandroid.ui.system.fragment.SystemFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CombineFragmentAdapter extends FragmentStateAdapter {

    public CombineFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return SystemFragment.getInstance();
        }
        return NavigationFragment.getInstance();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
