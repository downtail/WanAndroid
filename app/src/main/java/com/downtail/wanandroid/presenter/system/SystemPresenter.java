package com.downtail.wanandroid.presenter.system;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.system.SystemContract;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.ui.system.entity.SystemResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SystemPresenter extends BasePresenter<SystemContract.View> implements SystemContract.Presenter {

    @Inject
    public SystemPresenter() {
    }

    @Override
    public void getColumnData() {
        dataManager.getSystemData()
                .compose(mView.bindToLife())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<List<SystemResponse>>>(mView) {
                    @Override
                    public void onSuccess(BaseResponse<List<SystemResponse>> data) {
                        mView.loadSystemColumnData(data.getData());
                    }
                });
    }
}
