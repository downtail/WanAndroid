package com.downtail.wanandroid.ui.project;

import android.os.Bundle;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.project.ProjectContract;
import com.downtail.wanandroid.presenter.project.ProjectPresenter;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.View {

    public static ProjectFragment getInstance() {
        Bundle args = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onReload() {

    }
}
