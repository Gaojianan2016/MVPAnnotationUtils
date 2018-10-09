package com.gjn.mvpannotationlibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author gjn
 * @time 2018/8/1 11:37
 */

public class ToastUtils {
    private static ToastUtils toastUtils = new ToastUtils();
    private static Toast toast;
    private static Context context;

    private ToastUtils() {
    }

    public static ToastUtils getInstance(Context c) {
        if (toast == null) {
            context = c;
            toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        return toastUtils;
    }

    public void showToast(String msg){
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public void showToast(String msg, int duration){
        if (context != null) {
            toast.setText(msg);
            toast.setDuration(duration);
            toast.show();
        }
    }

    public void showToast(String msg, int duration, int gravity){
        showToast(msg, duration, gravity, 0, 0);
    }

    public void showToast(String msg, int duration, int gravity, int xOffset, int yOffset){
        if (context != null) {
            toast.setText(msg);
            toast.setDuration(duration);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.show();
        }
    }

    public static void showToast(Context context, String msg){
        getInstance(context).showToast(msg);
    }

    public static void showToast(Context context, String msg, int duration){
        getInstance(context).showToast(msg, duration);
    }

    public static void showToast(Context context, String msg, int duration, int gravity){
        getInstance(context).showToast(msg, duration, gravity);
    }

    public static void showToast(Context context, String msg, int duration, int gravity, int xOffset, int yOffset){
        getInstance(context).showToast(msg, duration, gravity, xOffset, yOffset);
    }

}
