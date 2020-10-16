package com.downtail.wanandroid.contract.service;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.entity.db.Author;

import java.util.List;

public interface ServiceContract {

    interface View extends BaseContract.BaseView {

        void loadAuthorData(List<Author> data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getServiceColumnData();

        void saveAuthorList(List<Author> list);
    }
}
