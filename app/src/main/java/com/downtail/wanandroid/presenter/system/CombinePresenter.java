package com.downtail.wanandroid.presenter.system;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.system.CombineContract;

import javax.inject.Inject;

public class CombinePresenter extends BasePresenter<CombineContract.View> implements CombineContract.Presenter {

    @Inject
    public CombinePresenter() {
    }
}
