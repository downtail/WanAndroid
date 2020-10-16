package com.downtail.wanandroid.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.downtail.wanandroid.ui.home.HomeFragment;
import com.downtail.wanandroid.ui.project.fragment.ProjectFragment;
import com.downtail.wanandroid.ui.service.ServiceFragment;
import com.downtail.wanandroid.ui.system.fragment.CombineFragment;

public class MainFragmentAdapter extends FragmentStateAdapter {

    public MainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return HomeFragment.getInstance();
        } else if (position == 1) {
            return CombineFragment.getInstance();
        } else if (position == 2) {
            return ProjectFragment.getInstance();
        }
        return ServiceFragment.getInstance();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
