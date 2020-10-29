package com.downtail.wanandroid.contract.main;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.response.UserEntity;

public interface LoginContract {

    interface View extends BaseContract.BaseView {

        void loginSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void login(String username, String password);

        void register(String username, String password, String rePassword);

        void setUserEntity(UserEntity entity);
    }
}
