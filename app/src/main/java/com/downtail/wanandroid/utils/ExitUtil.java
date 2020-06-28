package com.downtail.wanandroid.utils;

public class ExitUtil {

    private static long lastTime = 0;
    private static final long DURATION = 800;

    public static boolean exit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime <= DURATION) {
            return true;
        }
        lastTime = currentTime;
        return false;
    }
}
