package com.downtail.wanandroid.ui.mine.activity;

import android.view.View;
import android.widget.TextView;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.mine.PreferenceContract;
import com.downtail.wanandroid.presenter.mine.PreferencePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class PreferenceActivity extends BaseActivity<PreferencePresenter> implements PreferenceContract.View {

    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.layoutLogin)
    View layoutLogin;
    @BindView(R.id.layoutLogout)
    View layoutLogout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_preference;
    }

    @Override
    protected void initEvents() {
        tvAction.setText(R.string.mine_setting);
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
        mPresenter.getUserLoginStatus();
    }

    @OnClick({R.id.layoutBack, R.id.layoutLogin, R.id.layoutLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutBack:
                finish();
                break;
            case R.id.layoutLogin:
                jumpToLogin();
                break;
            case R.id.layoutLogout:
                mPresenter.logout();
                break;
        }
    }

    @Override
    public void loadUserLoginStatus(boolean isLogin) {
        layoutLogin.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        layoutLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

    @Override
    public void logoutSuccess() {
        toast(getResources().getString(R.string.userLogoutSuccess));
        mPresenter.getUserLoginStatus();
    }
}
