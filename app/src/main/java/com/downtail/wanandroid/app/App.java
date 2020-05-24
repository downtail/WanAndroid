package com.downtail.wanandroid.app;

import android.app.Application;
import android.content.Context;

import com.downtail.wanandroid.di.component.AppComponent;
import com.downtail.wanandroid.di.component.DaggerAppComponent;
import com.downtail.wanandroid.di.module.AppModule;

import androidx.multidex.MultiDex;
import me.jessyan.autosize.AutoSizeConfig;


public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化AppComponent
        initAppComponent();
        //Fragment分辨率适配
        AutoSizeConfig.getInstance().setCustomFragment(true);

    }

    public static App getInstance() {
        return instance;
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
