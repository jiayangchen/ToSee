package com.jianshu_.base;

import android.os.Handler;

/**
 * Created by jmw on 2016/3/21.
 */
public class UIUtil {

    private static Handler mHandler = new Handler();

    public static void post(Runnable task) {
        mHandler.post(task);
    }

    public static void postDelayed(Runnable task, long delayed) {
        mHandler.postDelayed(task, delayed);
    }

    public static void removeCallbacks(Runnable task) {
        mHandler.removeCallbacks(task);
    }

    public static void init() {

    }
}
