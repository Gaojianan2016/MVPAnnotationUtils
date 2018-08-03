package com.gjn.mvpannotationlibrary.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author gjn
 * @time 2018/7/27 14:26
 */

public class ViewUtils {

    public static void checkParent(View view){
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }
}
