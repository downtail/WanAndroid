package com.downtail.wanandroid.ui.project.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.project.ProjectContract;
import com.downtail.wanandroid.presenter.project.ProjectPresenter;
import com.downtail.wanandroid.ui.project.adapter.ProjectColumnAdapter;
import com.downtail.wanandroid.ui.project.adapter.ProjectPagerAdapter;
import com.downtail.wanandroid.entity.response.CategoryResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.View,
        OnItemClickListener {

    @BindView(R.id.rvColumn)
    RecyclerView rvColumn;
    @BindView(R.id.pagerColumn)
    ViewPager2 pagerColumn;

    private ProjectColumnAdapter projectColumnAdapter;
    private ProjectPagerAdapter projectPagerAdapter;

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
        projectColumnAdapter = new ProjectColumnAdapter(R.layout.item_project_column);
        projectColumnAdapter.setOnItemClickListener(this);
        rvColumn.setAdapter(projectColumnAdapter);
        rvColumn.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.HORIZONTAL, false));

        projectPagerAdapter = new ProjectPagerAdapter(this);
        pagerColumn.setOffscreenPageLimit(3);
        pagerColumn.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                projectColumnAdapter.setIndex(position);
                RecyclerView.LayoutManager layoutManager = rvColumn.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    layoutManager.scrollToPosition(position);
                }
            }
        });
        pagerColumn.setAdapter(projectPagerAdapter);

        mPresenter.getProjectCategoryData();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    protected boolean supportStatusBar() {
        return true;
    }

    @Override
    public void loadProjectCategoryData(List<CategoryResponse> data) {
        projectColumnAdapter.setNewInstance(data);
        projectPagerAdapter.setData(data);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        projectColumnAdapter.setIndex(position);
        pagerColumn.setCurrentItem(position);
    }
}
