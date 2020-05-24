package com.downtail.wanandroid.presenter.project;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.project.ProjectContract;

import javax.inject.Inject;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    @Inject
    public ProjectPresenter() {
    }
}
