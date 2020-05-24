package com.downtail.wanandroid.ui;

import com.downtail.wanandroid.ui.home.HomeFragment;
import com.downtail.wanandroid.ui.mine.SettingFragment;
import com.downtail.wanandroid.ui.navigation.NavigationFragment;
import com.downtail.wanandroid.ui.project.ProjectFragment;
import com.downtail.wanandroid.ui.system.SystemFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NavigationAdapter extends FragmentStateAdapter {

    public NavigationAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return HomeFragment.getInstance();
        } else if (position == 1) {
            return SystemFragment.getInstance();
        } else if (position == 2) {
            return NavigationFragment.getInstance();
        } else if (position == 3) {
            return ProjectFragment.getInstance();
        }
        return SettingFragment.getInstance();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
