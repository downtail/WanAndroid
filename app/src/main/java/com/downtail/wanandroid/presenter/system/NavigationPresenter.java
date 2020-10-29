package com.downtail.wanandroid.presenter.system;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.system.NavigationContract;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.entity.response.NavigationResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    @Inject
    public NavigationPresenter() {
    }

    @Override
    public void getNavigationData() {
        dataManager.getNavigationData()
                .compose(mView.bindToLife())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<List<NavigationResponse>>>(mView) {
                    @Override
                    public void onSuccess(BaseResponse<List<NavigationResponse>> data) {
                        mView.loadNavigationData(data.getData());
                    }
                });
    }
}
