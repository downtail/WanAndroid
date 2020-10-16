package com.downtail.wanandroid.ui.service;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.entity.db.Author;
import com.downtail.wanandroid.utils.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class ServiceAdapter extends BaseQuickAdapter<Author, BaseViewHolder> {

    public ServiceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Author author) {
        ImageLoader.loadCircleImage(getContext(), baseViewHolder.getView(R.id.ivAvatar), author.getThumb(), R.drawable.image_wx_avatar);
        baseViewHolder.setText(R.id.tvAuthor, author.getName());
        baseViewHolder.setText(R.id.tvDescription, author.getDescription());
    }
}
