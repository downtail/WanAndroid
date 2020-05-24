package com.downtail.wanandroid.contract.project;

import com.downtail.wanandroid.base.mvp.BaseContract;

public interface ProjectContract {

    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

    }
}
