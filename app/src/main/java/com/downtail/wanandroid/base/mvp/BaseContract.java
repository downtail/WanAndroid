package com.downtail.wanandroid.base.mvp;

import com.trello.rxlifecycle3.LifecycleTransformer;

public interface BaseContract {

    interface BaseView {

        void showEmpty();

        void showLoading();

        void showError();

        void showContent();

        void onReload();

        void toast(String message);

        void toast(int resId);

        void jumpToLogin();

        <T> LifecycleTransformer<T> bindToLife();
    }

    interface BasePresenter<V extends BaseView> {

        void attachView(V view);

        void detachView();
    }
}
