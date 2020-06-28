package com.downtail.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.widget.StateView;
import com.trello.rxlifecycle3.LifecycleTransformer;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends DaggerFragment<T> implements View.OnClickListener {

    private Unbinder unbinder;
    protected StateView stateView;

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
        //页面状态控制
        if (supportStateController()) {
            view = initStateView(view);
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

    protected View initStateView(View view) {
        view = StateView.initWithFragment(view);
        stateView = view.findViewWithTag("StateView");
        if (stateView != null) {
            stateView.setOnStateControllerListener(new StateView.OnStateControllerListener() {
                @Override
                public void onStateController(View itemView, int itemType) {
                    if (itemType == StateView.EMPTY) {
                        itemView.findViewById(R.id.layout_empty).setOnClickListener(BaseFragment.this);
                    } else if (itemType == StateView.ERROR) {
                        itemView.findViewById(R.id.layout_error).setOnClickListener(BaseFragment.this);
                    } else if (itemType == StateView.LOAD) {
                        itemView.findViewById(R.id.layout_load).setOnClickListener(BaseFragment.this);
                    }
                }
            });
        }
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
    public void showEmpty() {
        if (stateView != null) {
            stateView.setState(StateView.EMPTY);
        }
    }

    @Override
    public void showLoading() {
        if (stateView != null) {
            stateView.setState(StateView.LOAD);
        }
    }

    @Override
    public void showError() {
        if (stateView != null) {
            stateView.setState(StateView.ERROR);
        }
    }

    @Override
    public void showContent() {
        if (stateView != null) {
            stateView.setState(StateView.CONTENT);
        }
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
    public void jumpToLogin() {
        Navigator.openLogin(_mActivity);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_empty:
                onReload();
                break;
            case R.id.layout_error:
                onReload();
                break;
            case R.id.layout_load:
                showContent();
                break;
        }
    }
}
