package com.downtail.wanandroid.widget;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;


import com.downtail.wanandroid.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class StatePlus {

    private static final String CONTENT_VIEW_TAG = "ContentView";
    public static final int DATA = 0;
    public static final int LOAD = 1;
    public static final int EMPTY = 2;
    public static final int ERROR = 3;

    public ViewGroup mParent;
    private static SparseIntArray mCurrency = new SparseIntArray();
    private SparseIntArray mPresent = new SparseIntArray();
    private SparseArray<View> cacheViews = new SparseArray<>();
    private OnStateControllerListener onStateControllerListener;

    static {
        setItemType(EMPTY, R.layout.layout_empty);
        setItemType(ERROR, R.layout.layout_error);
        setItemType(LOAD, R.layout.layout_load);
    }

    public static void setItemType(@IntRange(from = LOAD, to = ERROR) int state, @LayoutRes int layoutId) {
        mCurrency.put(state, layoutId);
    }

    public void addItemType(@IntRange(from = LOAD, to = ERROR) int state, @LayoutRes int layoutId) {
        mPresent.put(state, layoutId);
    }

    public View init(View contentView) {
        return init(contentView, 0);
    }

    public View init(View contentView, int offSet) {
        if (contentView == null) {
            throw new NullPointerException("contentView can't be null");
        }
        View view = contentView.findViewWithTag(CONTENT_VIEW_TAG);
        if (view == null) {
            throw new IllegalStateException("contentView must has tag");
        }
        mParent = (ViewGroup) view.getParent();
        if (mParent == null) {
            mParent = new FrameLayout(contentView.getContext());
            mParent.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, offSet, 0, 0);
            mParent.addView(contentView, 0, layoutParams);
        }
        return mParent;
    }

    public void setState(@IntRange(from = DATA, to = ERROR) int state) {
        setState(state, 0);
    }

    public synchronized void setState(@IntRange(from = DATA, to = ERROR) int state, int offSet) {
        if (state != DATA) {
            View view = cacheViews.get(state);
            if (view == null) {
                int targetLayoutId = mPresent.get(state, View.NO_ID);
                if (targetLayoutId == View.NO_ID && mCurrency.get(state, View.NO_ID) == View.NO_ID) {
                    //throw new IllegalArgumentException("resource can't be zero");
                    return;
                }
                int viewId = targetLayoutId != View.NO_ID ? targetLayoutId : mCurrency.get(state, View.NO_ID);
                if (viewId != View.NO_ID && mParent != null) {
                    view = LayoutInflater.from(mParent.getContext()).inflate(viewId, mParent, false);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(0, offSet, 0, 0);
                    mParent.addView(view, 1, layoutParams);
                    cacheViews.put(state, view);
                    view.setVisibility(VISIBLE);
                    if (onStateControllerListener != null) {
                        onStateControllerListener.onStateController(view, state);
                    }
                }
            }
        }
        int size = cacheViews.size();
        for (int i = 0; i < size; i++) {
            int key = cacheViews.keyAt(i);
            if (key != DATA) {
                View view = cacheViews.valueAt(i);
                if (view != null) {
                    view.setVisibility(key == state ? VISIBLE : GONE);
                }
            }
        }
    }

    public void setOnStateControllerListener(OnStateControllerListener onStateControllerListener) {
        this.onStateControllerListener = onStateControllerListener;
    }

    public interface OnStateControllerListener {
        void onStateController(View itemView, int itemType);
    }
}
