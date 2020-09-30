package com.downtail.wanandroid.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.home.HomeContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.presenter.home.HomePresenter;
import com.downtail.wanandroid.ui.project.adapter.ArticleAdapter;
import com.downtail.wanandroid.ui.project.entity.ArticleMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;
import com.downtail.wanandroid.utils.DisplayUtil;
import com.downtail.wanandroid.widget.ScalePageTransformer;
import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View,
        OnRefreshLoadMoreListener, OnItemClickListener, OnItemChildClickListener {

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.pagerBanner)
    ViewPager2 pagerBanner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;

    private BannerAdapter bannerAdapter;
    private ArticleAdapter articleAdapter;
    private Disposable timer;
    private int page = 0;

    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEvents() {
        tvAction.setText(R.string.item_home);

        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                if (Math.abs(verticalOffset) >= totalScrollRange) {
                    toolbar.setVisibility(View.VISIBLE);
                } else {
                    toolbar.setVisibility(View.GONE);
                }
            }
        });

        bannerAdapter = new BannerAdapter(R.layout.item_banner);
        bannerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
        pagerBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    int currentIndex = pagerBanner.getCurrentItem();
                    if (currentIndex == 0) {
                        pagerBanner.setCurrentItem(bannerAdapter.getItemCount() - 2, false);
                    } else if (currentIndex == bannerAdapter.getItemCount() - 1) {
                        pagerBanner.setCurrentItem(1, false);
                    }
                }
            }
        });
        pagerBanner.setOffscreenPageLimit(2);
        pagerBanner.setAdapter(bannerAdapter);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(DisplayUtil.dip2px(_mActivity, 10)));
        transformer.addTransformer(new ScalePageTransformer());
        pagerBanner.setPageTransformer(transformer);
        View child = pagerBanner.getChildAt(0);
        if (child instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) child;
            recyclerView.setPadding(DisplayUtil.dip2px(_mActivity, 20), 0, DisplayUtil.dip2px(_mActivity, 20), 0);
            recyclerView.setClipToPadding(false);
        }

        articleAdapter = new ArticleAdapter();
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnItemChildClickListener(this);
        articleAdapter.addChildClickViewIds(R.id.ivCollect);
        rvArticle.setLayoutManager(new LinearLayoutManager(_mActivity));
        rvArticle.setAdapter(articleAdapter);

        onReload();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {
        mPresenter.getBannerData();
        mPresenter.getAdvancedArticleData(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTimer();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishLoadMore(1000);
        mPresenter.getHomeArticleData(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        smartRefreshLayout.finishRefresh(1000);
        mPresenter.getAdvancedArticleData(0);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ArticleMultipleEntity item = articleAdapter.getItem(position);
        if (item != null) {
            ArticleResponse article = item.getArticle();
            if (article != null) {
                Navigator.openBrowser(_mActivity, article.getLink(), article.getId(), article.isCollect());
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

    private void startTimer() {
        int itemCount = bannerAdapter.getItemCount();
        if (itemCount > 3) {
            Observable.interval(5, 5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<Long>() {

                        @Override
                        public void onSubscribe(Disposable d) {
                            super.onSubscribe(d);
                            timer = d;
                        }

                        @Override
                        public void onSuccess(Long data) {
                            int currentIndex = pagerBanner.getCurrentItem();
                            if (currentIndex < bannerAdapter.getItemCount() - 1) {
                                pagerBanner.setCurrentItem(currentIndex + 1);
                            }
                        }
                    });
        }
    }

    private void stopTimer() {
        if (timer != null && !timer.isDisposed()) {
            timer.dispose();
            timer = null;
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        startTimer();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        stopTimer();
    }

    @Override
    public void loadBannerData(List<BannerResponse> data) {
        if (data.size() > 1) {
            BannerResponse firstElement = data.get(0);
            BannerResponse lastElement = data.get(data.size() - 1);
            data.add(0, lastElement);
            data.add(firstElement);
        }
        bannerAdapter.setNewInstance(data);
        pagerBanner.setCurrentItem(1, false);
        startTimer();
    }

    @Override
    public void loadAdvancedArticleData(Paging<ArticleMultipleEntity> pagingData) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        List<ArticleMultipleEntity> datas = pagingData.getDatas();
        if (datas != null && datas.size() > 0) {
            page = pagingData.getCurPage();
        }
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
                articleAdapter.notifyItemRangeChanged(position, 1);
                if (isCollect) {
                    toast(R.string.confirmCollectSuccess);
                } else {
                    toast(R.string.cancelCollectSuccess);
                }
            }
        }
    }
}
