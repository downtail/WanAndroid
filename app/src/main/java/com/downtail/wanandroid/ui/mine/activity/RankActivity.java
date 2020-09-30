package com.downtail.wanandroid.ui.mine.activity;

import android.view.View;
import android.widget.TextView;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.mine.RankContract;
import com.downtail.wanandroid.presenter.mine.RankPresenter;
import com.downtail.wanandroid.ui.mine.adapter.RankAdapter;
import com.downtail.wanandroid.ui.mine.entity.RankResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class RankActivity extends BaseActivity<RankPresenter> implements RankContract.View,
        OnRefreshLoadMoreListener {

    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.tvPlus)
    TextView tvPlus;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rvRank)
    RecyclerView rvRank;

    private int page;
    private RankAdapter rankAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void initEvents() {
        tvAction.setText(R.string.mine_rank);
        tvPlus.setText(R.string.item_setting);

        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        rvRank.setLayoutManager(new LinearLayoutManager(mActivity));
        rankAdapter = new RankAdapter(R.layout.item_rank);
        rvRank.setAdapter(rankAdapter);

        page = 1;
        mPresenter.getRankListData(1);
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore(1000);
        mPresenter.getRankListData(page + 1);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh(1000);
        mPresenter.getRankListData(1);
    }

    @Override
    public void loadRankData(Paging<RankResponse> pagingData) {
        List<RankResponse> datas = pagingData.getDatas();
        if (datas != null && datas.size() > 0) {
            page = pagingData.getCurPage();
            if (page == 1) {
                rankAdapter.setNewInstance(datas);
            } else {
                rankAdapter.addData(datas);
            }
            smartRefreshLayout.setNoMoreData(pagingData.isOver());
        }
    }

    @OnClick({R.id.tvPlus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvPlus:
                Navigator.openRecord(mActivity);
                break;
        }
    }
}
