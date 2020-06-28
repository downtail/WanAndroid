package com.downtail.wanandroid.presenter.project;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.project.ProjectContract;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.ui.project.entity.CategoryResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    @Inject
    public ProjectPresenter() {
    }

    @Override
    public void getProjectCategoryData() {
        dataManager.getProjectCategoryData()
                .compose(mView.bindToLife())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<List<CategoryResponse>>>(mView) {
                    @Override
                    public void onSuccess(BaseResponse<List<CategoryResponse>> data) {
                        mView.loadProjectCategoryData(data.getData());
                    }
                });
    }
}
