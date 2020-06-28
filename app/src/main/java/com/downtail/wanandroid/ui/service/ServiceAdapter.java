package com.downtail.wanandroid.ui.service;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.utils.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class ServiceAdapter extends BaseQuickAdapter<ServiceResponse, BaseViewHolder> {

    public ServiceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ServiceResponse serviceResponse) {
        ImageLoader.loadCircleImage(getContext(),baseViewHolder.getView(R.id.ivAvatar),R.drawable.image_wx_avatar);
        baseViewHolder.setText(R.id.tvAuthor,serviceResponse.getName());
    }
}
