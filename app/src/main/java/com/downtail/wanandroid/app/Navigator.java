package com.downtail.wanandroid.app;

import android.app.Activity;
import android.content.Intent;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.browser.BrowserActivity;
import com.downtail.wanandroid.ui.main.LoginActivity;
import com.downtail.wanandroid.ui.main.MainActivity;
import com.downtail.wanandroid.ui.mine.activity.CollectActivity;
import com.downtail.wanandroid.ui.mine.activity.PreferenceActivity;
import com.downtail.wanandroid.ui.mine.activity.RankActivity;
import com.downtail.wanandroid.ui.mine.activity.RecordActivity;
import com.downtail.wanandroid.ui.service.ClientActivity;

public class Navigator {

    public static void openMain(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void openSetting(Activity activity) {
        Intent intent = new Intent(activity, PreferenceActivity.class);
        activity.startActivity(intent);
    }

    public static void openLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
    }

    public static void openCollect(Activity activity) {
        Intent intent = new Intent(activity, CollectActivity.class);
        activity.startActivity(intent);
    }

    public static void openBrowser(Activity activity, String url, int id, boolean isCollect) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra("id", id);
        intent.putExtra("isCollect", isCollect);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
    }

    public static void openRank(Activity activity) {
        Intent intent = new Intent(activity, RankActivity.class);
        activity.startActivity(intent);
    }

    public static void openRecord(Activity activity) {
        Intent intent = new Intent(activity, RecordActivity.class);
        activity.startActivity(intent);
    }

    public static void openClient(Activity activity, int id, String name) {
        Intent intent = new Intent(activity, ClientActivity.class);
        intent.putExtra(ClientActivity.ID, id);
        intent.putExtra(ClientActivity.NAME, name);
        activity.startActivity(intent);
    }
}
