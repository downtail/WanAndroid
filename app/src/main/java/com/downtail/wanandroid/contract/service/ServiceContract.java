package com.downtail.wanandroid.contract.service;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.ui.service.ServiceResponse;

import java.util.List;

public interface ServiceContract {

    interface View extends BaseContract.BaseView {

        void loadProviderData(List<ServiceResponse> data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getServiceColumnData();
    }
}
