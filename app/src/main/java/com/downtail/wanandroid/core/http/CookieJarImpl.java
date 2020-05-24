package com.downtail.wanandroid.core.http;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieJarImpl implements CookieJar {

    private final PersistentCookieStore cookieStore = PersistentCookieStore.getInstance();

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        return cookieStore.get(httpUrl);
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        //本地可校验cookie，并根据需要存储
        if (list != null && list.size() > 0) {
            for (Cookie item : list) {
                cookieStore.add(httpUrl, item);
            }
        }
    }
}
