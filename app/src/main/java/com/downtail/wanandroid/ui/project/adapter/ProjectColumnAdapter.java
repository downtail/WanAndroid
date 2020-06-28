package com.downtail.wanandroid.ui.project.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.project.entity.CategoryResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.core.content.ContextCompat;

public class ProjectColumnAdapter extends BaseQuickAdapter<CategoryResponse, BaseViewHolder> {

    private int index = 0;

    public ProjectColumnAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CategoryResponse categoryResponse) {
        baseViewHolder.setText(R.id.tvColumn, categoryResponse.getName());
        int position = baseViewHolder.getAdapterPosition();
        if (position == index) {
            baseViewHolder.setTextColor(R.id.tvColumn, ContextCompat.getColor(getContext(), R.color.color_ffffff));
        } else {
            baseViewHolder.setTextColor(R.id.tvColumn, ContextCompat.getColor(getContext(), R.color.color_cecece));
        }
    }

    @Override
    public void setNewInstance(@Nullable List<CategoryResponse> list) {
        this.index = 0;
        super.setNewInstance(list);
    }

    public void setIndex(int index) {
        int lastIndex = this.index;
        this.index = index;
        notifyItemChanged(lastIndex);
        notifyItemChanged(this.index);
    }
}
