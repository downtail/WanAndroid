package com.downtail.wanandroid.ui.main;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.main.MainContract;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.UserEntity;
import com.downtail.wanandroid.presenter.main.MainPresenter;
import com.downtail.wanandroid.utils.AppUtil;
import com.downtail.wanandroid.utils.ExitUtil;
import com.downtail.wanandroid.widget.plus.StatusBarPlus;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.contentView)
    View contentView;
    @BindView(R.id.navigationView)
    BottomNavigationView navigationView;
    @BindView(R.id.vpContainer)
    ViewPager2 pagerContainer;
    @BindView(R.id.tvNickname)
    TextView tvNickname;

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

        navigationView.setOnNavigationItemSelectedListener(this);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(this);
        pagerContainer.setOffscreenPageLimit(2);
        pagerContainer.setUserInputEnabled(true);
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
    protected void onResume() {
        super.onResume();
        if (mPresenter.getUserLoginStatus()) {
            mPresenter.getUserEntity();
        }
    }

    @Override
    protected void initStatusBar() {
        StatusBarPlus.setTransparent(this);
        StatusBarPlus.setStatusBarMode(this, true);
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
        }
        return true;
    }

    @OnClick({R.id.ivClient, R.id.layoutRank, R.id.layoutSetting, R.id.layoutCollect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClient:
                whenAvatarClick();
                break;
            case R.id.layoutRank:
                Navigator.openRank(mActivity);
                break;
            case R.id.layoutSetting:
                Navigator.openSetting(mActivity);
                break;
            case R.id.layoutCollect:
                Navigator.openCollect(mActivity);
                break;
        }
    }

    private void whenAvatarClick() {
        if (mPresenter.getUserLoginStatus()) {

        } else {
            jumpToLogin();
        }
    }

    @Override
    public void loadRankData(RankResponse data) {

    }

    @Override
    public void loadUserEntity(UserEntity entity) {
        if (entity != null) {
            tvNickname.setText(entity.getNickname());
        }
    }
}
