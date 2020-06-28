package com.downtail.wanandroid.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.main.MainContract;
import com.downtail.wanandroid.presenter.main.MainPresenter;
import com.downtail.wanandroid.utils.ExitUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigationView)
    BottomNavigationView navigationView;
    @BindView(R.id.pagerContainer)
    ViewPager2 pagerContainer;

    @Override
    protected void initBeforeBindLayout() {
        setTheme(R.style.MainTheme);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvents() {
        navigationView.setOnNavigationItemSelectedListener(this);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(this);
        pagerContainer.setOffscreenPageLimit(2);
        pagerContainer.setUserInputEnabled(false);
        pagerContainer.setAdapter(mainFragmentAdapter);
        pagerContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    protected boolean supportSwipeBack() {
        return false;
    }

    @Override
    public void onBackPressedSupport() {
        if (ExitUtil.exit()) {
            System.exit(0);
        } else {
            toast(R.string.exitAgain);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                pagerContainer.setCurrentItem(0, false);
                break;
            case R.id.item_system:
                pagerContainer.setCurrentItem(1, false);
                break;
            case R.id.item_project:
                pagerContainer.setCurrentItem(2, false);
                break;
            case R.id.item_public:
                pagerContainer.setCurrentItem(3, false);
                break;
            case R.id.item_setting:
                pagerContainer.setCurrentItem(4, false);
                break;
        }
        return true;
    }
}
