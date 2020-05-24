package com.downtail.wanandroid.di.component;

import android.content.Context;


import com.downtail.wanandroid.di.module.ActivityModule;
import com.downtail.wanandroid.di.scope.ActivityScope;
import com.downtail.wanandroid.di.scope.ContextLife;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
