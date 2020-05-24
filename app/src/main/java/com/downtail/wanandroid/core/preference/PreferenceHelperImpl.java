package com.downtail.wanandroid.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.app.Constants;

import javax.inject.Inject;

public class PreferenceHelperImpl implements PreferenceHelper {

    private final SharedPreferences mPreferences;

    @Inject
    public PreferenceHelperImpl() {
        mPreferences = App.getInstance().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
    }
}
