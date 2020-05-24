package com.downtail.wanandroid.core;


import com.downtail.wanandroid.core.db.DatabaseHelper;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.core.http.HttpHelper;
import com.downtail.wanandroid.core.preference.PreferenceHelper;
import com.downtail.wanandroid.model.BannerResponse;

import java.util.List;

import io.reactivex.Observable;

public class DataManager implements HttpHelper, DatabaseHelper, PreferenceHelper {

    private HttpHelper httpHelper;
    private DatabaseHelper databaseHelper;
    private PreferenceHelper preferenceHelper;

    public DataManager(HttpHelper httpHelper, DatabaseHelper databaseHelper, PreferenceHelper preferenceHelper) {
        this.httpHelper = httpHelper;
        this.databaseHelper = databaseHelper;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public boolean getLoginStatus() {
        return preferenceHelper.getLoginStatus();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        preferenceHelper.setLoginStatus(isLogin);
    }

    @Override
    public Observable<BaseResponse<List<BannerResponse>>> getBannerData() {
        return httpHelper.getBannerData();
    }
}
