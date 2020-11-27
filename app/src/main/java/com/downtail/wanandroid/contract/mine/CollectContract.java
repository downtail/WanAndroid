package com.downtail.wanandroid.contract.mine;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.local.ArticleMultipleEntity;
import com.downtail.wanandroid.entity.local.Paging;

import java.util.List;

public interface CollectContract {

    interface View extends BaseContract.BaseView {

        void loadCollectArticleData(Paging<ArticleMultipleEntity> pagingData);

        void loadCollectWebsiteData(List<ArticleMultipleEntity> data);

        void loadArticleCollectState(int position, boolean isCollect);

        void collectWebsiteSuccess(List<ArticleMultipleEntity> data);

        void modifyWebsiteSuccess(int position, ArticleMultipleEntity item);

        void cancelWebsiteSuccess(int position);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getCollectArticleData(int page);

        void getCollectWebsiteData();

        void collectWebsite(String name, String link);

        void modifyWebsite(int position, int id, String name, String link);

        void confirmArticleCollect(int position, int id);

        void cancelArticleCollect(int position, int id);

        void cancelWebsiteCollect(int position, int id);
    }
}
