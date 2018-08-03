package com.gjn.mvpannotationlibrary.base;

import android.os.Bundle;

/**
 * @author gjn
 * @time 2018/8/1 11:34
 */

public interface IEvent {
    void showNextActivity(Class<?> cls);
    void showNextActivity(Class<?> cls, Bundle bundle);
    void toNextActivity(Class<?> cls);
    void toNextActivity(Class<?> cls, Bundle bundle);
    void showToast(String msg);
}
