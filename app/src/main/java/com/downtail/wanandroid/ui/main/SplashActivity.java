package com.downtail.wanandroid.ui.main;

import android.view.View;
import android.widget.TextView;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.widget.plus.StatusBarPlus;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends BaseActivity {

    @BindView(R.id.tvJump)
    TextView tvJump;

    private Disposable timer;

    @Override
    protected void initBeforeBindLayout() {
        setTheme(R.style.MainTheme);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEvents() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        timer = d;
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        tvJump.setText("跳过" + (3 - aLong) + "秒");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        jumpToMainPage();
                    }

                    @Override
                    public void onComplete() {
                        jumpToMainPage();
                    }
                });
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
    }

    @Override
    public void onBackPressedSupport() {

    }

    @Override
    protected boolean supportSwipeBack() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null && !timer.isDisposed()) {
            timer.dispose();
            timer = null;
        }
    }

    @OnClick({R.id.tvJump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvJump:
                jumpToMainPage();
                break;
        }
    }

    private void jumpToMainPage() {
        Navigator.openMain(mActivity);
        finish();
    }
}
