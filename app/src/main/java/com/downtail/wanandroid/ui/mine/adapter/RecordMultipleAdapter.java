package com.downtail.wanandroid.ui.mine.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.plus.extensions.FloaterExtension;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.local.RecordMultipleEntity;
import com.downtail.wanandroid.entity.response.RecordResponse;

import org.jetbrains.annotations.NotNull;

public class RecordMultipleAdapter extends BaseMultiItemQuickAdapter<RecordMultipleEntity, BaseViewHolder> implements FloaterExtension {

    public RecordMultipleAdapter() {
        addItemType(RecordMultipleEntity.RANK, R.layout.item_rank);
        addItemType(RecordMultipleEntity.RECORD, R.layout.item_record);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecordMultipleEntity recordMultipleEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        if (itemViewType == RecordMultipleEntity.RANK) {
            RankResponse rank = recordMultipleEntity.getRank();
            if (rank != null) {
                baseViewHolder.setText(R.id.tvRank, String.valueOf(rank.getRank()));
                baseViewHolder.setText(R.id.tvLevel, String.valueOf(rank.getLevel()));
                baseViewHolder.setText(R.id.tvNick, rank.getUsername());
                baseViewHolder.setText(R.id.tvCoin, String.valueOf(rank.getCoinCount()));
            }
        } else if (itemViewType == RecordMultipleEntity.RECORD) {
            RecordResponse record = recordMultipleEntity.getRecord();
            if (record != null) {
                baseViewHolder.setText(R.id.tvDescription, record.getDesc());
            }
        }
    }

    @Override
    public boolean isFloaterView(int position) {
        return getItemViewType(position) == RecordMultipleEntity.RANK;
    }

    @Override
    public int getItemType(int position) {
        return RecordMultipleEntity.RANK;
    }
}
