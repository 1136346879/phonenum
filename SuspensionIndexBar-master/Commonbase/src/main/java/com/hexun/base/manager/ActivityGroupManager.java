package com.hexun.base.manager;

import com.hexun.base.ui.BaseMvpActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yy on 2017/9/18.
 */

public class ActivityGroupManager {

    private ActivityGroupManager() {
    }

    private static List<BaseMvpActivity> activityList = new LinkedList<>();

    public static List<BaseMvpActivity> getActivityList() {
        return activityList;
    }

    public static void addActivityToList(BaseMvpActivity baseActivity) {
        activityList.add(baseActivity);
    }

    public static void removeActivityFromList(BaseMvpActivity baseActivity) {
        activityList.remove(baseActivity);
    }

    public static BaseMvpActivity getTopActivityInList() {
        if (!activityList.iterator().hasNext()) {
            return null;
        }
        return activityList.iterator().next();
    }

    public static void exitApp() {
        if (activityList != null && !activityList.isEmpty()) {
            for (BaseMvpActivity baseActivity : activityList) {
                if (!baseActivity.isFinishing()) {
                    baseActivity.finish();
                }
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
