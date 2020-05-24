package com.downtail.wanandroid.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.widget.StateView;
import com.downtail.wanandroid.widget.plus.StatusBarPlus;
import com.trello.rxlifecycle3.LifecycleTransformer;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends DaggerActivity<T> implements View.OnClickListener {

    private Unbinder unbinder;
    protected Activity mActivity;
    protected StateView stateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引用布局
        setContentView(getLayoutId());
        //引用
        mActivity = this;
        //设置屏幕方向
        setScreenOrientation();
        //适配状态栏
        initStatusBar();
        //页面状态控制
        if (supportStateController()) {
            initStateView();
        }
        //是否支持侧滑退出
        setSwipeBackEnable(supportSwipeBack());
        //butterknife
        unbinder = ButterKnife.bind(this);
        //初始化操作
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stateView = null;
        unbinder.unbind();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initEvents();

    protected void setScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
    }

    protected void initStatusBar() {
        StatusBarPlus.setColor(this, getResources().getColor(R.color.colorPrimaryDark));
        StatusBarPlus.setStatusBarMode(this, false);
    }

    protected boolean supportStateController() {
        return true;
    }

    protected void initStateView() {
        stateView = StateView.init(mActivity)
                .setOnStateControllerListener(new StateView.OnStateControllerListener() {
                    @Override
                    public void onStateController(View itemView, int itemType) {
                        if (itemType == StateView.EMPTY) {
                            itemView.findViewById(R.id.layout_empty).setOnClickListener(BaseActivity.this);
                        } else if (itemType == StateView.ERROR) {
                            itemView.findViewById(R.id.layout_error).setOnClickListener(BaseActivity.this);
                        } else if (itemType == StateView.LOAD) {
                            itemView.findViewById(R.id.layout_load).setOnClickListener(BaseActivity.this);
                        }
                    }
                });
    }

    protected boolean supportSwipeBack() {
        return true;
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

    }

    @Override
    public void jumpToLogin() {

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, ev)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);//得到输入框在屏幕中上下左右的位置
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    protected void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
