package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Update;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeerActivity extends AppCompatActivity {
    final int NUMBER = 20;
    final int MATCHES = 20;
    final int UPDATE = 21;
    SwipeRefreshLayout swipeRefreshLayout;
    MatchesAdapter matchesAdapter;
    List<MatchHero> matches = new ArrayList<>();
    int offset = 0;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MATCHES: {
                    matches.clear();
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                        recentMatch.setType(1);
//                        if (recentMatch.getGame_mode() != 19)
                        matches.add(recentMatch);
                    }
                    matchesAdapter.setHasfoot(true);
                    matchesAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                }
                case UPDATE: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    if (jsonArray.size() == 0) {
                        matchesAdapter.setHasfoot(false);
                    }
                    int t = matches.size();
                    for (JsonElement e : jsonArray) {
                        RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                        recentMatch.setType(1);
//                        if (recentMatch.getGame_mode() != 19)
                        matches.add(recentMatch);
                    }
                    matchesAdapter.notifyItemRangeChanged(t, matchesAdapter.getItemCount());
                }
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Update.setDensity(this, getApplication());
        getWindow().setStatusBarColor(Color.parseColor("#FFCC0000"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        setContentView(R.layout.activity_peer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        final long with_id = intent.getLongExtra("id", 0);
        String name = intent.getStringExtra("name");
        if (with_id == 0) {
            Intent intent1 = new Intent(PeerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        getSupportActionBar().setTitle(getString(R.string.matches_with) + " " + name);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sharedPreferences.getString("id", "");
        RecyclerView recyclerView = findViewById(R.id.player_matches);
        swipeRefreshLayout = findViewById(R.id.swipe_player);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        matchesAdapter = new MatchesAdapter(this, matches);
        matchesAdapter.setHasfoot(false);
        recyclerView.setAdapter(matchesAdapter);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                matchesAdapter.setHasfoot(false);
                swipeRefreshLayout.setRefreshing(true);
                offset = 0;
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches" + "?offset=" + offset + "&limit=" + NUMBER + "&included_account_id=" + with_id, handler, MATCHES);
            }
        });
        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches" + "?offset=" + offset + "&limit=" + NUMBER + "&included_account_id=" + with_id, handler, MATCHES);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && matchesAdapter.isHasfoot()) {
                    Fresco.getImagePipeline().resume();
                    if (linearLayoutManager.findLastVisibleItemPosition() == matchesAdapter.getItemCount() - 1) {
                        offset += NUMBER;
                        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches" + "?offset=" + offset + "&limit=" + NUMBER + "&included_account_id=" + with_id, handler, UPDATE);
                    }
                } else Fresco.getImagePipeline().pause();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(PeerActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
