package com.downtail.wanandroid.ui.main;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.main.MainContract;
import com.downtail.wanandroid.presenter.main.MainPresenter;
import com.downtail.wanandroid.utils.AppUtil;
import com.downtail.wanandroid.utils.ExitUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.contentView)
    View contentView;
    @BindView(R.id.navigationView)
    BottomNavigationView navigationView;
    @BindView(R.id.vpContainer)
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
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                int offset = Float.valueOf(drawerView.getWidth() * slideOffset).intValue();
                contentView.setTranslationX(offset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        tvAction.setText(R.string.item_setting);

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
    protected void initStatusBar() {
        //super.initStatusBar();
    }

    @Override
    protected boolean supportSwipeBack() {
        return false;
    }

    @Override
    public void onBackPressedSupport() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (ExitUtil.enableExit()) {
            AppUtil.getInstance().exitApp();
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
