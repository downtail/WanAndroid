package com.downtail.wanandroid.presenter.mine;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.mine.RecordContract;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.RecordMultipleEntity;
import com.downtail.wanandroid.entity.response.RecordResponse;
import com.downtail.wanandroid.entity.response.Paging;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RecordPresenter extends BasePresenter<RecordContract.View> implements RecordContract.Presenter {
    @Inject
    public RecordPresenter() {
    }

    @Override
    public void getRankData(int page) {
        Observable.zip(
                dataManager.getRankData()
                        .compose(mView.bindToLife())
                        .compose(RxUtil.transformer())
                        .subscribeOn(Schedulers.io()),
                dataManager.getRecordData(page)
                        .compose(mView.bindToLife())
                        .compose(RxUtil.transformer())
                        .subscribeOn(Schedulers.io()),
                new BiFunction<RankResponse, Paging<RecordResponse>, Paging<RecordMultipleEntity>>() {
                    @Override
                    public Paging<RecordMultipleEntity> apply(RankResponse rankResponse, Paging<RecordResponse> recordResponsePaging) throws Exception {
                        List<RecordMultipleEntity> data = new ArrayList<>();
                        data.add(new RecordMultipleEntity(rankResponse));
                        List<RecordResponse> datas = recordResponsePaging.getDatas();
                        for (int i = 0; i < datas.size(); i++) {
                            data.add(new RecordMultipleEntity(datas.get(i)));
                        }
                        return new Paging<>(recordResponsePaging.getCurPage(), recordResponsePaging.getOffset(), recordResponsePaging.isOver(), recordResponsePaging.getPageCount(), recordResponsePaging.getSize(), recordResponsePaging.getTotal(), data);
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Paging<RecordMultipleEntity>>(mView) {
                    @Override
                    public void onSuccess(Paging<RecordMultipleEntity> data) {
                        mView.loadRecordData(data);
                    }
                });
    }

    @Override
    public void getRecordData(int page) {
        dataManager.getRecordData(page)
                .compose(mView.bindToLife())
                .compose(RxUtil.transformer())
                .subscribeOn(Schedulers.io())
                .map(new Function<Paging<RecordResponse>, Paging<RecordMultipleEntity>>() {
                    @Override
                    public Paging<RecordMultipleEntity> apply(Paging<RecordResponse> recordResponsePaging) throws Exception {
                        List<RecordMultipleEntity> data = new ArrayList<>();
                        List<RecordResponse> datas = recordResponsePaging.getDatas();
                        for (int i = 0; i < datas.size(); i++) {
                            data.add(new RecordMultipleEntity(datas.get(i)));
                        }
                        return new Paging<>(recordResponsePaging.getCurPage(), recordResponsePaging.getOffset(), recordResponsePaging.isOver(), recordResponsePaging.getPageCount(), recordResponsePaging.getSize(), recordResponsePaging.getTotal(), data);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Paging<RecordMultipleEntity>>(mView) {
                    @Override
                    public void onSuccess(Paging<RecordMultipleEntity> data) {
                        mView.loadRecordData(data);
                    }
                });
    }
}
