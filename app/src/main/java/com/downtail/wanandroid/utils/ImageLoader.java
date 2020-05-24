package com.downtail.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {

    public static void loadNormalImage(Context context, ImageView ivTarget, String url) {
        Glide.with(context)
                .load(url)
                .into(ivTarget);
    }
}
