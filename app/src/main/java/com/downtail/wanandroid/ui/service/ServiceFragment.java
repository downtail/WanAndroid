package com.downtail.wanandroid.ui.service;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.Navigator;
import com.downtail.wanandroid.base.fragment.BaseFragment;
import com.downtail.wanandroid.contract.service.ServiceContract;
import com.downtail.wanandroid.entity.db.Author;
import com.downtail.wanandroid.entity.response.ServiceResponse;
import com.downtail.wanandroid.presenter.service.ServicePresenter;
import com.downtail.wanandroid.widget.DragItemTouchHelper;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ServiceFragment extends BaseFragment<ServicePresenter> implements ServiceContract.View,
        OnItemClickListener {

    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.rvService)
    RecyclerView rvService;

    private ServiceAdapter serviceAdapter;

    public static ServiceFragment getInstance() {
        Bundle args = new Bundle();
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initEvents() {
        tvAction.setText(R.string.item_public);

        serviceAdapter = new ServiceAdapter(R.layout.item_service);
        serviceAdapter.setOnItemClickListener(this);
        rvService.setLayoutManager(new LinearLayoutManager(_mActivity));
        rvService.setAdapter(serviceAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragItemTouchHelper(new DragItemTouchHelper.DragListener() {
            @Override
            public void onItemDrag(int startIndex, int endIndex) {
                List<Author> data = serviceAdapter.getData();
                Long id = data.get(startIndex).getId();
                data.get(startIndex).setId(data.get(endIndex).getId());
                data.get(endIndex).setId(id);
                Collections.swap(data, startIndex, endIndex);
                serviceAdapter.notifyItemMoved(startIndex, endIndex);
                mPresenter.saveAuthorList(data);
            }
        }));
        itemTouchHelper.attachToRecyclerView(rvService);
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    protected boolean supportStatusBar() {
        return true;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mPresenter.getServiceColumnData();
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        Author item = serviceAdapter.getItem(position);
        if (item != null) {
            Navigator.openClient(_mActivity, item.getAuthorId(), item.getName());
        }
    }

    @Override
    public void loadAuthorData(List<Author> data) {
        serviceAdapter.setNewInstance(data);
    }
}
