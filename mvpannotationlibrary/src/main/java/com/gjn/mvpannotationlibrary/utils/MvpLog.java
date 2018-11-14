package com.gjn.mvpannotationlibrary.utils;

/**
 * @author gjn
 * @time 2018/10/9 15:02
 */

public class MvpLog {
    private static final String TAG = "MvpLog";

    public static boolean isDebug = true;

    public static void v(String tag, String msg) {
        if (isDebug) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            android.util.Log.e(tag, msg, tr);
        }
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        e(TAG, msg, tr);
    }
}
