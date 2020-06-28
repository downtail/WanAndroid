package com.downtail.wanandroid.ui.system.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.system.entity.SystemResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SystemNestedAdapter extends BaseQuickAdapter<SystemResponse.ChildrenBean, BaseViewHolder> {

    private int id;

    public SystemNestedAdapter(int layoutResId, @Nullable List<SystemResponse.ChildrenBean> data, int id) {
        super(layoutResId, data);
        this.id = id;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SystemResponse.ChildrenBean childrenBean) {
        baseViewHolder.setText(R.id.tvNested, childrenBean.getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
