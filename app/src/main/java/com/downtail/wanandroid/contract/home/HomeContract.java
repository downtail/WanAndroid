package com.downtail.wanandroid.contract.home;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.response.BannerResponse;
import com.downtail.wanandroid.entity.response.ArticleMultipleEntity;
import com.downtail.wanandroid.entity.response.Paging;

import java.util.List;

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void loadBannerData(List<BannerResponse> data);

        void loadAdvancedArticleData(Paging<ArticleMultipleEntity> pagingData);

        void loadArticleCollectState(int position, boolean isCollect);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBannerData();

        void getAdvancedArticleData(int page);

        void getHomeArticleData(int page);

        void confirmArticleCollect(int position, int id);

        void cancelArticleCollect(int position, int id);
    }
}
