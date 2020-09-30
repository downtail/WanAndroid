package com.downtail.wanandroid.core.http.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * NursingWorker
 * Created by downtail on 2020/9/9
 */
public class ProgressInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Map<String, List<String>> headers = request.headers().toMultimap();
        if (headers.containsKey("shouldInterceptProgress")) {
            return response.newBuilder()
                    .body(new ProgressResponseBody(response.body(), new ProgressListener() {
                        int previousValue = 0;

                        @Override
                        public void update(long bytesRead, long contentLength, boolean done) {
                            int currentValue = Float.valueOf(bytesRead * 100f / contentLength).intValue();
                            if (currentValue != previousValue && currentValue % 5 == 0) {
                                previousValue = currentValue;
                            }
                        }
                    }))
                    .build();
        }
        return response;
    }
}
