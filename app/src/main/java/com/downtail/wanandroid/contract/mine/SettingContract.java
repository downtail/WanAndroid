package com.downtail.wanandroid.contract.mine;

import com.downtail.wanandroid.base.mvp.BaseContract;

public interface SettingContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        boolean getUserLoginStatus();
    }
}
