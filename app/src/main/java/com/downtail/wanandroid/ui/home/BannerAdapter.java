package com.downtail.wanandroid.ui.home;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.entity.response.BannerResponse;
import com.downtail.wanandroid.utils.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class BannerAdapter extends BaseQuickAdapter<BannerResponse, BaseViewHolder> {

    public BannerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BannerResponse bannerResponse) {
        ImageLoader.loadNormalImage(getContext(), baseViewHolder.getView(R.id.ivBanner), bannerResponse.getImagePath());
    }
}
