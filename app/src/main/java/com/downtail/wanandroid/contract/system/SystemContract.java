package com.downtail.wanandroid.contract.system;

import com.downtail.wanandroid.base.mvp.BaseContract;

public interface SystemContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

    }
}
