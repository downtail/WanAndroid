package com.downtail.wanandroid.ui.mine.activity;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.ui.mine.adapter.CollectFragmentAdapter;
import com.downtail.wanandroid.ui.system.adapter.CombineColumnAdapter;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.OnClick;

public class CollectActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R.id.tvAction)
    TextView tvAction;
    @BindView(R.id.rvColumn)
    RecyclerView rvColumn;
    @BindView(R.id.pagerCollect)
    ViewPager2 pagerCollect;

    private CombineColumnAdapter combineColumnAdapter;
    private CollectFragmentAdapter collectFragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initEvents() {
        List<String> columns = Arrays.asList(getResources().getStringArray(R.array.arrayOfCollect));
        combineColumnAdapter = new CombineColumnAdapter(R.layout.item_combine_column, columns);
        combineColumnAdapter.setOnItemClickListener(this);
        rvColumn.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
        rvColumn.setAdapter(combineColumnAdapter);

        collectFragmentAdapter = new CollectFragmentAdapter(this);
        pagerCollect.setOffscreenPageLimit(1);
        pagerCollect.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                combineColumnAdapter.setIndex(position);
            }
        });
        pagerCollect.setAdapter(collectFragmentAdapter);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onReload() {

    }

    @OnClick({R.id.layoutBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutBack:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        combineColumnAdapter.setIndex(position);
        pagerCollect.setCurrentItem(position);
    }
}
