package com.downtail.wanandroid.presenter.home;

import android.text.TextUtils;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.home.HomeContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.ui.home.BannerResponse;
import com.downtail.wanandroid.ui.project.entity.ArticleMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    @Inject
    public HomePresenter() {
    }

    @Override
    public void getBannerData() {
        dataManager.getBannerData()
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<BannerResponse>>(mView) {
                    @Override
                    public void onSuccess(List<BannerResponse> data) {
                        mView.loadBannerData(data);
                    }
                });
    }

    @Override
    public void getAdvancedArticleData(int page) {
        Observable.zip(
                dataManager.getAdvancedArticleData()
                        .compose(mView.bindToLife())
                        .compose(RxUtil.transformer())
                        .subscribeOn(Schedulers.io()),
                dataManager.getHomeArticleData(page)
                        .compose(mView.bindToLife())
                        .compose(RxUtil.transformer())
                        .subscribeOn(Schedulers.io()),
                new BiFunction<List<ArticleResponse>, Paging<ArticleResponse>, Paging<ArticleMultipleEntity>>() {
                    @Override
                    public Paging<ArticleMultipleEntity> apply(List<ArticleResponse> articleResponses, Paging<ArticleResponse> paging) throws Exception {
                        List<ArticleMultipleEntity> data = new ArrayList<>();
                        for (int i = 0; i < articleResponses.size(); i++) {
                            ArticleResponse articleResponse = articleResponses.get(i);
                            if (TextUtils.isEmpty(articleResponse.getEnvelopePic())) {
                                data.add(new ArticleMultipleEntity(ArticleMultipleEntity.TEXT, articleResponse));
                            } else {
                                data.add(new ArticleMultipleEntity(ArticleMultipleEntity.IMAGE, articleResponse));
                            }
                        }
                        List<ArticleResponse> datas = paging.getDatas();
                        for (int i = 0; i < datas.size(); i++) {
                            ArticleResponse articleResponse = datas.get(i);
                            if (TextUtils.isEmpty(articleResponse.getEnvelopePic())) {
                                data.add(new ArticleMultipleEntity(ArticleMultipleEntity.TEXT, articleResponse));
                            } else {
                                data.add(new ArticleMultipleEntity(ArticleMultipleEntity.IMAGE, articleResponse));
                            }
                        }
                        return new Paging<>(paging.getCurPage(), paging.getOffset(), paging.isOver(), paging.getPageCount(), paging.getSize(), paging.getTotal(), data);

                    }
                })
                .compose(mView.bindToLife())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Paging<ArticleMultipleEntity>>(mView) {
                    @Override
                    public void onSuccess(Paging<ArticleMultipleEntity> data) {
                        mView.loadAdvancedArticleData(data);
                    }
                });
    }

    @Override
    public void getHomeArticleData(int page) {
        dataManager.getHomeArticleData(page)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .map(new Function<Paging<ArticleResponse>, Paging<ArticleMultipleEntity>>() {
                    @Override
                    public Paging<ArticleMultipleEntity> apply(Paging<ArticleResponse> paging) throws Exception {
                        List<ArticleMultipleEntity> list = new ArrayList<>();
                        List<ArticleResponse> datas = paging.getDatas();
                        for (int i = 0; i < datas.size(); i++) {
                            ArticleResponse articleResponse = datas.get(i);
                            if (TextUtils.isEmpty(articleResponse.getEnvelopePic())) {
                                list.add(new ArticleMultipleEntity(ArticleMultipleEntity.TEXT, articleResponse));
                            } else {
                                list.add(new ArticleMultipleEntity(ArticleMultipleEntity.IMAGE, articleResponse));
                            }
                        }
                        return new Paging<>(paging.getCurPage(), paging.getOffset(), paging.isOver(), paging.getPageCount(), paging.getSize(), paging.getTotal(), list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Paging<ArticleMultipleEntity>>(mView) {
                    @Override
                    public void onSuccess(Paging<ArticleMultipleEntity> data) {
                        mView.loadAdvancedArticleData(data);
                    }
                });
    }

    @Override
    public void confirmArticleCollect(int position, int id) {
        dataManager.confirmArticleCollect(id)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        mView.loadArticleCollectState(position, true);
                    }
                });
    }

    @Override
    public void cancelArticleCollect(int position, int id) {
        dataManager.cancelArticleCollect(id)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        mView.loadArticleCollectState(position, false);
                    }
                });
    }
}
