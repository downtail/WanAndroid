package com.downtail.wanandroid.ui.system.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.system.entity.NavigationResponse;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationAdapter extends BaseQuickAdapter<NavigationResponse, BaseViewHolder> implements OnItemClickListener {

    private OnNestedClickListener onNestedClickListener;

    public NavigationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NavigationResponse navigationResponse) {
        baseViewHolder.setText(R.id.tvCategory, navigationResponse.getName());
        RecyclerView rvNested = baseViewHolder.getView(R.id.rvNested);
        RecyclerView.LayoutManager layoutManager = rvNested.getLayoutManager();
        if (layoutManager == null) {
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
            rvNested.setLayoutManager(flexboxLayoutManager);
        }
        RecyclerView.Adapter adapter = rvNested.getAdapter();
        List<ArticleResponse> articles = navigationResponse.getArticles();
        if (adapter == null) {
            NavigationNestedAdapter nestedAdapter = new NavigationNestedAdapter(R.layout.item_nested, articles, navigationResponse.getCid());
            nestedAdapter.setOnItemClickListener(this);
            adapter = nestedAdapter;
            rvNested.setAdapter(adapter);
        }
        if (adapter instanceof NavigationNestedAdapter) {
            NavigationNestedAdapter nestedAdapter = (NavigationNestedAdapter) adapter;
            if (nestedAdapter.getItemCount() != articles.size() || nestedAdapter.getCid() != navigationResponse.getCid()) {
                nestedAdapter.setNewInstance(articles);
                nestedAdapter.setCid(navigationResponse.getCid());
            }
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (onNestedClickListener != null) {
            if (adapter instanceof NavigationNestedAdapter) {
                NavigationNestedAdapter nestedAdapter = (NavigationNestedAdapter) adapter;
                ArticleResponse item = nestedAdapter.getItem(position);
                if (item != null) {
                    onNestedClickListener.onClick(item, position);
                }
            }
        }
    }

    public void setOnNestedClickListener(OnNestedClickListener onNestedClickListener) {
        this.onNestedClickListener = onNestedClickListener;
    }

    public interface OnNestedClickListener {
        void onClick(ArticleResponse articleResponse, int position);
    }
}
