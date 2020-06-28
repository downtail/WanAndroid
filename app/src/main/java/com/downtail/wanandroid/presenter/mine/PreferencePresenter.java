package com.downtail.wanandroid.presenter.mine;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.mine.PreferenceContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PreferencePresenter extends BasePresenter<PreferenceContract.View> implements PreferenceContract.Presenter {

    @Inject
    public PreferencePresenter() {
    }

    @Override
    public void getUserLoginStatus() {
        mView.loadUserLoginStatus(dataManager.getLoginStatus());
    }

    @Override
    public void logout() {
        dataManager.logout()
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        dataManager.setLoginStatus(false);
                        mView.logoutSuccess();
                    }
                });
    }
}
