package com.downtail.wanandroid.ui;

import android.view.View;
import android.widget.TextView;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.widget.plus.StatusBarPlus;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tvJump)
    TextView tvJump;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
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

    @Override
    protected void initStatusBar() {
        StatusBarPlus.setTransparent(this);
        StatusBarPlus.setStatusBarMode(this, false);
    }

    @Override
    public void onBackPressedSupport() {

    }

    @Override
    protected boolean supportSwipeBack() {
        return false;
    }

    @OnClick({R.id.tvJump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvJump:
                Navigator.openMain(mActivity);
                finish();
                break;
        }
    }
}
