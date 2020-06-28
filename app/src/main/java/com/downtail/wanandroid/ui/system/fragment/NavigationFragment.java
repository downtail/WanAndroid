package com.downtail.wanandroid.ui.system.fragment;

import android.os.Bundle;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.system.NavigationContract;
import com.downtail.wanandroid.presenter.system.NavigationPresenter;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.system.adapter.NavigationAdapter;
import com.downtail.wanandroid.ui.system.entity.NavigationResponse;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.rvNavigation)
    RecyclerView rvNavigation;

    private NavigationAdapter navigationAdapter;

    public static NavigationFragment getInstance() {
        Bundle args = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initEvents() {
        rvNavigation.setLayoutManager(new LinearLayoutManager(_mActivity));
        navigationAdapter = new NavigationAdapter(R.layout.item_column);
        navigationAdapter.setOnNestedClickListener(new NavigationAdapter.OnNestedClickListener() {
            @Override
            public void onClick(ArticleResponse articleResponse, int position) {
                Navigator.openBrowser(_mActivity, articleResponse.getLink(), articleResponse.getId(), articleResponse.isCollect());
            }
        });
        rvNavigation.setAdapter(navigationAdapter);

        mPresenter.getNavigationData();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    public void loadNavigationData(List<NavigationResponse> data) {
        navigationAdapter.setNewInstance(data);
    }
}
