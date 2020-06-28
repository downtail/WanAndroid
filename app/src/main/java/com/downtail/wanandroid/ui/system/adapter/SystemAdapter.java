package com.downtail.wanandroid.ui.system.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.system.entity.SystemResponse;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SystemAdapter extends BaseQuickAdapter<SystemResponse, BaseViewHolder> {

    public SystemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SystemResponse systemResponse) {
        baseViewHolder.setText(R.id.tvCategory, systemResponse.getName());
        RecyclerView rvNested = baseViewHolder.getView(R.id.rvNested);
        RecyclerView.LayoutManager layoutManager = rvNested.getLayoutManager();
        if (layoutManager == null) {
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
            rvNested.setLayoutManager(flexboxLayoutManager);
        }
        RecyclerView.Adapter adapter = rvNested.getAdapter();
        List<SystemResponse.ChildrenBean> children = systemResponse.getChildren();
        if (adapter == null) {
            adapter = new SystemNestedAdapter(R.layout.item_nested, children, systemResponse.getId());
            rvNested.setAdapter(adapter);
        }
        if (adapter instanceof SystemNestedAdapter) {
            SystemNestedAdapter nestedAdapter = (SystemNestedAdapter) adapter;
            if (nestedAdapter.getItemCount() != children.size() || nestedAdapter.getId() != systemResponse.getId()) {
                nestedAdapter.setNewInstance(children);
                nestedAdapter.setId(systemResponse.getId());
            }
        }
    }
}
