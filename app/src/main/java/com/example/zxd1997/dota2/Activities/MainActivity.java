package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zxd1997.dota2.Adapters.TabFragmentAdapter;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Fragments.HeroesFragment;
import com.example.zxd1997.dota2.Fragments.ItemsFragment;
import com.example.zxd1997.dota2.Fragments.MyFragment;
import com.example.zxd1997.dota2.Fragments.ProFragment;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Update;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static List<Hero> heroes = new ArrayList<>();

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
            Update.update_hero();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("stored", true);
            editor.commit();
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
        final String DISCONNECT = "disconnect from id";
        if (id == R.id.action_settings) {
            return true;
        }
        if (id==R.id.action_disconnect){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("id","");
            editor.commit();
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            Intent intent = new Intent(DISCONNECT);
            localBroadcastManager.sendBroadcast(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
