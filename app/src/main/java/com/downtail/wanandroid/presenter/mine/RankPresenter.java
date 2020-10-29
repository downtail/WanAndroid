package com.downtail.wanandroid.presenter.mine;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.mine.RankContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.Paging;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RankPresenter extends BasePresenter<RankContract.View> implements RankContract.Presenter {

    @Inject
    public RankPresenter() {
    }

    @Override
    public void getRankListData(int page) {
        dataManager.getRankListData(page)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Paging<RankResponse>>(mView) {
                    @Override
                    public void onSuccess(Paging<RankResponse> data) {
                        mView.loadRankData(data);
                    }
                });
    }
}
