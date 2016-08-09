package io.chgocn.plug.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Application Activity Manager
 * Activity manage and exit the application
 *
 * @author liux (http://my.oschina.net/liux)
 * @author chgocn (chgocn@gmail.com)
 * @version 1.1
 * @since 2012-3-21
 * @update 2014-07-22
 */
public final class AppManager {

    private static Stack<Activity> ACTIVITY_STACK;
    private static AppManager INSTANCE;

    private AppManager() {
    }

    /**
     * support a method to get a INSTANCE for the outside
     */
    public static AppManager getAppManager() {
        if (null == INSTANCE) {
            INSTANCE = new AppManager();
        }
        return INSTANCE;
    }

    /**
     * add the Activity into stack
     */
    public synchronized void addActivity(Activity activity) {
        if (ACTIVITY_STACK == null) {
            ACTIVITY_STACK = new Stack<Activity>();
        }
        ACTIVITY_STACK.add(activity);
    }

    /**
     * get the current Activity(the last push to stack)
     */
    public synchronized Activity currentActivity() {
        return ACTIVITY_STACK.lastElement();
    }

    /**
     * end the current Activity(the last push to stack)
     */
    public synchronized void finishActivity() {
        try {
            Activity activity = ACTIVITY_STACK.lastElement();
            finishActivity(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * end the Activity with class INSTANCE
     * remove the activity from stack and finish it
     */
    public synchronized void finishActivity(Activity activity) {
        if (activity != null) {
            ACTIVITY_STACK.remove(activity);
            activity.finish();
            //activity = null;
        }
    }

    /**
     * end the Activity with class name
     */
    public synchronized void finishActivity(Class<?> cls) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(ACTIVITY_STACK);

        for (Activity activity : tempActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * end the Activity with class name
     */
    public synchronized void finishActivity(Class<?> cls, int resultCode) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(ACTIVITY_STACK);

        for (Activity activity : tempActivityStack) {
            if (activity.getClass().equals(cls)) {
                activity.setResult(resultCode);
                finishActivity(activity);
            }
        }
    }

    /**
     * end all the activities except Activity with class name
     */
    public synchronized void finishAllActivityExcept(Class<?> cls) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(ACTIVITY_STACK);

        for (Activity activity : tempActivityStack) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * end all the activity
     */
    public synchronized void finishAllActivity() {
        for (int i = 0, size = ACTIVITY_STACK.size(); i < size; i++) {
            if (null != ACTIVITY_STACK.get(i)) {
                ACTIVITY_STACK.get(i).finish();
            }
        }
        ACTIVITY_STACK.clear();
    }

    /**
     * exit the application
     */
    public void exitApp() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
            Log.e("AppManager: " + e.getMessage());
        }
    }
}
