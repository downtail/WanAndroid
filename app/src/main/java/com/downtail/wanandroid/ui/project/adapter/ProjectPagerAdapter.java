package com.downtail.wanandroid.ui.project.adapter;

import com.downtail.wanandroid.ui.project.fragment.ArticleFragment;
import com.downtail.wanandroid.entity.response.CategoryResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProjectPagerAdapter extends FragmentStateAdapter {

    private List<CategoryResponse> data;

    public ProjectPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        CategoryResponse categoryResponse = data.get(position);
        return ArticleFragment.getInstance(categoryResponse != null ? categoryResponse.getId() : 0);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<CategoryResponse> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
