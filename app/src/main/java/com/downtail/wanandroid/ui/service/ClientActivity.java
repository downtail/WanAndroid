package com.downtail.wanandroid.ui.service;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.service.ClientContract;
import com.downtail.wanandroid.presenter.service.ClientPresenter;
import com.downtail.wanandroid.ui.project.adapter.ArticleAdapter;
import com.downtail.wanandroid.ui.project.entity.ArticleMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ClientActivity extends BaseActivity<ClientPresenter> implements ClientContract.View,
        OnRefreshLoadMoreListener, OnItemClickListener, OnItemChildClickListener {

    public static final String ID = "id";
    public static final String NAME = "name";

    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rvClient)
    RecyclerView rvClient;

    private int id;
    private int page;
    private ArticleAdapter articleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_client;
    }

    @Override
    protected void initEvents() {
        Intent intent = getIntent();
        String name = intent.getStringExtra(NAME);
        if (!TextUtils.isEmpty(name)) {
            tvAction.setText(name);
        }
        id = intent.getIntExtra(ID, 0);

        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        articleAdapter = new ArticleAdapter();
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnItemChildClickListener(this);
        articleAdapter.addChildClickViewIds(R.id.ivCollect);
        rvClient.setLayoutManager(new LinearLayoutManager(mActivity));
        rvClient.setAdapter(articleAdapter);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(etSearch);
                    dealWithInput(true);
                    return true;
                }
                return false;
            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable endDrawable = etSearch.getCompoundDrawables()[2];
                if (endDrawable == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() > etSearch.getWidth() - etSearch.getPaddingEnd() - endDrawable.getIntrinsicWidth()) {
                    dealWithInput(true);
                }
                return false;
            }
        });

        page = 1;
        etSearch.setText("");
        dealWithInput(true);
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore(1000);
        dealWithInput(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh(1000);
        dealWithInput(true);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        int itemViewType = articleAdapter.getItemViewType(position);
        if (itemViewType == ArticleMultipleEntity.TEXT || itemViewType == ArticleMultipleEntity.IMAGE) {
            if (item != null) {
                ArticleResponse article = item.getArticle();
                if (article != null) {
                    Navigator.openBrowser(mActivity, article.getLink(), article.getId(), article.isCollect());
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

    private void dealWithInput(boolean isRefresh) {
        String content = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            if (isRefresh) {
                mPresenter.getClientArticleData(id, 1);
            } else {
                mPresenter.getClientArticleData(id, page + 1);
            }
        } else {
            if (isRefresh) {
                mPresenter.getClientArticleByKeyword(id, 1, content);
            } else {
                mPresenter.getClientArticleByKeyword(id, page + 1, content);
            }
        }
    }

    @Override
    public void loadProjectArticleData(Paging<ArticleMultipleEntity> pagingData) {
        List<ArticleMultipleEntity> datas = pagingData.getDatas();
        page = pagingData.getCurPage();
        if (page == 1) {
            articleAdapter.setNewInstance(datas);
        } else {
            articleAdapter.addData(datas);
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
