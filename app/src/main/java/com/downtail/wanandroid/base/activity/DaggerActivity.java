package com.downtail.wanandroid.base.activity;

import android.os.Bundle;

import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.di.component.ActivityComponent;
import com.downtail.wanandroid.di.component.DaggerActivityComponent;
import com.downtail.wanandroid.di.module.ActivityModule;
import com.trello.rxlifecycle3.LifecycleTransformer;

import javax.inject.Inject;

public abstract class DaggerActivity<T extends BaseContract.BasePresenter> extends SwipeBackActivity implements BaseContract.BaseView {

    @Inject
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化dagger
        initActivityComponent();
        //注入
        initInjector();
        //关联presenter
        attachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void initInjector();

    @SuppressWarnings("unchecked")
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void toast(String message) {

    }

    @Override
    public void toast(int resId) {

    }

    @Override
    public void jumpToLogin() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return null;
    }
}
