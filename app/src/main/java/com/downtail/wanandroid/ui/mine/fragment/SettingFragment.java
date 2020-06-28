package com.downtail.wanandroid.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.mine.SettingContract;
import com.downtail.wanandroid.presenter.mine.SettingPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.ivClient)
    CircleImageView ivClient;

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
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @OnClick({R.id.ivClient, R.id.layoutRank, R.id.layoutSetting, R.id.layoutCollect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClient:
                whenAvatarClick();
                break;
            case R.id.layoutRank:
                Navigator.openRank(_mActivity);
                break;
            case R.id.layoutSetting:
                Navigator.openSetting(_mActivity);
                break;
            case R.id.layoutCollect:
                Navigator.openCollect(_mActivity);
                break;
        }
    }

    private void whenAvatarClick() {
        if (mPresenter.getUserLoginStatus()) {

        } else {
            jumpToLogin();
        }
    }
}
