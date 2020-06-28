package com.downtail.wanandroid.contract.broser;

import com.downtail.wanandroid.base.mvp.BaseContract;

public interface BrowserContract {

    interface View extends BaseContract.BaseView {

        void loadArticleCollectState(int touchX, int touchY, boolean isCollect);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void confirmArticleCollect(int touchX, int touchY, int id);

        void cancelArticleCollect(int touchX, int touchY, int id);
    }
}
