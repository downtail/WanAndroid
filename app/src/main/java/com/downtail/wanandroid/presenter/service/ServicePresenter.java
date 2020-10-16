package com.downtail.wanandroid.presenter.service;

import com.blankj.utilcode.util.GsonUtils;
import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.service.ServiceContract;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.RxUtil;
import com.downtail.wanandroid.entity.db.Author;
import com.downtail.wanandroid.entity.local.AuthorEntity;
import com.downtail.wanandroid.entity.response.ServiceResponse;
import com.downtail.wanandroid.utils.AssetsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class ServicePresenter extends BasePresenter<ServiceContract.View> implements ServiceContract.Presenter {

    @Inject
    public ServicePresenter() {
    }

    @Override
    public void getServiceColumnData() {
        dataManager.getAuthorList()
                .compose(mView.bindToLife())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<Author>>() {
                    @Override
                    public void accept(List<Author> authors) throws Exception {
                        if (mView != null) {
                            if (authors.isEmpty()) {
                                return;
                            }
                            mView.loadAuthorData(authors);
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .filter(new Predicate<List<Author>>() {
                    @Override
                    public boolean test(List<Author> authors) throws Exception {
                        return authors.isEmpty();
                    }
                })
                .flatMap(new Function<List<Author>, ObservableSource<BaseResponse<List<ServiceResponse>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<ServiceResponse>>> apply(List<Author> authors) throws Exception {
                        return dataManager.getServiceColumnData();
                    }
                })
                .compose(RxUtil.transformer())
                .map(new Function<List<ServiceResponse>, List<Author>>() {
                    @Override
                    public List<Author> apply(List<ServiceResponse> serviceResponses) throws Exception {
                        List<Author> data = new ArrayList<>();
                        String original = AssetsUtil.getAssetsAuthorData(App.getInstance());
                        List<AuthorEntity> list = GsonUtils.fromJson(original, GsonUtils.getListType(AuthorEntity.class));
                        for (int i = 0; i < serviceResponses.size(); i++) {
                            Author author = new Author();
                            ServiceResponse serviceResponse = serviceResponses.get(i);
                            author.setAuthorId(serviceResponse.getId());
                            author.setName(serviceResponse.getName());
                            for (int j = 0; j < list.size(); j++) {
                                AuthorEntity authorEntity = list.get(j);
                                if (authorEntity.getAuthorId() == serviceResponse.getId()) {
                                    author.setThumb(authorEntity.getThumb());
                                    author.setDescription(authorEntity.getDescription());
                                    break;
                                }
                            }
                            data.add(author);
                        }
                        return data;
                    }
                })
                .doOnNext(new Consumer<List<Author>>() {
                    @Override
                    public void accept(List<Author> authors) throws Exception {
                        dataManager.saveAuthorList(authors);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<Author>>() {
                    @Override
                    public void onSuccess(List<Author> data) {
                        if (mView != null) {
                            mView.loadAuthorData(data);
                        }
                    }
                });
    }

    @Override
    public void saveAuthorList(List<Author> list) {
        //从异步线程中进行数据库写入操作
        Observable.just("AsyncOperation")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        dataManager.saveAuthorList(list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>() {
                    @Override
                    public void onSuccess(String data) {

                    }
                });
    }
}
