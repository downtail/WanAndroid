package com.downtail.wanandroid.ui.mine.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.mine.entity.RankResponse;

import org.jetbrains.annotations.NotNull;

public class RankAdapter extends BaseQuickAdapter<RankResponse, BaseViewHolder> {

    public RankAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RankResponse rankResponse) {
        baseViewHolder.setText(R.id.tvRank, String.valueOf(rankResponse.getRank()));
        baseViewHolder.setText(R.id.tvLevel, String.valueOf(rankResponse.getLevel()));
        baseViewHolder.setText(R.id.tvNick, rankResponse.getUsername());
        baseViewHolder.setText(R.id.tvCoin, String.valueOf(rankResponse.getCoinCount()));
        int coinCount = rankResponse.getCoinCount();
        baseViewHolder.setGone(R.id.viewDivider, true);
        showWithAnim(baseViewHolder.getView(R.id.viewDivider), coinCount, rankResponse);
    }

    private void showWithAnim(View view, int coinCount, RankResponse rankResponse) {
        if (getItemCount() > 0) {
            RankResponse item = getItem(0);
            int maxCount = item.getCoinCount();
            maxCount = maxCount == 0 ? 1000 : maxCount;
            int width = ScreenUtils.getScreenWidth();
            int distance = Float.valueOf(coinCount * 1.0F / maxCount * width).intValue();
            if (rankResponse.isShowAnim()) {
                rankResponse.setShowAnim(false);
                ValueAnimator animator = ValueAnimator.ofInt(0, distance);
                animator.setDuration(400);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Object animatedValue = animation.getAnimatedValue();
                        if (animatedValue instanceof Integer) {
                            int value = (Integer) animatedValue;
                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                            layoutParams.width = value;
                            view.requestLayout();
                        }
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }
                });
                animator.start();
            } else {
                view.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = distance;
                view.requestLayout();
            }
        }
    }
}
