package com.downtail.wanandroid.entity.local;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.downtail.wanandroid.entity.response.RankResponse;
import com.downtail.wanandroid.entity.response.RecordResponse;

public class RecordMultipleEntity implements MultiItemEntity {

    private int itemType;

    public static final int RANK = 0;
    public static final int RECORD = 1;

    private RankResponse rank;
    private RecordResponse record;

    public RecordMultipleEntity(RankResponse rank) {
        this.itemType = RANK;
        this.rank = rank;
    }

    public RecordMultipleEntity(RecordResponse record) {
        this.itemType = RECORD;
        this.record = record;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public RankResponse getRank() {
        return rank;
    }

    public RecordResponse getRecord() {
        return record;
    }
}
