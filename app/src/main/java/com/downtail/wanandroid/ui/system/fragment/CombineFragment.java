package com.downtail.wanandroid.ui.system.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.system.CombineContract;
import com.downtail.wanandroid.presenter.system.CombinePresenter;
import com.downtail.wanandroid.ui.system.adapter.CombineColumnAdapter;
import com.downtail.wanandroid.ui.system.adapter.CombineFragmentAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CombineFragment extends BaseFragment<CombinePresenter> implements CombineContract.View,
        OnItemClickListener {

    @BindView(R.id.rvColumn)
    RecyclerView rvColumn;
    @BindView(R.id.pagerColumn)
    ViewPager2 pagerColumn;

    private CombineColumnAdapter combineColumnAdapter;

    public static CombineFragment getInstance() {
        Bundle args = new Bundle();
        CombineFragment fragment = new CombineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_combine;
    }

    @Override
    protected void initEvents() {
        List<String> columns = Arrays.asList(getResources().getStringArray(R.array.arrayOfColumn));
        combineColumnAdapter = new CombineColumnAdapter(R.layout.item_combine_column, columns);
        combineColumnAdapter.setOnItemClickListener(this);
        rvColumn.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.HORIZONTAL, false));
        rvColumn.setAdapter(combineColumnAdapter);

        CombineFragmentAdapter combineFragmentAdapter = new CombineFragmentAdapter(this);
        pagerColumn.setOffscreenPageLimit(1);
        pagerColumn.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                combineColumnAdapter.setIndex(position);
            }
        });
        pagerColumn.setAdapter(combineFragmentAdapter);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onReload() {

    }

    @Override
    protected boolean supportStatusBar() {
        return true;
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        combineColumnAdapter.setIndex(position);
        pagerColumn.setCurrentItem(position);
    }
}
