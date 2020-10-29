package com.downtail.wanandroid.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.GsonUtils;
import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.entity.response.UserEntity;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PreferenceHelperImpl implements PreferenceHelper {

    private final SharedPreferences mPreferences;

    @Inject
    public PreferenceHelperImpl() {
        mPreferences = App.getInstance().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public synchronized boolean getLoginStatus() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    @Override
    public synchronized void setLoginStatus(boolean isLogin) {
        mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
    }

    @Override
    public synchronized Observable<Boolean> setUserEntity(UserEntity entity) {
        String json = GsonUtils.toJson(entity);
        boolean flag = mPreferences.edit().putString(Constants.USER_INFO, json).commit();
        return Observable.just(flag);
    }

    @Override
    public synchronized Observable<UserEntity> getUserEntity() {
        String json = mPreferences.getString(Constants.USER_INFO, null);
        UserEntity userEntity = GsonUtils.fromJson(json, UserEntity.class);
        return Observable.just(userEntity);
    }
}
