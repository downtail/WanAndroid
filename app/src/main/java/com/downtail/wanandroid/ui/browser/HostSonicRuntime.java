package com.downtail.wanandroid.ui.browser;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebResourceResponse;

import com.tencent.sonic.sdk.SonicRuntime;
import com.tencent.sonic.sdk.SonicSessionClient;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class HostSonicRuntime extends SonicRuntime {

    public HostSonicRuntime(Context context) {
        super(context);
    }

    @Override
    public void log(String tag, int level, String message) {

    }

    @Override
    public String getCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }

    @Override
    public boolean setCookie(String url, List<String> cookies) {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size() > 0) {
            CookieManager cookieManager = CookieManager.getInstance();
            for (String cookie : cookies) {
                cookieManager.setCookie(url, cookie);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getUserAgent() {
        return null;
    }

    @Override
    public String getCurrentUserAccount() {
        return null;
    }

    @Override
    public boolean isSonicUrl(String url) {
        return true;
    }

    @Override
    public Object createWebResourceResponse(String mimeType, String encoding, InputStream data, Map<String, String> headers) {
        WebResourceResponse resourceResponse = new WebResourceResponse(mimeType, encoding, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.setResponseHeaders(headers);
        }
        return resourceResponse;
    }

    @Override
    public boolean isNetworkValid() {
        return true;
    }

    @Override
    public void showToast(CharSequence text, int duration) {

    }

    @Override
    public void postTaskToThread(Runnable task, long delayMillis) {

    }

    @Override
    public void notifyError(SonicSessionClient client, String url, int errorCode) {

    }
}
