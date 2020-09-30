package com.downtail.wanandroid.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

public class ScalePageTransformer implements ViewPager2.PageTransformer {

    private static final float DEFAULT_MIN_SCALE = 0.85f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        ViewCompat.setElevation(page, -Math.abs(position));
        int width = page.getWidth();
        int height = page.getHeight();
        page.setPivotX(width / 2);
        page.setPivotY(height / 2);
        if (position <= -1) {
            page.setPivotX(width);
            page.setScaleX(DEFAULT_MIN_SCALE);
            page.setScaleY(DEFAULT_MIN_SCALE);
        } else if (position < 1) {
            if (position < 0) {
                float scaleFactor = (1 + position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(width);
            } else {
                float scaleFactor = (1 - position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(0);
            }
        } else if (position >= 1) {
            page.setPivotX(0);
            page.setScaleX(DEFAULT_MIN_SCALE);
            page.setScaleY(DEFAULT_MIN_SCALE);
        }
    }
}
