package com.downtail.wanandroid.app;

import android.app.Activity;
import android.content.Intent;

import com.downtail.wanandroid.ui.MainActivity;

public class Navigator {

    public static void openMain(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
