package com.downtail.wanandroid.presenter.main;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.main.MainContract;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter() {
    }
}
