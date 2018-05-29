package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.zxd1997.dota2.Adapters.TabFragmentAdapter;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Fragments.DetailFragment;
import com.example.zxd1997.dota2.Fragments.EconomyFragment;
import com.example.zxd1997.dota2.Fragments.NoDetailFragment;
import com.example.zxd1997.dota2.Fragments.OverviewFragment;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.Okhttp;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {
    final static int MATCH = 5;
    long id;
    TabLayout tabLayout;
    private Match match = null;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("match", "handleMessage: " + msg.obj.toString());
            match = new Gson().fromJson(msg.obj.toString(), Match.class);
            Log.d("info", "handleMessage: " + match.getMatch_id() + " " + match.getRadiant_score() + " " + match.getDire_score() + " " + match.getReplay_salt());
            fragments.add(OverviewFragment.newInstance());
            if (match.getRadiant_xp_adv() == null) {
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_6)));
                fragments.add(NoDetailFragment.newInstance());
            } else {
                tabLayout.addTab(tabLayout.newTab().setText("Combat"));
                fragments.add(DetailFragment.newInstance());
                tabLayout.addTab(tabLayout.newTab().setText("Economy"));
                fragments.add(EconomyFragment.newInstance());
            }
            tabFragmentAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    };
    TabFragmentAdapter tabFragmentAdapter;
    List<Fragment> fragments = new ArrayList<>();
    ProgressBar progressBar;

    public Match getMatch() {
        return match;
    }
    private ViewPager mViewPager;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.add(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Intent intent = getIntent();
        if (intent == null) {
            startActivity(new Intent(MatchActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        id = intent.getLongExtra("id", -1);
        if (id == -1) {
            startActivity(new Intent(MatchActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        Log.d("matchid", "onCreate: " + id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        Okhttp.getFromService(getString(R.string.api) + getString(R.string.matches) + id, handler, MATCH);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_text_5)));
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
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
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
