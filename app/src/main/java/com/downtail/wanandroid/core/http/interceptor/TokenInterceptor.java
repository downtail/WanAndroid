package com.downtail.wanandroid.core.http.interceptor;


import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.core.DataManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * NursingWorker
 * Created by downtail on 2020/8/28
 */
public class TokenInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request original = chain.request();
        DataManager dataManager = App.getInstance().getAppComponent().getDataManager();
        Request request = original.newBuilder()
                .addHeader("token", "")
                .build();
//        Response response = chain.proceed(request);
//        Map<String, List<String>> headers = response.headers().toMultimap();
//        if (headers.containsKey("Authorization")) {
//            List<String> tokens = headers.get("Authorization");
//            if (tokens != null && !tokens.isEmpty()) {
//                String token = tokens.get(0);
//                if (!TextUtils.isEmpty(token)) {
//                    token = token.replace("Bearer", "").trim();
//                    dataManager.setToken(token);
//                }
//            }
//        }
        return chain.proceed(request);
    }
}
