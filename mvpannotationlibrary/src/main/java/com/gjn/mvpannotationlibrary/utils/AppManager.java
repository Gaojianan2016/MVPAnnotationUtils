package com.gjn.mvpannotationlibrary.utils;

import android.app.Activity;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gjn
 * @time 2018/7/25 17:42
 */

public class AppManager {
    private static CopyOnWriteArrayList<Activity> mActivities = new CopyOnWriteArrayList<>();

    private static AppManager appManager;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    public static CopyOnWriteArrayList<Activity> getActivities() {
        return mActivities;
    }

    public int getActivityItemCount() {
        return mActivities.size();
    }

    public Activity getActivityItem(int position) {
        return mActivities.get(position);
    }

    public void addActivity(Activity activity) {
        MvpLog.d("push " + activity.getClass().getSimpleName());
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            MvpLog.d("remove " + activity.getClass().getSimpleName());
            mActivities.remove(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void finishActivity(Class cls) {
        for (Activity activity : mActivities) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        for (Activity activity : mActivities) {
            finishActivity(activity);
        }
    }
}
