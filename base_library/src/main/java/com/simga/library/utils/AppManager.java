package com.simga.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.Stack;

/**
 * Author : zhouyx
 * Date   : 2015/12/11
 * Activity管理类
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager get() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 从堆栈中移除该Activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().getSimpleName().equals(cls.getSimpleName())) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有除目标外的Activity
     */
    public void finishActivitiesWithoutTarget(Class<?> cls) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(activityStack);
        for (Activity activity : tempActivityStack) {
            if (!activity.getClass().getSimpleName().equals(cls.getSimpleName())) {
                activity.finish();
            }
        }
        tempActivityStack.clear();
    }

    /**
     * 结束所有Activity 除了cls和cls2这个主activity之外
     */
    public void finishAllActivityExceptParamActivity(Class<?> cls, Class<?> cls2) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity != null &&
                    !activity.getClass().getSimpleName().equals(cls.getSimpleName())
                    && !activity.getClass().getSimpleName().equals(cls2.getSimpleName())) {
                activity.finish();
            }
        }
    }

    //true:当前进程是主进程 false:当前进程不是主进程
    public boolean isUIProcess(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    //app是否在前台
    private boolean isAppForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName());
    }

    /**
     * 判断某个页面是不是在第一个页面
     */
    private boolean isActivityTop(Context context, String className) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String activityName = am.getRunningTasks(1).get(0).topActivity.getClassName();
        try {
            Class classObject = Class.forName(activityName);
            return classObject.getSimpleName().equals(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
