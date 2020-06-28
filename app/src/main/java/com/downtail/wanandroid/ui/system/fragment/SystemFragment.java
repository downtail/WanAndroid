package com.downtail.wanandroid.ui.system.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.system.SystemContract;
import com.downtail.wanandroid.presenter.system.SystemPresenter;
import com.downtail.wanandroid.ui.system.adapter.SystemAdapter;
import com.downtail.wanandroid.ui.system.entity.SystemResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SystemFragment extends BaseFragment<SystemPresenter> implements SystemContract.View,
        OnItemClickListener {

    @BindView(R.id.rvSystem)
    RecyclerView rvSystem;

    private SystemAdapter systemAdapter;

    public static SystemFragment getInstance() {
        Bundle args = new Bundle();
        SystemFragment fragment = new SystemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initEvents() {
        rvSystem.setLayoutManager(new LinearLayoutManager(_mActivity));
        systemAdapter = new SystemAdapter(R.layout.item_column);
        systemAdapter.setOnItemClickListener(this);
        rvSystem.setAdapter(systemAdapter);

        mPresenter.getColumnData();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        SystemResponse item = systemAdapter.getItem(position);
        if (item != null) {

        }
    }

    @Override
    public void loadSystemColumnData(List<SystemResponse> data) {
        systemAdapter.setNewInstance(data);
    }
}
