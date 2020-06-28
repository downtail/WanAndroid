package com.downtail.wanandroid.ui.browser;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.broser.BrowserContract;
import com.downtail.wanandroid.presenter.browser.BrowserPresenter;
import com.downtail.wanandroid.utils.DisplayUtil;
import com.downtail.wanandroid.utils.ImageLoader;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import java.util.Random;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class BrowserActivity extends BaseActivity<BrowserPresenter> implements BrowserContract.View, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    public static final String PARAM_URL = "param_url";
    private static final String PARAM_MODE = "param_mode";
    float[] angles = new float[]{-35f, -25f, 0f, 25f, 35f};

    @BindView(R.id.rootView)
    ViewGroup rootView;
    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.webView)
    WebView webView;

    private SonicSession sonicSession;
    private GestureDetector gestureDetector;
    private boolean isCollect;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browser;
    }

    @Override
    protected void initEvents() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(PARAM_URL);
        if (TextUtils.isEmpty(url)) {
            finish();
            return;
        }
        id = intent.getIntExtra("id", 0);
        isCollect = intent.getBooleanExtra("isCollect", false);
        //硬件加速
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        //初始化sonic引擎
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new HostSonicRuntime(getApplicationContext()), new SonicConfig.Builder().build());
        }

        //SonicSession
        SonicSessionClientImpl sonicSessionClient = null;
        sonicSession = SonicEngine.getInstance().createSession(url, new SonicSessionConfig.Builder()
                .setSupportLocalServer(true)
                .setSessionMode(SonicConstants.SESSION_MODE_QUICK)
                .build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        } else {
//            toast("create sonic session fail!");
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
                showContent();
            }

            @TargetApi(21)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //绑定javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");

        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //支持缩放，默认为true。是下面那个的前提
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        //设置webview缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //
        webSettings.setAllowContentAccess(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);
        //保存密码
        //webSettings.setSavePassword(false);
        //保存表单数据
        webSettings.setSaveFormData(false);

        webView.setWebChromeClient(new WebChromeClient() {
            //进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            //获得网址标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else {
            webView.loadUrl(url);
        }

        gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);

    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @OnClick({R.id.layoutBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutBack:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(false);
    }

    @Override
    protected void onDestroy() {
        if (sonicSession != null) {
            sonicSession.destroy();
            sonicSession = null;
        }
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
    }

    @Override
    public void loadArticleCollectState(int touchX, int touchY, boolean isCollect) {
        this.isCollect = isCollect;
        if (isCollect) {
            showEscapeStar(touchX, touchY);
        } else {
            toast(R.string.cancelCollectSuccess);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        int touchX = Float.valueOf(e.getX()).intValue();
        int touchY = Float.valueOf(e.getY()).intValue();
        if (isCollect) {
            mPresenter.cancelArticleCollect(touchX, touchY, id);
        } else {
            mPresenter.confirmArticleCollect(touchX, touchY, id);
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (gestureDetector.onTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void showEscapeStar(int touchX, int touchY) {
        ImageView ivStar = new ImageView(mActivity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DisplayUtil.dip2px(mActivity, 50), DisplayUtil.dip2px(mActivity, 50));
        layoutParams.leftMargin = touchX - DisplayUtil.dip2px(mActivity, 25);
        layoutParams.topMargin = touchY - DisplayUtil.dip2px(mActivity, 50);
        ivStar.setLayoutParams(layoutParams);
//        ivStar.setLeft(touchX - DisplayUtil.dip2px(mActivity, 25));
//        ivStar.setBottom(touchY - DisplayUtil.dip2px(mActivity, 25));
        rootView.addView(ivStar);
        ImageLoader.loadNormalImage(mActivity, ivStar, R.drawable.image_collect_selected);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(getScaleAnim(ivStar, "scaleX", 100, 0, 2f, 0.9f))
                .with(getScaleAnim(ivStar, "scaleY", 100, 0, 2f, 0.9f))
                .with(getRotationAnim(ivStar, 0, 0, angles[new Random().nextInt(4)]))
                .with(getAlphaAnim(ivStar, 100, 0, 0f, 1f))
                .with(getScaleAnim(ivStar, "scaleX", 50, 150, 0.9f, 1f))
                .with(getScaleAnim(ivStar, "scaleY", 50, 150, 0.9f, 1f))
                .with(getTranslationYAnim(ivStar, 800, 400, 0f, -600f))
                .with(getAlphaAnim(ivStar, 300, 400, 1f, 0f))
                .with(getScaleAnim(ivStar, "scaleX", 700, 400, 1f, 3f))
                .with(getScaleAnim(ivStar, "scaleY", 700, 400, 1f, 3f));
        animatorSet.addPauseListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.removeView(ivStar);
            }
        });
        animatorSet.start();
    }

    private ObjectAnimator getRotationAnim(View view, long time, long delay, float... values) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", values);
        rotation.setDuration(time);
        rotation.setStartDelay(delay);
        return rotation;
    }

    private ObjectAnimator getAlphaAnim(View view, long time, long delay, float... values) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", values);
        alpha.setDuration(time);
        alpha.setStartDelay(delay);
        return alpha;
    }

    private ObjectAnimator getTranslationXAnim(View view, long time, long delay, float... values) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", values);
        translationX.setDuration(time);
        translationX.setStartDelay(delay);
        return translationX;
    }

    private ObjectAnimator getTranslationYAnim(View view, long time, long delay, float... values) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", values);
        translationY.setDuration(time);
        translationY.setStartDelay(delay);
        return translationY;
    }

    private ObjectAnimator getScaleAnim(View view, String propertyName, long time, long delay, float... values) {
        ObjectAnimator scale = ObjectAnimator.ofFloat(view, propertyName, values);
        scale.setDuration(time);
        scale.setStartDelay(delay);
        return scale;
    }
}
