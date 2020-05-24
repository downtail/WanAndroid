package com.downtail.wanandroid.presenter.mine;

import com.downtail.wanandroid.base.mvp.BasePresenter;
import com.downtail.wanandroid.contract.mine.SettingContract;

import javax.inject.Inject;

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    @Inject
    public SettingPresenter() {
    }
}
