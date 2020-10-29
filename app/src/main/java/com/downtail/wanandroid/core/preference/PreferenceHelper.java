package com.downtail.wanandroid.core.preference;

import com.downtail.wanandroid.entity.response.UserEntity;

import io.reactivex.Observable;

public interface PreferenceHelper {

    boolean getLoginStatus();

    void setLoginStatus(boolean isLogin);

    Observable<Boolean> setUserEntity(UserEntity entity);

    Observable<UserEntity> getUserEntity();
}
