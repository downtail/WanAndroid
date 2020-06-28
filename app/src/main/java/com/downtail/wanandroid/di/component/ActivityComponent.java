package com.downtail.wanandroid.di.component;

import android.content.Context;

import com.downtail.wanandroid.di.module.ActivityModule;
import com.downtail.wanandroid.di.scope.ActivityScope;
import com.downtail.wanandroid.di.scope.ContextLife;
import com.downtail.wanandroid.ui.browser.BrowserActivity;
import com.downtail.wanandroid.ui.main.LoginActivity;
import com.downtail.wanandroid.ui.main.MainActivity;
import com.downtail.wanandroid.ui.mine.activity.PreferenceActivity;
import com.downtail.wanandroid.ui.mine.activity.RankActivity;
import com.downtail.wanandroid.ui.mine.activity.RecordActivity;
import com.downtail.wanandroid.ui.service.ClientActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(PreferenceActivity activity);

    void inject(BrowserActivity activity);

    void inject(RankActivity activity);

    void inject(RecordActivity activity);

    void inject(ClientActivity activity);
}
