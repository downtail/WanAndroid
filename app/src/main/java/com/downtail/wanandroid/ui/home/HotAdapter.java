package com.downtail.wanandroid.ui.home;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.entity.response.HotEntity;

import org.jetbrains.annotations.NotNull;

public class HotAdapter extends BaseQuickAdapter<HotEntity, BaseViewHolder> {

    public HotAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HotEntity hotEntity) {
        baseViewHolder.setText(R.id.tvBody, hotEntity.getName());
    }
}
