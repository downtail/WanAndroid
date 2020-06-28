package com.downtail.wanandroid.base.mvp;



import com.downtail.wanandroid.core.DataManager;

import javax.inject.Inject;

public class BasePresenter<V extends BaseContract.BaseView> implements BaseContract.BasePresenter<V> {

    @Inject
    protected DataManager dataManager;
    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
