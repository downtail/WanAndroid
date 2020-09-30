package com.downtail.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.di.component.DaggerFragmentComponent;
import com.downtail.wanandroid.di.component.FragmentComponent;
import com.downtail.wanandroid.di.module.FragmentModule;
import com.trello.rxlifecycle3.LifecycleTransformer;

import javax.inject.Inject;

public abstract class DaggerFragment<T extends BaseContract.BasePresenter> extends SwipeBackFragment implements BaseContract.BaseView {

    @Inject
    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化dagger
        initFragmentComponent();
        //注入
        initInjector();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //关联presenter
        attachView();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    protected abstract void initInjector();

    @SuppressWarnings("unchecked")
    protected void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void toast(int resId) {

    }

    @Override
    public void toast(String message) {

    }

    @Override
    public void jumpToLogin() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return null;
    }
}
