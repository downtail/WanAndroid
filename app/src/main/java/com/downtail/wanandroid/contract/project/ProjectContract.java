package com.downtail.wanandroid.contract.project;

import com.downtail.wanandroid.base.mvp.BaseContract;
import com.downtail.wanandroid.ui.project.entity.CategoryResponse;

import java.util.List;

public interface ProjectContract {

    interface View extends BaseContract.BaseView {

        void loadProjectCategoryData(List<CategoryResponse> data);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getProjectCategoryData();
    }
}
