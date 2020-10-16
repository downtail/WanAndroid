package com.downtail.wanandroid.di.module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.DataManager;
import com.downtail.wanandroid.core.db.DatabaseHelper;
import com.downtail.wanandroid.core.db.DatabaseHelperImpl;
import com.downtail.wanandroid.core.http.HttpHelper;
import com.downtail.wanandroid.core.http.HttpHelperImpl;
import com.downtail.wanandroid.core.http.RetrofitManager;
import com.downtail.wanandroid.core.http.api.HomeApi;
import com.downtail.wanandroid.core.http.api.ProjectApi;
import com.downtail.wanandroid.core.http.api.ServiceApi;
import com.downtail.wanandroid.core.http.api.SetupApi;
import com.downtail.wanandroid.core.http.api.SystemApi;
import com.downtail.wanandroid.core.preference.PreferenceHelper;
import com.downtail.wanandroid.core.preference.PreferenceHelperImpl;
import com.downtail.wanandroid.di.scope.AppScope;
import com.downtail.wanandroid.di.scope.ContextLife;
import com.downtail.wanandroid.entity.db.DaoMaster;
import com.downtail.wanandroid.entity.db.DaoSession;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @ContextLife("Application")
    @AppScope
    @Provides
    public Context provideApplicationContext() {
        return application.getApplicationContext();
    }

    @AppScope
    @Provides
    public HomeApi provideHomeApi() {
        return RetrofitManager.getInstance().create(HomeApi.class);
    }

    @AppScope
    @Provides
    public SystemApi provideSystemApi() {
        return RetrofitManager.getInstance().create(SystemApi.class);
    }

    @AppScope
    @Provides
    public ServiceApi provideNavigationApi() {
        return RetrofitManager.getInstance().create(ServiceApi.class);
    }

    @AppScope
    @Provides
    public ProjectApi provideProjectApi() {
        return RetrofitManager.getInstance().create(ProjectApi.class);
    }

    @AppScope
    @Provides
    public SetupApi provideSetupApi() {
        return RetrofitManager.getInstance().create(SetupApi.class);
    }

    @AppScope
    @Provides
    public HttpHelper provideHttpHelper(HttpHelperImpl helper) {
        return helper;
    }

    @AppScope
    @Provides
    public DaoSession provideDaoSession() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(application, Constants.DB_NAME);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        return daoMaster.newSession();
    }

    @AppScope
    @Provides
    public DatabaseHelper provideDatabaseHelper(DatabaseHelperImpl helper) {
        return helper;
    }

    @AppScope
    @Provides
    public PreferenceHelper providePreferenceHelper(PreferenceHelperImpl helper) {
        return helper;
    }

    @AppScope
    @Provides
    public DataManager provideDataManager(HttpHelper httpHelper, DatabaseHelper databaseHelper, PreferenceHelper preferenceHelper) {
        return new DataManager(httpHelper, databaseHelper, preferenceHelper);
    }
}
