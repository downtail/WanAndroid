package com.downtail.wanandroid.contract.system;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.ui.system.entity.SystemResponse;

import java.util.List;

public interface SystemContract {

    interface View extends BaseContract.BaseView {

        void loadSystemColumnData(List<SystemResponse> data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getColumnData();
    }
}
