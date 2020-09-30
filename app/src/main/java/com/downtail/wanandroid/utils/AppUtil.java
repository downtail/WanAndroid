package com.downtail.wanandroid.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * NursingWorker
 * Created by downtail on 2020/9/3
 */
public class AppUtil {

    private Stack<WeakReference<Activity>> mActivityStack;

    private AppUtil() {
        mActivityStack = new Stack<>();
    }

    private static class Inner {
        static final AppUtil mInstance = new AppUtil();
    }

    public static AppUtil getInstance() {
        return Inner.mInstance;
    }

    public void push(Activity activity) {
        mActivityStack.push(new WeakReference<>(activity));
    }

    public void pop(Activity target) {
        for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
            WeakReference<Activity> mRef = it.next();
            Activity activity = mRef.get();
            if (target == activity && activity != null) {
                activity.finish();
                mActivityStack.remove(mRef);
                return;
            }
        }
    }

    public Activity peek() {
        if (!mActivityStack.isEmpty()) {
            WeakReference<Activity> mRef = mActivityStack.peek();
            return mRef.get();
        }
        return null;
    }

    public void finishAllActivity() {
        for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
            WeakReference<Activity> mRef = it.next();
            Activity activity = mRef.get();
            if (activity != null) {
                activity.finish();
            }
        }
        mActivityStack.clear();
    }

    public void exitApp() {
        try {
            finishAllActivity();
            // 退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            // 从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
