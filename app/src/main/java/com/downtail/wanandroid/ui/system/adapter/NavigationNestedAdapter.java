package com.downtail.wanandroid.ui.system.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NavigationNestedAdapter extends BaseQuickAdapter<ArticleResponse, BaseViewHolder> {

    private int cid;

    public NavigationNestedAdapter(int layoutResId, @Nullable List<ArticleResponse> data, int cid) {
        super(layoutResId, data);
        this.cid = cid;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ArticleResponse articleResponse) {
        baseViewHolder.setText(R.id.tvNested, articleResponse.getTitle());
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
