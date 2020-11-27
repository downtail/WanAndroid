package com.downtail.wanandroid.contract.home;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.db.Word;
import com.downtail.wanandroid.entity.local.ArticleMultipleEntity;
import com.downtail.wanandroid.entity.local.Paging;
import com.downtail.wanandroid.entity.response.HotEntity;

import java.util.List;

public interface SearchContract {

    interface View extends BaseContract.BaseView {

        void loadHotKey(List<HotEntity> data);

        void loadArticleData(Paging<ArticleMultipleEntity> data);

        void loadArticleCollectState(int position, boolean isCollect);

        void getRecentWord(List<Word> data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getHotKey();

        void getArticleByKeyword(int page, String keyword);

        void confirmArticleCollect(int position, int id);

        void cancelArticleCollect(int position, int id);

        void saveRecentWord(String keyword, long createTime);

        void getRecentWord();
    }
}
