package com.downtail.wanandroid.presenter.browser;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.broser.BrowserContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BrowserPresenter extends BasePresenter<BrowserContract.View> implements BrowserContract.Presenter {

    @Inject
    public BrowserPresenter() {
    }

    @Override
    public void confirmArticleCollect(int touchX, int touchY, int id) {
        dataManager.confirmArticleCollect(id)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        mView.loadArticleCollectState(touchX, touchY, true);
                    }
                });
    }

    @Override
    public void cancelArticleCollect(int touchX, int touchY, int id) {
        dataManager.cancelArticleCollect(id)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(mView) {
                    @Override
                    public void onSuccess(String data) {
                        mView.loadArticleCollectState(touchX, touchY, false);
                    }
                });
    }
}
