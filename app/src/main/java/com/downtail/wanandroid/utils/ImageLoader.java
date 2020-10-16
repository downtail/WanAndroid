package com.downtail.wanandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageLoader {

    public static void loadNormalImage(Context context, ImageView ivTarget, String url) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadNormalImage(Context context, ImageView ivTarget, int resId) {
        Glide.with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadCircleImage(Context context, ImageView ivTarget, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadCircleImage(Context context, ImageView ivTarget, String url, @DrawableRes int resId) {
        Glide.with(context)
                .load(url)
                .placeholder(resId)
                .error(resId)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadCircleImage(Context context, ImageView ivTarget, int resId) {
        Glide.with(context)
                .load(resId)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadBlurImage(Context context, ImageView ivTarget, int resId) {
        MultiTransformation<Bitmap> multiTransformation = new MultiTransformation<>(new CenterCrop(), new BlurTransformation());
        Glide.with(context)
                .load(resId)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadRoundedImage(Context context, ImageView ivTarget, String url, int radiusDp) {
        MultiTransformation<Bitmap> multiTransformation = new MultiTransformation<>(new CenterCrop(), new RoundedCornersTransformation(DisplayUtil.dip2px(context, radiusDp), 0, RoundedCornersTransformation.CornerType.ALL));
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadRoundedImage(Context context, ImageView ivTarget, String url, int radiusDp, int resId) {
        MultiTransformation<Bitmap> multiTransformation = new MultiTransformation<>(new CenterCrop(), new RoundedCornersTransformation(DisplayUtil.dip2px(context, radiusDp), 0, RoundedCornersTransformation.CornerType.ALL));
        Glide.with(context)
                .load(url)
                .error(resId)
                .placeholder(resId)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }
}
