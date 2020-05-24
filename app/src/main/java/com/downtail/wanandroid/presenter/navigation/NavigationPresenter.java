package com.downtail.wanandroid.presenter.navigation;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.navigation.NavigationContract;

import javax.inject.Inject;

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    @Inject
    public NavigationPresenter() {
    }
}
