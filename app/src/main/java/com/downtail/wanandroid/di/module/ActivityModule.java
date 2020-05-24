package com.downtail.wanandroid.di.module;

import android.app.Activity;
import android.content.Context;

import com.downtail.wanandroid.di.scope.ActivityScope;
import com.downtail.wanandroid.di.scope.ContextLife;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @ActivityScope
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @ContextLife("Activity")
    @ActivityScope
    @Provides
    public Context provideActivityContext() {
        return mActivity;
    }
}
