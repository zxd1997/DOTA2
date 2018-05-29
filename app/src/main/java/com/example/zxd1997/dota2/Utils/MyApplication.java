package com.example.zxd1997.dota2.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    private static List<Activity> activities = new LinkedList<Activity>();
    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static void add(Activity activity) {
        activities.add(activity);
    }

    public static void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }
}
