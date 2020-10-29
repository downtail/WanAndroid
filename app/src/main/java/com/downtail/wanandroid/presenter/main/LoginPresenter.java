package com.downtail.wanandroid.presenter.main;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.main.LoginContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.entity.response.UserEntity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login(String username, String password) {
        dataManager.login(username, password)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserEntity>(mView) {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        dataManager.setLoginStatus(true);
                        mView.loginSuccess();
                        setUserEntity(userEntity);
                    }
                });
    }

    @Override
    public void register(String username, String password, String rePassword) {
        dataManager.register(username, password, rePassword)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserEntity>(mView) {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        dataManager.setLoginStatus(true);
                        mView.loginSuccess();
                        setUserEntity(userEntity);
                    }
                });
    }

    @Override
    public void setUserEntity(UserEntity entity) {
        dataManager.setUserEntity(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {

                    }
                });
    }
}
