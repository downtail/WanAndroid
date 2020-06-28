package com.downtail.wanandroid.contract.mine;

import com.downtail.wanandroid.base.mvp.BaseContract;

public interface PreferenceContract {

    interface View extends BaseContract.BaseView{

        void loadUserLoginStatus(boolean isLogin);

        void logoutSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void getUserLoginStatus();

        void logout();
    }
}
