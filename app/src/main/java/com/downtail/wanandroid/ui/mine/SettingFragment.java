package com.downtail.wanandroid.ui.mine;

import android.os.Bundle;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.mine.SettingContract;
import com.downtail.wanandroid.presenter.mine.SettingPresenter;

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View {

    public static SettingFragment getInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
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
