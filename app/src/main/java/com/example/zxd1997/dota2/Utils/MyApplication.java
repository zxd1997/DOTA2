package com.example.zxd1997.dota2.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {
    private static Context context;
    private static List<Activity> activities = new LinkedList<>();

    public static Context getContext() {
        return context;
    }

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
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true).build();
        Fresco.initialize(context, config);
        super.onCreate();
    }
}
