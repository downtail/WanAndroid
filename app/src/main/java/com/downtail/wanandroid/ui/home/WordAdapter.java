package com.downtail.wanandroid.ui.home;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.entity.db.Word;

import org.jetbrains.annotations.NotNull;

public class WordAdapter extends BaseQuickAdapter<Word, BaseViewHolder> {

    public WordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Word word) {
        baseViewHolder.setText(R.id.tvBody, word.getKeyword());
    }
}
