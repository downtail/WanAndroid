package com.downtail.wanandroid.ui;

import android.view.MenuItem;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigationView)
    BottomNavigationView navigationView;
    @BindView(R.id.pagerContainer)
    ViewPager2 pagerContainer;

    private NavigationAdapter navigationAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvents() {
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationAdapter = new NavigationAdapter(this);
        pagerContainer.setAdapter(navigationAdapter);
        pagerContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onReload() {

    }

    @Override
    protected boolean supportSwipeBack() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                pagerContainer.setCurrentItem(0);
                break;
            case R.id.item_system:
                pagerContainer.setCurrentItem(1);
                break;
            case R.id.item_navigation:
                pagerContainer.setCurrentItem(2);
                break;
            case R.id.item_project:
                pagerContainer.setCurrentItem(3);
                break;
            case R.id.item_setting:
                pagerContainer.setCurrentItem(4);
                break;
        }
        return true;
    }
}
