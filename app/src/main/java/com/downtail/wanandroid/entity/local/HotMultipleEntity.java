package com.downtail.wanandroid.entity.local;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.downtail.wanandroid.entity.response.HotEntity;

public class HotMultipleEntity implements MultiItemEntity {

    public static final int HEADER = 0;
    public static final int BODY = 1;
    public static final int EMPTY = 2;
    private int itemType;

    private HotEntity body;

    public HotMultipleEntity(int itemType) {
        this.itemType = itemType;
    }

    public HotMultipleEntity(HotEntity body) {
        this.itemType = BODY;
        this.body = body;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public HotEntity getBody() {
        return body;
    }
}
