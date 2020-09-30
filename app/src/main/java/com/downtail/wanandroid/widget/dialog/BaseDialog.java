package com.downtail.wanandroid.widget.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.downtail.wanandroid.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialog extends DialogFragment {

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new WeakDialog(requireContext(), getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(enableCancelable());
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setGravity(getGravity());
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = getAlpha();
                lp.dimAmount = getDim();
                window.setAttributes(lp);
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        }
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initEvents();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                if (isFullScreen()) {
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, getContentHeight(window));
                } else {
                    window.setLayout(getAvailableWidth(), getAvailableHeight());
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int getLayout();

    protected abstract void initEvents();

    protected boolean enableCancelable() {
        return true;
    }

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected float getAlpha() {
        return 1.0f;
    }

    protected float getDim() {
        return 0.5f;
    }

    protected boolean isFullScreen() {
        return false;
    }

    protected int getAvailableWidth() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                return Float.valueOf(getContentWidth(window) * getWidthRatio()).intValue();
            }
        }
        return -1;
    }

    protected int getAvailableHeight() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                return Float.valueOf(getContentHeight(window) * getHeightRatio()).intValue();
            }
        }
        return -2;
    }

    protected float getWidthRatio() {
        return 0.7f;
    }

    protected float getHeightRatio() {
        return 0.7f;
    }

    private int getContentWidth(Window window) {
        Rect outRect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.width();
    }

    private int getContentHeight(Window window) {
        Rect outRect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.height();
    }
}
