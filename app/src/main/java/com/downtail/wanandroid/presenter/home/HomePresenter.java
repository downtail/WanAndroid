package com.downtail.wanandroid.presenter.home;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.home.HomeContract;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.model.BannerResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    @Inject
    public HomePresenter() {
    }

    @Override
    public void getBannerData() {
        dataManager.getBannerData()
                .compose(mView.bindToLife())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<List<BannerResponse>>>(mView) {
                    @Override
                    public void onSuccess(BaseResponse<List<BannerResponse>> data) {
                        mView.loadBannerData(data.getData());
                    }
                });
    }
}
