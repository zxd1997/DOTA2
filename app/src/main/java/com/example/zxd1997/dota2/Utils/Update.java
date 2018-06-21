package com.example.zxd1997.dota2.Utils;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Ability;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class Update {
    private static final String TAG = "density";

    public static void setDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        final float targetDensity = (float) displayMetrics.widthPixels / (float) 411;
        final float targetScaledDensity = targetDensity * (displayMetrics.scaledDensity / displayMetrics.density);
        final int targetDensityDpi = (int) (160 * targetDensity);
        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = (float) (targetDensity * 0.87);
        displayMetrics.densityDpi = targetDensityDpi;
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = (float) (targetDensity * 0.87);
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    public static void update_zip(final Handler handler) {
        OKhttp.getZip(MyApplication.getContext().getString(R.string.zip), handler);
    }

    private static StringBuilder read_file(String filename) {
        StringBuilder s = new StringBuilder();
        try {
            FileInputStream fileInputStream = MyApplication.getContext().openFileInput(filename);
            byte[] bytes = new byte[4096];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                s.append(new String(bytes, 0, len));
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void readFromJson() {
        StringBuilder heroes_json = read_file("hero_names.json");
        MainActivity.heroes = new Gson().fromJson(heroes_json.toString(), new TypeToken<Map<String, Hero>>() {
        }.getType());
        StringBuilder ability_id_json = read_file("ability_ids.json");
        MainActivity.ability_ids = new Gson().fromJson(ability_id_json.toString(), new TypeToken<Map<String, String>>() {
        }.getType());
        StringBuilder items_json = read_file("items.json");
        MainActivity.items = new Gson().fromJson(items_json.toString(), new TypeToken<Map<String, Item>>() {
        }.getType());
        StringBuilder ability_json = read_file("abilities.json");
        MainActivity.abilities = new Gson().fromJson(ability_json.toString(), new TypeToken<Map<String, Ability>>() {
        }.getType());
    }
}
