package com.downtail.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.widget.StatePlus;
import com.trello.rxlifecycle3.LifecycleTransformer;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends PermissionFragment<T> {

    private Unbinder unbinder;
    protected StatePlus statePlus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这里调用super的原因是因为在父类中做了attachView()操作
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        //为fragment根布局设置tag
        view.setTag("ContentView");
        //如果有状态栏
        if (supportStatusBar()) {
            view = initStatusBar(view);
        }
        //是否支持状态控制
        if (supportStateController()) {
            statePlus = new StatePlus();
            view = statePlus.init(view);
        }
        //是否支持侧滑退出
        setSwipeBackEnable(supportSwipeBack());
        //初始化操作
        initEvents();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initEvents();

    protected View initStatusBar(View view) {
        return view;
    }

    protected boolean supportStatusBar() {
        return false;
    }

    protected boolean supportStateController() {
        return true;
    }

    protected boolean supportSwipeBack() {
        return false;
    }

    @Override
    public void toast(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void toast(int resId) {
        ToastUtils.showShort(resId);
    }

    @Override
    public void showEmpty() {
        if (statePlus != null) {
            statePlus.setState(StatePlus.EMPTY);
        }
    }

    @Override
    public void showLoading() {
        if (statePlus != null) {
            statePlus.setState(StatePlus.LOAD);
        }
    }

    @Override
    public void showError() {
        if (statePlus != null) {
            statePlus.setState(StatePlus.ERROR);
        }
    }

    @Override
    public void showContent() {
        if (statePlus != null) {
            statePlus.setState(StatePlus.DATA);
        }
    }

    @Override
    public void jumpToLogin() {
        Navigator.openLogin(_mActivity);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
