package com.downtail.wanandroid.di.component;

import android.content.Context;

import com.downtail.wanandroid.core.DataManager;
import com.downtail.wanandroid.di.module.AppModule;
import com.downtail.wanandroid.di.scope.AppScope;
import com.downtail.wanandroid.di.scope.ContextLife;

import dagger.Component;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    DataManager getDataManager();
}
