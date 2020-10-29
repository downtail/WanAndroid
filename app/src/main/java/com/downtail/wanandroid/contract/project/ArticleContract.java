package com.downtail.wanandroid.contract.project;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.response.ArticleMultipleEntity;
import com.downtail.wanandroid.entity.response.Paging;

public interface ArticleContract {

    interface View extends BaseContract.BaseView {

        void loadProjectArticleData(Paging<ArticleMultipleEntity> pagingData);

        void loadArticleCollectState(int position, boolean isCollect);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getProjectArticleDataByType(int page, int cid);

        void confirmArticleCollect(int position, int id);

        void cancelArticleCollect(int position, int id);
    }
}
