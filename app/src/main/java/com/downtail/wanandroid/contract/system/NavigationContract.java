package com.downtail.wanandroid.contract.system;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.ui.system.entity.NavigationResponse;

import java.util.List;

public interface NavigationContract {

    interface View extends BaseContract.BaseView {

        void loadNavigationData(List<NavigationResponse> data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getNavigationData();
    }
}
