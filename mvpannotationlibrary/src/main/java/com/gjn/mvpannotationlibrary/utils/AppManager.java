package com.gjn.mvpannotationlibrary.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * @author gjn
 * @time 2018/7/25 17:42
 */

public class AppManager {
    private static Stack<Activity> mStack = new Stack<>();
    private static AppManager appManager;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class){
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    public int getActivityItemCount(){
        return mStack.size();
    }

    public Activity getActivityItem(int position){
        return mStack.get(position);
    }

    public void addActivity(Activity activity){
        MvpLog.d("push " + activity.getClass().getSimpleName());
        mStack.push(activity);
    }

    public void removeActivity(Activity activity){
        MvpLog.d("remove " + activity.getClass().getSimpleName());
        mStack.remove(activity);
    }

    public void finishActivity(Activity activity){
        if (activity != null) {
            removeActivity(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void finishActivity(Class cls){
        for (Activity activity : mStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity(){
        for (Activity activity : mStack) {
            finishActivity(activity);
        }
    }
}
