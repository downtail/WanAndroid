package com.downtail.wanandroid.ui.system.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.core.content.ContextCompat;

public class CombineColumnAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int index = 0;

    public CombineColumnAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tvColumn, s);
        int position = baseViewHolder.getAdapterPosition();
        if (position == index) {
            baseViewHolder.setTextColor(R.id.tvColumn, ContextCompat.getColor(getContext(), R.color.color_ffffff));
        } else {
            baseViewHolder.setTextColor(R.id.tvColumn, ContextCompat.getColor(getContext(), R.color.color_cecece));
        }
    }

    public void setIndex(int index) {
        this.index = index;
        notifyItemRangeChanged(0, getItemCount());
    }
}
