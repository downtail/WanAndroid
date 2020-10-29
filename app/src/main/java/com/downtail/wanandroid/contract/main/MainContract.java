package com.downtail.wanandroid.contract.main;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.UserEntity;

public interface MainContract {

    interface View extends BaseContract.BaseView {

        void loadRankData(RankResponse data);

        void loadUserEntity(UserEntity entity);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        boolean getUserLoginStatus();

        void getUserEntity();

        void getRankData();
    }
}
