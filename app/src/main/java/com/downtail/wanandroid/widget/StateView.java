package com.downtail.wanandroid.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.downtail.wanandroid.R;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;

public class StateView extends RelativeLayout {

    private static final String CONTENT_VIEW_TAG = "ContentView";
    private static final String STATE_VIEW_TAG = "StateView";
    public static final int EMPTY = 0;
    public static final int LOAD = 1;
    public static final int ERROR = 2;
    public static final int CONTENT = 3;
    private static SparseIntArray mCurrency = new SparseIntArray();
    private SparseIntArray mPresent = new SparseIntArray();
    private SparseArray<View> cacheViews = new SparseArray<>();
    private int offSet;
    private OnStateControllerListener onStateControllerListener;

    static {
        setItemType(EMPTY, R.layout.layout_empty);
        setItemType(ERROR, R.layout.layout_error);
        setItemType(LOAD, R.layout.layout_load);
    }

    public static void setItemType(@IntRange(from = EMPTY, to = ERROR) int state, @LayoutRes int layoutId) {
        mCurrency.put(state, layoutId);
    }

    public StateView addItemType(@IntRange(from = EMPTY, to = ERROR) int state, @LayoutRes int layoutId) {
        mPresent.put(state, layoutId);
        return this;
    }

    public StateView(Context context) {
        this(context, 0);
    }

    public StateView(Context context, int offSet) {
        this(context, null, offSet);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int offSet) {
        super(context, attrs);
        this.offSet = offSet;
    }

    public static StateView init(Activity activity) {
        return init(activity, 0);
    }

    public static StateView init(Activity activity, int offSet) {
        if (activity == null) {
            throw new NullPointerException("activity can't be null");
        }
        View contentView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        return init(contentView, offSet);
    }

    public static View initWithFragment(View contentView) {
        return initWithFragment(contentView, 0);
    }

    public static View initWithFragment(View contentView, int offSet) {
        if (contentView == null) {
            throw new NullPointerException("contentView can't be null");
        }
        View view = contentView.findViewWithTag(CONTENT_VIEW_TAG);
        if (view == null) {
            throw new IllegalStateException("contentView must has tag");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            int index = parent.indexOfChild(view);
            parent.removeView(view);
            StateView stateView = new StateView(view.getContext(), offSet);
            stateView.setTag(STATE_VIEW_TAG);
            stateView.addView(view, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setVisibility(VISIBLE);
            parent.addView(stateView, index, layoutParams);
            return parent;
        } else {
            StateView stateView = new StateView(contentView.getContext(), offSet);
            stateView.setTag(STATE_VIEW_TAG);
            stateView.addView(contentView, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            contentView.setVisibility(VISIBLE);
            return stateView;
        }
    }

    public static StateView init(View contentView) {
        return init(contentView, 0);
    }

    public static StateView init(View contentView, int offSet) {
        if (contentView == null) {
            throw new NullPointerException("contentView can't be null");
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent == null) {
            throw new NullPointerException("parentView can't be null");
        }

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        int index = parent.indexOfChild(contentView);
        parent.removeView(contentView);
        StateView stateView = new StateView(contentView.getContext(), offSet);
        stateView.addView(contentView, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setVisibility(VISIBLE);
        parent.addView(stateView, index, layoutParams);
        return stateView;
    }

    public void setState(@IntRange(from = EMPTY, to = CONTENT) int state) {
        if (state != CONTENT) {
            View view = cacheViews.get(state);
            if (view == null) {
                if (mPresent.get(state) == 0 && mCurrency.get(state) == 0) {
                    throw new IllegalArgumentException("resource can't be zero");
                }
                int viewId = mPresent.get(state) != 0 ? mPresent.get(state) : mCurrency.get(state);
                if (viewId != 0) {
                    view = LayoutInflater.from(getContext()).inflate(viewId, this, false);
                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(0, offSet, 0, 0);
                    addView(view, getChildCount(), layoutParams);
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
            if (key != CONTENT) {
                View view = cacheViews.valueAt(i);
                if (view != null) {
                    view.setVisibility(key == state ? VISIBLE : GONE);
                }
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public StateView setOnStateControllerListener(OnStateControllerListener onStateControllerListener) {
        this.onStateControllerListener = onStateControllerListener;
        return this;
    }

    public interface OnStateControllerListener {
        void onStateController(View itemView, int itemType);
    }
}
