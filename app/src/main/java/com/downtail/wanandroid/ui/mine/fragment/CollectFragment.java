package com.downtail.wanandroid.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.mine.CollectContract;
import com.downtail.wanandroid.presenter.mine.CollectPresenter;
import com.downtail.wanandroid.ui.mine.entity.WebsiteResponse;
import com.downtail.wanandroid.ui.project.adapter.ArticleAdapter;
import com.downtail.wanandroid.ui.project.entity.ArticleMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;
import com.downtail.wanandroid.widget.dialog.EditDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CollectFragment extends BaseFragment<CollectPresenter> implements CollectContract.View,
        OnRefreshLoadMoreListener, OnItemClickListener, OnItemChildClickListener, OnItemLongClickListener {

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rvCollect)
    RecyclerView rvCollect;

    private static final String TYPE = "type";
    private int page = 0;
    private int code;
    private ArticleAdapter articleAdapter;

    public static CollectFragment getInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initEvents() {
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        articleAdapter = new ArticleAdapter();
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnItemChildClickListener(this);
        articleAdapter.addChildClickViewIds(R.id.ivCollect);
        articleAdapter.setOnItemLongClickListener(this);
        rvCollect.setLayoutManager(new LinearLayoutManager(_mActivity));
        rvCollect.setAdapter(articleAdapter);

        Bundle args = getArguments();
        if (args != null) {
            code = args.getInt(TYPE);
        }

        if (code == 0) {
            smartRefreshLayout.setEnableLoadMore(true);
            mPresenter.getCollectArticleData(0);
        } else {
            smartRefreshLayout.setEnableLoadMore(false);
            mPresenter.getCollectWebsiteData();
        }
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore(1000);
        if (code == 0) {
            mPresenter.getCollectArticleData(page);
        } else {
            mPresenter.getCollectWebsiteData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh(1000);
        if (code == 0) {
            mPresenter.getCollectArticleData(0);
        } else {
            mPresenter.getCollectWebsiteData();
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

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        int itemViewType = articleAdapter.getItemViewType(position);
        if (itemViewType == ArticleMultipleEntity.EXTRA) {
            EditDialog.getInstance()
                    .setCode(0)
                    .setOnEditListener(new EditDialog.OnEditListener() {
                        @Override
                        public void onHint(int resId) {
                            toast(resId);
                        }

                        @Override
                        public void onEdit(String name, String link) {
                            mPresenter.collectWebsite(name, link);
                        }

                        @Override
                        public void onComplete(int id, String name, String link) {

                        }
                    })
                    .show(getChildFragmentManager(), "edit");
        } else if (itemViewType == ArticleMultipleEntity.WEBSITE) {
            if (item != null) {
                WebsiteResponse website = item.getWebsite();
                if (website != null) {
                    Navigator.openBrowser(_mActivity, website.getLink(), website.getId(), true);
                }
            }
        } else {
            if (item != null) {
                ArticleResponse article = item.getArticle();
                if (article != null) {
                    Navigator.openBrowser(_mActivity, article.getLink(), article.getId(), article.isCollect());
                }
            }
        }
    }

    @Override
    public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        int itemViewType = articleAdapter.getItemViewType(position);
        if (itemViewType == ArticleMultipleEntity.WEBSITE) {
            ArticleMultipleEntity item = articleAdapter.getItem(position);
            if (item != null) {
                WebsiteResponse website = item.getWebsite();
                if (website != null) {
                    EditDialog.getInstance()
                            .setCode(1)
                            .setWebsite(website)
                            .setOnEditListener(new EditDialog.OnEditListener() {
                                @Override
                                public void onHint(int resId) {
                                    toast(resId);
                                }

                                @Override
                                public void onEdit(String name, String link) {

                                }

                                @Override
                                public void onComplete(int id, String name, String link) {
                                    mPresenter.modifyWebsite(position, id, name, link);
                                }
                            })
                            .show(getChildFragmentManager(), "edit");
                }
            }
        }
        return true;
    }

    private void confirmOrCancelCollect(int position) {
        int itemViewType = articleAdapter.getItemViewType(position);
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        if (item != null) {
            if (itemViewType == ArticleMultipleEntity.TEXT || itemViewType == ArticleMultipleEntity.IMAGE) {
                ArticleResponse article = item.getArticle();
                if (article != null) {
                    if (article.isCollect()) {
                        mPresenter.cancelArticleCollect(position, article.getId());
                    } else {
                        mPresenter.confirmArticleCollect(position, article.getId());
                    }
                }
            } else if (itemViewType == ArticleMultipleEntity.WEBSITE) {
                WebsiteResponse website = item.getWebsite();
                if (website != null) {
                    mPresenter.cancelWebsiteCollect(position, website.getId());
                }
            }
        }
    }

    @Override
    public void loadCollectArticleData(Paging<ArticleMultipleEntity> pagingData) {
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
    public void loadCollectWebsiteData(List<ArticleMultipleEntity> data) {
        articleAdapter.setNewInstance(data);
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

    @Override
    public void collectWebsiteSuccess(List<ArticleMultipleEntity> data) {
        articleAdapter.addData(1, data);
    }

    @Override
    public void modifyWebsiteSuccess(int position, ArticleMultipleEntity item) {
        articleAdapter.setData(position, item);
    }

    @Override
    public void cancelWebsiteSuccess(int position) {
        articleAdapter.remove(position);
    }
}
