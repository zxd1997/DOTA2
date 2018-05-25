package com.example.zxd1997.dota2.Utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;

public class Update {
    final static int HEROES = 0;
    static List<Hero> heroes = MainActivity.heroes;
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("hhhhhh", "handleMessage: " + msg.obj.toString());
            switch (msg.what) {
                case HEROES: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    heroes.clear();
//                    DataSupport.deleteAll("Heroes");
                    for (JsonElement e : jsonArray) {
                        Hero hero = new Gson().fromJson(e, Hero.class);
                        Log.d("id", "handleMessage: " + hero.getHero_id() + hero.getLocalized_name());
                        heroes.add(hero);
                        hero.save();
                    }
                    for (Hero i : heroes) {
                        Log.d("hero", "handleMessage: " + i.getHero_id() + " " + i.getLocalized_name());
                    }
                    break;
                }
            }
        }
    };

    public static void update_hero() {
        Okhttp.getFromService("https://api.opendota.com/api/heroes", handler, HEROES);
    }
}
