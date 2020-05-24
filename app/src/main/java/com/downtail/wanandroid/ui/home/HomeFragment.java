package com.downtail.wanandroid.ui.home;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.home.HomeContract;
import com.downtail.wanandroid.model.BannerResponse;
import com.downtail.wanandroid.presenter.home.HomePresenter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, OnItemClickListener {

    @BindView(R.id.pagerBanner)
    ViewPager2 pagerBanner;

    private BannerAdapter bannerAdapter;

    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEvents() {
        bannerAdapter = new BannerAdapter(R.layout.item_banner);
        bannerAdapter.setOnItemClickListener(this);
        pagerBanner.setAdapter(bannerAdapter);
        mPresenter.getBannerData();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

    }

    @Override
    public void loadBannerData(List<BannerResponse> data) {
        bannerAdapter.setNewInstance(data);
    }
}
