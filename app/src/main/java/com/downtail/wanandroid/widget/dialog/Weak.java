package com.downtail.wanandroid.widget.dialog;

import android.content.DialogInterface;

/**
 * NursingWorker
 * Created by downtail on 2020/9/22
 */
public class Weak {

    public static WeakOnCancelListener proxy(DialogInterface.OnCancelListener real) {
        return new WeakOnCancelListener(real);
    }

    public static WeakOnDismissListener proxy(DialogInterface.OnDismissListener real) {
        return new WeakOnDismissListener(real);
    }

    public static WeakOnShowListener proxy(DialogInterface.OnShowListener real) {
        return new WeakOnShowListener(real);
    }
}
