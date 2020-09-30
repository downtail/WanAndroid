package com.downtail.wanandroid.widget.dialog;

import android.content.DialogInterface;

import java.lang.ref.WeakReference;

/**
 * NursingWorker
 * Created by downtail on 2020/9/22
 */
public class WeakOnShowListener implements DialogInterface.OnShowListener {

    private WeakReference<DialogInterface.OnShowListener> mRef;

    public WeakOnShowListener(DialogInterface.OnShowListener real) {
        this.mRef = new WeakReference<>(real);
    }

    @Override
    public void onShow(DialogInterface dialog) {
        DialogInterface.OnShowListener real = mRef.get();
        if (real != null) {
            real.onShow(dialog);
        }
    }
}
