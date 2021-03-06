package com.sly.baseframe.manager;

import android.app.Activity;
import android.os.Message;
import android.text.TextUtils;

import java.util.Stack;

public class AppJumpManager {
    private static int DELAY_TIME = 1000;
    private static Stack<Activity> activityStack;
    private static AppJumpManager instance;
    private static android.os.Handler mHandler = new BaseHandler();

    private AppJumpManager() {
    }

    /**
     * 单一实例
     */
    public static AppJumpManager getAppManager() {
        if (instance == null) {
            instance = new AppJumpManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void removeActivity() {
        Activity activity = activityStack.lastElement();
        removeActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                if (activity != null) {
                    activity.finish();
                    activity = null;
                }
            }
        }
    }

    /**
     * 判断指定类名的activity是否在任务中
     */
    public boolean containActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                if (activity != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (null != activity) {
                activity.finish();
                activity = null;
            }
        }
        activityStack.clear();
    }

    /**
     * 移除指定名字的activity
     */
    public void removeActivityFromName(String activityName) {
        if (activityStack == null || TextUtils.isEmpty(activityName)) {
            return;
        }
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityName.equals(activityStack.get(i).getClass().getSimpleName())) {
                activityStack.get(i).finish();
            }
        }
    }

    /**
     * 移除除了指定activity的所有activity
     */
    public void finishAllExcept(String activityName) {
        if (activityStack == null || TextUtils.isEmpty(activityName)) {
            return;
        }
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityName.equals(activityStack.get(i).getClass().getSimpleName())) {

            } else {
                activityStack.get(i).finish();
            }
        }
    }

    public void finishCurrentActivity(final Activity activity) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activityStack.remove(activity);
                activity.finish();
            }
        }, DELAY_TIME);
    }

    static class BaseHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {

        }
    }
}
