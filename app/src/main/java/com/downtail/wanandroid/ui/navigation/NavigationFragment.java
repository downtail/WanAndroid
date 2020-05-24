package com.downtail.wanandroid.ui.navigation;

import android.os.Bundle;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.navigation.NavigationContract;
import com.downtail.wanandroid.presenter.navigation.NavigationPresenter;

public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.View {

    public static NavigationFragment getInstance() {
        Bundle args = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onReload() {

    }
}
