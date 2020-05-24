package com.downtail.wanandroid.contract.home;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.model.BannerResponse;

import java.util.List;

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void loadBannerData(List<BannerResponse> data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBannerData();
    }
}
