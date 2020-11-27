package com.downtail.wanandroid.contract.service;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.local.ArticleMultipleEntity;
import com.downtail.wanandroid.entity.local.Paging;

public interface ClientContract {

    interface View extends BaseContract.BaseView {

        void loadProjectArticleData(Paging<ArticleMultipleEntity> pagingData);

        void loadArticleCollectState(int position, boolean isCollect);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getClientArticleData(int id, int page);

        void getClientArticleByKeyword(int id, int page, String keyword);

        void confirmArticleCollect(int position, int id);

        void cancelArticleCollect(int position, int id);
    }
}
