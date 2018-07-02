package com.example.zxd1997.dota2.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.zxd1997.dota2.Adapters.TabFragmentAdapter;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Fragments.Match.DetailFragment;
import com.example.zxd1997.dota2.Fragments.Match.EconomyFragment;
import com.example.zxd1997.dota2.Fragments.Match.LogsFragment;
import com.example.zxd1997.dota2.Fragments.Match.NoDetailFragment;
import com.example.zxd1997.dota2.Fragments.Match.OverviewFragment;
import com.example.zxd1997.dota2.Fragments.Match.PurchaseAndCastFragment;
import com.example.zxd1997.dota2.Fragments.Match.VisionFragment;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Update;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchActivity extends AppCompatActivity {
    private final static int MATCH = 5;
    private final static int LOADED = 1;
    private final static int LOADEDALL = 6;
    private TabLayout tabLayout;
    private TabFragmentAdapter tabFragmentAdapter;
    private final List<Fragment> fragments = new ArrayList<>();
    private ProgressBar progressBar;
    private Match match = null;
    private int count = 0;
    private boolean parsed = false;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MATCH: {
                    match = new Gson().fromJson(msg.obj.toString(), Match.class);
//            Log.d("info", "handleMessage: " + match.getMatch_id() + " " + match.getRadiant_score() + " " + match.getDire_score() + " " + match.getReplay_salt());
                    fragments.add(OverviewFragment.newInstance());
                    IntentFilter intentFilter = new IntentFilter("loaded");
                    Receiver receiver = new Receiver();
                    LocalBroadcastManager.getInstance(Objects.requireNonNull(getApplicationContext())).registerReceiver(receiver, intentFilter);
                    if (match.getRadiant_xp_adv() == null) {
                        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_6)));
                        fragments.add(NoDetailFragment.newInstance());
                        tabFragmentAdapter.notifyDataSetChanged();
                        mViewPager.setOffscreenPageLimit(fragments.size());
                        tabFragmentAdapter.notifyDataSetChanged();
                    } else {
                        parsed = true;
                        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_7)));
                        fragments.add(DetailFragment.newInstance());
                        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_8)));
                        fragments.add(EconomyFragment.newInstance());
                        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_9)));
                        fragments.add(PurchaseAndCastFragment.newInstance());
                        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_10)));
                        fragments.add(VisionFragment.newInstance());
                        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_11)));
                        fragments.add(LogsFragment.newInstance());
                        tabFragmentAdapter.notifyDataSetChanged();
                        mViewPager.setOffscreenPageLimit(fragments.size());
                    }
                    break;
                }
                case LOADED: {
                    mViewPager.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    break;
                }
                case LOADEDALL: {
                    mViewPager.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    break;
                }
            }

            return true;
        }
    });

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            count++;
            if (count == 1 && !parsed) {
                Message message = new Message();
                message.what = LOADED;
                handler.sendMessage(message);
            }
            if (count == 6 && parsed) {
                Message message = new Message();
                message.what = LOADEDALL;
                handler.sendMessage(message);
            }
        }
    }
    private ViewPager mViewPager;

    public Match getMatch() {
        return match;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ViewServer.get(this).addWindow(this);
        Update.setDensity(this, getApplication());
        MyApplication.add(this);
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#FFCC0000"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        setContentView(R.layout.activity_match);
        Intent intent = getIntent();
        if (intent == null) {
            startActivity(new Intent(MatchActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        assert intent != null;
        long id = intent.getLongExtra("id", -1);
        if (id == -1) {
            startActivity(new Intent(MatchActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(MyApplication.getContext().getString(R.string.match) + "：" + id);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        OKhttp.getFromService(getString(R.string.api) + getString(R.string.matches) + id, handler, MATCH);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_5)));
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(tabFragmentAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MatchActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        ViewServer.get(this).setFocusedWindow(this);
//    }

    @Override
    protected void onDestroy() {
        int i = 0;
        for (Fragment fragment : fragments) {
            tabFragmentAdapter.myDestroyItem(mViewPager, i++, fragment);
        }
        fragments.clear();
        ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
        System.gc();
        System.runFinalization();
//        ViewServer.get(this).removeWindow(this);
        super.onDestroy();
    }
}
