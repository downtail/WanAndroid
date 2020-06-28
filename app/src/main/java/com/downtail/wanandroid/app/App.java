package com.downtail.wanandroid.app;

import android.app.Application;
import android.content.Context;

import com.downtail.wanandroid.di.component.AppComponent;
import com.downtail.wanandroid.di.component.DaggerAppComponent;
import com.downtail.wanandroid.di.module.AppModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import me.jessyan.autosize.AutoSizeConfig;


public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsHeader(context);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }

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
