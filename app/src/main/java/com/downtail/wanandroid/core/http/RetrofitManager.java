package com.downtail.wanandroid.core.http;


import com.downtail.wanandroid.BuildConfig;
import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.app.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final int CONNECT_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;
    private static final long CACHE_SIZE = 100 * 1024 * 1024;

    private Retrofit mRetrofit;

    private RetrofitManager() {
        File file = new File(App.getInstance().getExternalCacheDir(), "NetworkCache");
        Cache cache = new Cache(file, CACHE_SIZE);
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJarImpl())
                .cache(cache)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static class Singleton {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return Singleton.INSTANCE;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
