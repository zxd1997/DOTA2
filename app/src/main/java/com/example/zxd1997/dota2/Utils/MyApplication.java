package com.example.zxd1997.dota2.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static Context context;
    private static final List<Activity> activities = new LinkedList<>();

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
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(context, new OkHttpClient.Builder().build())
                .setDownsampleEnabled(true)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
//                .setBitmapsConfig(Bitmap.Config.ARGB_4444)
                .build();
        Fresco.initialize(context, config);
        super.onCreate();
    }
}
