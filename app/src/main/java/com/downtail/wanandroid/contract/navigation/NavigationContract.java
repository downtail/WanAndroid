package com.downtail.wanandroid.contract.navigation;

import com.downtail.wanandroid.base.mvp.BaseContract;

public interface NavigationContract {

    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

    }
}
