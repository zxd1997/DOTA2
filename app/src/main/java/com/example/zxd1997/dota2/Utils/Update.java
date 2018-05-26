package com.example.zxd1997.dota2.Utils;

import android.os.Handler;
import android.util.Log;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Ability;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Update {
    public static void updatezip(final Handler handler) {
        Okhttp.getZip("https://github.com/zxd1997/dotaconstants/archive/master.zip", handler);
    }

    static public StringBuilder readfile(String filename) {
        StringBuilder s = new StringBuilder();
        try {
            FileInputStream fileInputStream = MyApplication.getContext().openFileInput(filename);
            byte[] bytes = new byte[4096];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                Log.d("hero", "readfile: " + new String(bytes, 0, len));
                s.append(new String(bytes, 0, len));
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void readFromJson() {
        StringBuilder heroes_json = readfile("hero_names.json");
        MainActivity.heroes = new Gson().fromJson(heroes_json.toString(), new TypeToken<Map<String, Hero>>() {
        }.getType());
        StringBuilder ability_id_json = readfile("ability_ids.json");
        MainActivity.ability_ids = new Gson().fromJson(ability_id_json.toString(), new TypeToken<Map<String, String>>() {
        }.getType());
        StringBuilder items_json = readfile("items.json");
        MainActivity.items = new Gson().fromJson(items_json.toString(), new TypeToken<Map<String, Item>>() {
        }.getType());
        StringBuilder ability_json = readfile("abilities.json");
        MainActivity.abilities = new Gson().fromJson(ability_json.toString(), new TypeToken<Map<String, Ability>>() {
        }.getType());
        for (Map.Entry<String, Ability> e : MainActivity.abilities.entrySet()) {
            Log.d("ability", "readFromJson: " + e.getKey() + " " + e.getValue().getDname());
        }
    }
}
