package com.downtail.wanandroid.contract.mine;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.local.RecordMultipleEntity;
import com.downtail.wanandroid.entity.local.Paging;

public interface RecordContract {

    interface View extends BaseContract.BaseView {

        void loadRecordData(Paging<RecordMultipleEntity> pagingData);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getRankData(int page);

        void getRecordData(int page);
    }
}
