package com.downtail.wanandroid.ui.project.adapter;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.project.entity.CategoryResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProjectColumnAdapter extends BaseQuickAdapter<CategoryResponse, BaseViewHolder> {

    private int index = 0;

    public ProjectColumnAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CategoryResponse categoryResponse) {
        baseViewHolder.setText(R.id.tvColumn, categoryResponse.getName());
        int position = baseViewHolder.getAdapterPosition();
        TextView tvColumn = baseViewHolder.getView(R.id.tvColumn);
        if (position == index) {
            baseViewHolder.setTextColor(R.id.tvColumn, ContextCompat.getColor(getContext(), R.color.color_18ce94));
            tvColumn.setTextSize(16);
            tvColumn.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            baseViewHolder.setTextColor(R.id.tvColumn, ContextCompat.getColor(getContext(), R.color.color_212121));
            tvColumn.setTextSize(14);
            tvColumn.setTypeface(Typeface.DEFAULT);
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
