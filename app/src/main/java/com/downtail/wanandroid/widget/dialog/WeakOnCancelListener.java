package com.downtail.wanandroid.widget.dialog;

import android.content.DialogInterface;

import java.lang.ref.WeakReference;

/**
 * NursingWorker
 * Created by downtail on 2020/9/22
 */
public class WeakOnCancelListener implements DialogInterface.OnCancelListener {

    private WeakReference<DialogInterface.OnCancelListener> mRef;

    public WeakOnCancelListener(DialogInterface.OnCancelListener real) {
        this.mRef = new WeakReference<>(real);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        DialogInterface.OnCancelListener real = mRef.get();
        if (real != null) {
            real.onCancel(dialog);
        }
    }
}
