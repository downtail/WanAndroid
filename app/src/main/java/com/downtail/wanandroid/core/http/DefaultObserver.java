package com.downtail.wanandroid.core.http;


import com.downtail.wanandroid.base.mvp.BaseContract;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class DefaultObserver<T> implements Observer<T> {

    private BaseContract.BaseView mView;

    public DefaultObserver() {
    }

    public DefaultObserver(BaseContract.BaseView mView) {
        this.mView = mView;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mView != null) {
            mView.showLoading();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T data);

    @Override
    public void onError(Throwable e) {
        if (mView != null) {
            mView.showContent();
            ExceptionHandler.handleException(mView, e);
        }
    }

    @Override
    public void onComplete() {
        if (mView != null) {
            mView.showContent();
        }
    }
}
