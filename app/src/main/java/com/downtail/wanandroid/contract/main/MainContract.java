package com.downtail.wanandroid.contract.main;

import com.downtail.wanandroid.base.mvp.BaseContract;

public interface MainContract {

    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

    }
}
