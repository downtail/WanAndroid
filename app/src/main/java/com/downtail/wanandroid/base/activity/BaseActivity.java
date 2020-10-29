package com.downtail.wanandroid.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.utils.AppUtil;
import com.downtail.wanandroid.widget.StatePlus;
import com.downtail.wanandroid.widget.plus.StatusBarPlus;
import com.trello.rxlifecycle3.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends PermissionActivity<T> {

    private Unbinder unbinder;
    protected Activity mActivity;
    protected StatePlus statePlus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在绑定布局之前
        initBeforeBindLayout();
        //引用布局
        View rootView = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        if (supportStateController()) {
            rootView.setTag("ContentView");
            statePlus = new StatePlus();
            rootView = statePlus.init(rootView);
        }
        setContentView(rootView);
        //引用
        mActivity = this;
        //Activity管理类
        AppUtil.getInstance().push(this);
        //注册EventBus
        if (supportEventBus()) {
            registerEventBus();
        }
        //设置屏幕方向
        setScreenOrientation();
        //适配状态栏
        initStatusBar();
        //是否支持侧滑退出
        setSwipeBackEnable(supportSwipeBack());
        //ButterKnife
        unbinder = ButterKnife.bind(this);
        //初始化操作
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (supportEventBus()) {
            unregisterEventBus();
        }
        AppUtil.getInstance().pop(mActivity);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initEvents();

    protected void initBeforeBindLayout() {

    }

    protected boolean supportEventBus() {
        return false;
    }

    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void setScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
    }

    protected void initStatusBar() {
        StatusBarPlus.setColor(this, getResources().getColor(R.color.color_ffffff));
        StatusBarPlus.setStatusBarMode(this, true);
    }

    protected boolean supportStateController() {
        return true;
    }

    protected boolean supportSwipeBack() {
        return true;
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
    public void toast(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void toast(int resId) {
        ToastUtils.showShort(resId);
    }

    @Override
    public void jumpToLogin() {
        Navigator.openLogin(mActivity);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, ev)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v);   //收起键盘
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v instanceof EditText) {  //判断得到的焦点控件是否EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);//得到输入框在屏幕中上下左右的位置
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    protected void hideKeyboard(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null) {
            im.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
