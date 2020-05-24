package com.downtail.wanandroid.core.http;



import com.downtail.wanandroid.base.mvp.BaseContract;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class DefaultObserver<T> implements Observer<T> {

    private BaseContract.BaseView mView;

    public DefaultObserver(BaseContract.BaseView mView) {
        this.mView = mView;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        if (t instanceof Error) {
            mView.toast(((Error) t).getErrMsg());
        }
    }

    public abstract void onSuccess(T data);

    @Override
    public void onError(Throwable e) {
        ExceptionHandler.handleException(mView, e);
    }

    @Override
    public void onComplete() {

    }
}
