package com.downtail.wanandroid.presenter.system;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.system.SystemContract;

import javax.inject.Inject;

public class SystemPresenter extends BasePresenter<SystemContract.View> implements SystemContract.Presenter {

    @Inject
    public SystemPresenter() {
    }
}
