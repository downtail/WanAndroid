package com.downtail.wanandroid.contract.mine;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.ui.mine.entity.RankResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;

public interface RankContract {

    interface View extends BaseContract.BaseView {

        void loadRankData(Paging<RankResponse> pagingData);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getRankListData(int page);
    }
}
