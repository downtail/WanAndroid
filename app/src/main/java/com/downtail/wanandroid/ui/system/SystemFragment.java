package com.downtail.wanandroid.ui.system;

import android.os.Bundle;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.system.SystemContract;
import com.downtail.wanandroid.presenter.system.SystemPresenter;

public class SystemFragment extends BaseFragment<SystemPresenter> implements SystemContract.View {

    public static SystemFragment getInstance() {
        Bundle args = new Bundle();
        SystemFragment fragment = new SystemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_system;
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
