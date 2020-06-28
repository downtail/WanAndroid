package com.downtail.wanandroid.ui.mine.activity;

import android.view.View;
import android.widget.TextView;

import com.downtail.plus.decorations.FloaterItemDecoration;
import com.downtail.plus.decorations.FloaterView;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.mine.RecordContract;
import com.downtail.wanandroid.presenter.mine.RecordPresenter;
import com.downtail.wanandroid.ui.mine.adapter.RecordMultipleAdapter;
import com.downtail.wanandroid.ui.mine.entity.RankResponse;
import com.downtail.wanandroid.ui.mine.entity.RecordMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.Paging;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class RecordActivity extends BaseActivity<RecordPresenter> implements RecordContract.View,
        OnRefreshLoadMoreListener {

    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rvRecord)
    RecyclerView rvRecord;

    private int page;
    private RecordMultipleAdapter recordMultipleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initEvents() {
        tvAction.setText(R.string.item_setting);

        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        rvRecord.setLayoutManager(new LinearLayoutManager(mActivity));
        recordMultipleAdapter = new RecordMultipleAdapter();
        rvRecord.setAdapter(recordMultipleAdapter);

        FloaterView floaterView = FloaterView.init(rvRecord)
                .addItemType(RecordMultipleEntity.RANK, R.layout.item_rank)
                .setOnBindViewListener(new FloaterView.OnBindViewListener() {
                    @Override
                    public void onBind(View view, int position) {
                        RecordMultipleEntity item = recordMultipleAdapter.getItem(position);
                        if (item != null) {
                            RankResponse rank = item.getRank();
                            if (rank != null) {
                                view.<TextView>findViewById(R.id.tvRank).setText(String.valueOf(rank.getRank()));
                                view.<TextView>findViewById(R.id.tvLevel).setText(String.valueOf(rank.getLevel()));
                                view.<TextView>findViewById(R.id.tvNick).setText(rank.getUsername());
                                view.<TextView>findViewById(R.id.tvCoin).setText(String.valueOf(rank.getCoinCount()));
                            }
                        }
                    }
                });
        FloaterItemDecoration floaterItemDecoration = new FloaterItemDecoration(recordMultipleAdapter, floaterView);
        rvRecord.addItemDecoration(floaterItemDecoration);

        page = 1;
        mPresenter.getRankData(1);
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore(1000);
        mPresenter.getRecordData(page + 1);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh(1000);
        mPresenter.getRankData(1);
    }

    @Override
    public void loadRecordData(Paging<RecordMultipleEntity> pagingData) {
        List<RecordMultipleEntity> datas = pagingData.getDatas();
        if (datas != null && datas.size() > 0) {
            page = pagingData.getCurPage();
            if (page == 1) {
                recordMultipleAdapter.setNewInstance(datas);
            } else {
                recordMultipleAdapter.addData(datas);
            }
            smartRefreshLayout.setNoMoreData(pagingData.isOver());
        }
    }
}
