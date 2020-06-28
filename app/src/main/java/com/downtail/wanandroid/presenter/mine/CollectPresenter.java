package com.downtail.wanandroid.presenter.mine;

import android.text.TextUtils;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.mine.CollectContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.ui.mine.entity.WebsiteResponse;
import com.downtail.wanandroid.ui.project.entity.ArticleMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {

    @Inject
    public CollectPresenter() {
    }

    @Override
    public void getCollectArticleData(int page) {
        dataManager.getCollectArticleData(page)
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
                            articleResponse.setCollect(true);
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
                        mView.loadCollectArticleData(data);
                    }
                });
    }

    @Override
    public void getCollectWebsiteData() {
        dataManager.getCollectWebsiteData()
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .map(new Function<List<WebsiteResponse>, List<ArticleMultipleEntity>>() {
                    @Override
                    public List<ArticleMultipleEntity> apply(List<WebsiteResponse> websiteResponses) throws Exception {
                        List<ArticleMultipleEntity> data = new ArrayList<>();
                        data.add(new ArticleMultipleEntity(ArticleMultipleEntity.EXTRA));
                        for (int i = 0; i < websiteResponses.size(); i++) {
                            data.add(new ArticleMultipleEntity(websiteResponses.get(i)));
                        }
                        return data;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<ArticleMultipleEntity>>(mView) {
                    @Override
                    public void onSuccess(List<ArticleMultipleEntity> data) {
                        mView.loadCollectWebsiteData(data);
                    }
                });
    }

    @Override
    public void collectWebsite(String name, String link) {
        dataManager.collectWebsite(name, link)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .map(new Function<WebsiteResponse, List<ArticleMultipleEntity>>() {
                    @Override
                    public List<ArticleMultipleEntity> apply(WebsiteResponse websiteResponse) throws Exception {
                        List<ArticleMultipleEntity> data = new ArrayList<>();
                        data.add(new ArticleMultipleEntity(websiteResponse));
                        return data;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<ArticleMultipleEntity>>(mView) {
                    @Override
                    public void onSuccess(List<ArticleMultipleEntity> data) {
                        mView.collectWebsiteSuccess(data);
                    }
                });
    }

    @Override
    public void modifyWebsite(int position, int id, String name, String link) {
        dataManager.modifyWebsite(id, name, link)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .map(new Function<WebsiteResponse, ArticleMultipleEntity>() {
                    @Override
                    public ArticleMultipleEntity apply(WebsiteResponse websiteResponse) throws Exception {
                        return new ArticleMultipleEntity(websiteResponse);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ArticleMultipleEntity>(mView) {
                    @Override
                    public void onSuccess(ArticleMultipleEntity data) {
                        mView.modifyWebsiteSuccess(position, data);
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

    @Override
    public void cancelWebsiteCollect(int position, int id) {
        dataManager.cancelWebsiteCollect(id)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        mView.cancelWebsiteSuccess(position);
                    }
                });
    }
}
