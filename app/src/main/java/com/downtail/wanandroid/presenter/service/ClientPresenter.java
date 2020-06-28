package com.downtail.wanandroid.presenter.service;

import android.text.TextUtils;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.service.ClientContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.ui.project.entity.ArticleMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ClientPresenter extends BasePresenter<ClientContract.View> implements ClientContract.Presenter {

    @Inject
    public ClientPresenter() {
    }

    @Override
    public void getClientArticleData(int id, int page) {
        dataManager.getClientArticleData(id, page)
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
                        mView.loadProjectArticleData(data);
                    }
                });
    }

    @Override
    public void getClientArticleByKeyword(int id, int page, String keyword) {
        dataManager.getClientArticleByKeyword(id, page, keyword)
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
                        mView.loadProjectArticleData(data);
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
