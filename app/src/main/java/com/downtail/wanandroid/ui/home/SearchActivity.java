package com.downtail.wanandroid.ui.home;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.home.SearchContract;
import com.downtail.wanandroid.entity.db.Word;
import com.downtail.wanandroid.entity.local.ArticleMultipleEntity;
import com.downtail.wanandroid.entity.local.Paging;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.response.HotEntity;
import com.downtail.wanandroid.presenter.home.SearchPresenter;
import com.downtail.wanandroid.ui.project.adapter.ArticleAdapter;
import com.downtail.wanandroid.widget.plus.StatusBarPlus;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivAction)
    ImageView ivAction;
    @BindView(R.id.ivClear)
    ImageView ivClear;
    @BindView(R.id.rvHot)
    RecyclerView rvHot;
    @BindView(R.id.rvCache)
    RecyclerView rvCache;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;

    private HotAdapter hotAdapter;
    private WordAdapter wordAdapter;
    private ArticleAdapter articleAdapter;
    private int mCurrentPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initEvents() {
        mCurrentPage = 0;
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rvHot.setLayoutManager(flexboxLayoutManager);
        hotAdapter = new HotAdapter(R.layout.item_hot_body);
        hotAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HotEntity item = hotAdapter.getItem(position);
                if (item != null) {
                    String keyword = item.getName();
                    if (!TextUtils.isEmpty(keyword)) {
                        etSearch.setText(keyword);
                        mPresenter.getArticleByKeyword(0, keyword);
                        mPresenter.saveRecentWord(keyword, System.currentTimeMillis());
                    }
                }
            }
        });
        rvHot.setAdapter(hotAdapter);

        wordAdapter = new WordAdapter(R.layout.item_hot_body);
        wordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Word item = wordAdapter.getItem(position);
                if (item != null) {
                    String keyword = item.getKeyword();
                    if (!TextUtils.isEmpty(keyword)) {
                        etSearch.setText(keyword);
                        mPresenter.getArticleByKeyword(0, keyword);
                        mPresenter.saveRecentWord(keyword, System.currentTimeMillis());
                    }
                }
            }
        });
        rvCache.setAdapter(wordAdapter);
        FlexboxLayoutManager flexLayoutManager;
        flexLayoutManager = new FlexboxLayoutManager(this);
        flexLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rvCache.setLayoutManager(flexLayoutManager);

        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        articleAdapter = new ArticleAdapter();
        articleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ArticleMultipleEntity item = articleAdapter.getItem(position);
                if (item != null) {
                    ArticleResponse article = item.getArticle();
                    if (article != null) {
                        Navigator.openBrowser(mActivity, article.getLink(), article.getId(), article.isCollect());
                    }
                }
            }
        });
        articleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.ivCollect:
                        confirmOrCancelCollect(position);
                        break;
                }
            }
        });
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        rvArticle.setAdapter(articleAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dealWithInput();
            }
        });

        onReload();
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onReload() {
        mPresenter.getHotKey();
        mPresenter.getRecentWord();
    }

    @Override
    protected void initStatusBar() {
        StatusBarPlus.setColor(this, Color.argb(127, 0, 0, 0));
    }

    @OnClick({R.id.ivAction, R.id.ivClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivAction:
                getArticleByKeyword();
                break;
            case R.id.ivClear:
                etSearch.setText("");
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

    private void getArticleByKeyword() {
        String keyword = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        mPresenter.getArticleByKeyword(0, keyword);
        mPresenter.saveRecentWord(keyword, System.currentTimeMillis());
    }

    private void dealWithInput() {
        String text = etSearch.getText().toString();
        if (TextUtils.isEmpty(text)) {
            ivClear.setVisibility(View.GONE);
            articleAdapter.setNewInstance(null);
            smartRefreshLayout.setVisibility(View.GONE);
        } else {
            ivClear.setVisibility(View.VISIBLE);
            etSearch.setSelection(text.length());
        }
        mCurrentPage = 0;
    }

    @Override
    public void loadHotKey(List<HotEntity> data) {
        hotAdapter.setNewInstance(data);
    }

    @Override
    public void loadArticleData(Paging<ArticleMultipleEntity> data) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        mCurrentPage = data.getCurPage();
        smartRefreshLayout.setVisibility(View.VISIBLE);
        List<ArticleMultipleEntity> list = data.getDatas();
        if (mCurrentPage == 0) {
            articleAdapter.setNewInstance(list);
        } else if (mCurrentPage > 0) {
            articleAdapter.addData(list);
        }
        if (list.isEmpty()) {
            smartRefreshLayout.setNoMoreData(true);
        }
    }

    @Override
    public void loadArticleCollectState(int position, boolean isCollect) {
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        if (item != null) {
            ArticleResponse article = item.getArticle();
            if (article != null) {
                article.setCollect(isCollect);
                articleAdapter.notifyItemRangeChanged(position, 1);
                if (isCollect) {
                    toast(R.string.confirmCollectSuccess);
                } else {
                    toast(R.string.cancelCollectSuccess);
                }
            }
        }
    }

    @Override
    public void getRecentWord(List<Word> data) {
        wordAdapter.setNewInstance(data);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        String text = etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            mPresenter.getArticleByKeyword(mCurrentPage + 1, text);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        String text = etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            mPresenter.getArticleByKeyword(0, text);
        }
    }
}
