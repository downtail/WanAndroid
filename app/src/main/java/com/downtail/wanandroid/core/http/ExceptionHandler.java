package com.downtail.wanandroid.core.http;

import com.downtail.wanandroid.base.mvp.BaseContract;


public class ExceptionHandler {

    public static void handleException(BaseContract.BaseView view, Throwable e) {
        if (e instanceof ServerException) {
            ServerException err = (ServerException) e;
            int errCode = err.getErrCode();
            if (errCode == -1001) {
                view.showContent();
                view.jumpToLogin();
            } else if (errCode == -2) {
                view.showEmpty();
            } else if (errCode == -1) {

            }
            view.toast(err.getErrMsg());
        } else {
//            if (e instanceof NullPointerException) {
//                return;
//            }
            view.toast(e.getMessage());
        }
    }
}
