package com.downtail.wanandroid.presenter.main;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.main.MainContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.UserEntity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter() {
    }

    @Override
    public boolean getUserLoginStatus() {
        return dataManager.getLoginStatus();
    }


    @Override
    public void getUserEntity() {
        dataManager.getUserEntity()
                .compose(mView.bindToLife())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity data) {
                        if (mView != null) {
                            mView.loadUserEntity(data);
                        }
                    }
                });
    }

    @Override
    public void getRankData() {
        dataManager.getRankData()
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<RankResponse>() {
                    @Override
                    public void onSuccess(RankResponse data) {
                        if (mView != null) {
                            mView.loadRankData(data);
                        }
                    }
                });
    }
}
