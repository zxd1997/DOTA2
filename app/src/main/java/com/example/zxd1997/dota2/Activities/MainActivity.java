package com.example.zxd1997.dota2.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.zxd1997.dota2.Beans.Ability;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Fragments.HeroesFragment;
import com.example.zxd1997.dota2.Fragments.ItemsFragment;
import com.example.zxd1997.dota2.Fragments.MyFragment;
import com.example.zxd1997.dota2.Fragments.ProFragment;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final static int FINISHED = 8;
    public static Map<String, Hero> heroes;
    public static Map<String, String> ability_ids;
    public static Map<String, Ability> abilities;
    public static Map<String, Item> items;
    SharedPreferences sharedPreferences;
    ProgressDialog pd;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FINISHED: {
                    Update.readFromJson();
                    pd.dismiss();
                    break;
                }
            }
        }
    };
    TabFragmentAdapter tabFragmentAdapter;
    List<Fragment> fragments;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Update.setDensity(this, getApplication());
        MyApplication.add(this);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!getFileStreamPath("master.zip").exists()) {
            Update.updatezip(handler);
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Update");
            pd.setMessage("Updating");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        } else {
            Update.readFromJson();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragments = new ArrayList<Fragment>();
        fragments.add(MyFragment.newInstance());
        fragments.add(HeroesFragment.newInstance());
        fragments.add(ItemsFragment.newInstance());
        fragments.add(ProFragment.newInstance());
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(tabFragmentAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_disconnect) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", "");
            editor.commit();
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            Intent intent = new Intent(DISCONNECT);
            localBroadcastManager.sendBroadcast(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.exit();
    }
}
