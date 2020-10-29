package com.downtail.wanandroid.ui.project.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.project.ArticleContract;
import com.downtail.wanandroid.presenter.project.ArticlePresenter;
import com.downtail.wanandroid.ui.project.adapter.ArticleAdapter;
import com.downtail.wanandroid.entity.response.ArticleMultipleEntity;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.response.Paging;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View,
        OnRefreshLoadMoreListener, OnItemClickListener, OnItemChildClickListener {

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;

    private static final String tag = "parentId";
    private int parentId;
    private int page = 1;
    private ArticleAdapter articleAdapter;

    public static ArticleFragment getInstance(int parentId) {
        Bundle args = new Bundle();
        args.putInt(tag, parentId);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    protected void initEvents() {
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        articleAdapter = new ArticleAdapter();
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnItemChildClickListener(this);
        articleAdapter.addChildClickViewIds(R.id.ivCollect);
        rvArticle.setLayoutManager(new LinearLayoutManager(_mActivity));
        rvArticle.setAdapter(articleAdapter);

        Bundle args = getArguments();
        if (args != null) {
            parentId = args.getInt(tag);
        }

        onReload();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {
        mPresenter.getProjectArticleDataByType(1, parentId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore(1000);
        mPresenter.getProjectArticleDataByType(page + 1, parentId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh(1000);
        mPresenter.getProjectArticleDataByType(1, parentId);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        int itemViewType = articleAdapter.getItemViewType(position);
        if (itemViewType == ArticleMultipleEntity.TEXT || itemViewType == ArticleMultipleEntity.IMAGE) {
            if (item != null) {
                ArticleResponse article = item.getArticle();
                if (article != null) {
                    Navigator.openBrowser(_mActivity, article.getLink(), article.getId(), article.isCollect());
                }
            }
        }
    }

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        switch (view.getId()) {
            case R.id.ivCollect:
                confirmOrCancelCollect(position);
                break;
        }
    }

    private void confirmOrCancelCollect(int position) {
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        if (item != null) {
            ArticleResponse article = item.getArticle();
            if (article != null) {
                if (article.isCollect()) {
                    mPresenter.cancelArticleCollect(position, article.getId());
                } else {
                    mPresenter.confirmArticleCollect(position, article.getId());
                }
            }
        }
    }

    @Override
    public void loadProjectArticleData(Paging<ArticleMultipleEntity> pagingData) {
        List<ArticleMultipleEntity> datas = pagingData.getDatas();
        if (datas != null && datas.size() > 0) {
            page = pagingData.getCurPage();
            if (page == 1) {
                articleAdapter.setNewInstance(datas);
            } else {
                articleAdapter.addData(datas);
            }
        }
        smartRefreshLayout.setNoMoreData(pagingData.isOver());
    }

    @Override
    public void loadArticleCollectState(int position, boolean isCollect) {
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        if (item != null) {
            ArticleResponse article = item.getArticle();
            if (article != null) {
                article.setCollect(isCollect);
                articleAdapter.notifyItemChanged(position);
                if (isCollect) {
                    toast(R.string.confirmCollectSuccess);
                } else {
                    toast(R.string.cancelCollectSuccess);
                }
            }
        }
    }
}
