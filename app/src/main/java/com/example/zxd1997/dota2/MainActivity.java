package com.example.zxd1997.dota2;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static int HEROES=0;
    SharedPreferences sharedPreferences;
    static List<Hero> heroes=new ArrayList<>();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d("hhhhhh", "handleMessage: "+msg.obj.toString());

            switch (msg.what){
                case HEROES:{
                    JsonParser parser=new JsonParser();
                    JsonArray jsonArray=parser.parse(msg.obj.toString()).getAsJsonArray();
                    heroes.clear();
//                    DataSupport.deleteAll("Heroes");
                    for (JsonElement e:jsonArray){
                        Hero hero=new Gson().fromJson(e,Hero.class);
                        Log.d("id", "handleMessage: "+hero.getHero_id()+hero.getLocalized_name());
                        heroes.add(hero);
                        hero.save();
                    }
                    for (Hero i:heroes){
                        Log.d("hero", "handleMessage: "+i.getHero_id()+" "+i.getLocalized_name());
                    }
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("stored",true);
                    editor.commit();
                    break;
                }
            }
        }
    };
    private ViewPager mViewPager;
    TabFragmentAdapter tabFragmentAdapter;
    List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean("stored",false)){
            Okhttp.getFromService("https://api.opendota.com/api/heroes",handler,HEROES);
        }else {
            heroes= DataSupport.findAll(Hero.class);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragments=new ArrayList<Fragment>();
        fragments.add(MyFragment.newInstance());
        fragments.add(HeroesFragment.newInstance());
        fragments.add(ItemsFragment.newInstance());
        fragments.add(ProFragment.newInstance());
        tabFragmentAdapter=new TabFragmentAdapter(getSupportFragmentManager(),fragments);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(tabFragmentAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id==R.id.action_disconnect){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("id","");
            editor.commit();
            fragments.remove(0);
            fragments.add(0,MyFragment.newInstance());
            tabFragmentAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }


}
