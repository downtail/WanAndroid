package com.downtail.wanandroid.presenter.service;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.service.ServiceContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.ui.service.ServiceResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServicePresenter extends BasePresenter<ServiceContract.View> implements ServiceContract.Presenter {

    @Inject
    public ServicePresenter() {
    }

    @Override
    public void getServiceColumnData() {
        dataManager.getServiceColumnData()
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<ServiceResponse>>(mView) {
                    @Override
                    public void onSuccess(List<ServiceResponse> data) {
                        mView.loadProviderData(data);
                    }
                });
    }
}
