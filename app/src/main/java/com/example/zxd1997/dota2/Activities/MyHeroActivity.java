package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
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
import com.example.zxd1997.dota2.Beans.Total;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Update;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyHeroActivity extends AppCompatActivity {
    private final int WL = 30;
    private final int TOTAL = 31;
    private final int HERO_MARCHES = 32;
    private final int HERO_MARCHES_UPDATE = 33;
    private final int NUMBER = 20;
    MatchesAdapter.HeroCard heroCard;
    RecyclerView recyclerView;
    MatchesAdapter matchesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<MatchHero> matches = new ArrayList<>();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WL: {
                    heroCard = (MatchesAdapter.HeroCard) recyclerView.getChildViewHolder(recyclerView.getChildAt(0));
                    com.example.zxd1997.dota2.Beans.WL wl = new Gson().fromJson(msg.obj.toString(), com.example.zxd1997.dota2.Beans.WL.class);
                    wl.setWinrate();
                    heroCard.total.setText(String.valueOf(wl.getWin() + wl.getLose()));
                    DecimalFormat df = new DecimalFormat("0.00%");
                    heroCard.winrate.setText(df.format(wl.getWinrate()));
                    break;
                }
                case TOTAL: {
                    heroCard = (MatchesAdapter.HeroCard) recyclerView.getChildViewHolder(recyclerView.getLayoutManager().findViewByPosition(0));
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    List<Total> totals = new ArrayList<>();
                    for (JsonElement e : jsonArray) {
                        Total total = new Gson().fromJson(e, Total.class);
                        totals.add(total);
                    }
                    DecimalFormat df = new DecimalFormat("0.0");
                    DecimalFormat df1 = new DecimalFormat("0.00");
                    heroCard.kill.setText(df.format((float) totals.get(0).getSum() / totals.get(0).getN()));
                    heroCard.death.setText(df.format((float) totals.get(1).getSum() / totals.get(1).getN()));
                    heroCard.assists.setText(df.format((float) totals.get(2).getSum() / totals.get(2).getN()));
                    heroCard.kda.setText(df1.format((float) totals.get(3).getSum() / totals.get(3).getN()));
                    heroCard.gpm.setText(df.format((float) totals.get(4).getSum() / totals.get(4).getN()));
                    heroCard.xpm.setText(df.format((float) totals.get(5).getSum() / totals.get(5).getN()));
                    heroCard.lh.setText(df.format((float) totals.get(6).getSum() / totals.get(6).getN()));
                    heroCard.dn.setText(df.format((float) totals.get(7).getSum() / totals.get(7).getN()));
                    heroCard.damage.setText(df.format((float) totals.get(11).getSum() / totals.get(11).getN()));
                    heroCard.td.setText(df.format((float) totals.get(12).getSum() / totals.get(12).getN()));
                    heroCard.hh.setText(df.format((float) totals.get(13).getSum() / totals.get(13).getN()));
                    break;
                }
                case HERO_MARCHES: {
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
                case HERO_MARCHES_UPDATE: {
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
                    break;
                }
            }
            return true;
        }
    });
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Update.setDensity(this, getApplication());
        getWindow().setStatusBarColor(Color.parseColor("#FFCC0000"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        setContentView(R.layout.activity_my_hero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        final int hero_id = intent.getIntExtra("id", 0);
        if (hero_id == 0) {
            Intent intent1 = new Intent(MyHeroActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        } else {
            TypedArray typedArray = getResources().obtainTypedArray(R.array.heroes);
            setTitle(typedArray.getString(hero_id));
            typedArray.recycle();
            recyclerView = findViewById(R.id.player_matches);
            swipeRefreshLayout = findViewById(R.id.swipe_player);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            MatchHero m = new MatchHero();
            m.setHero_id(hero_id);
            m.setType(7);
            matches.add(m);
            m = new MatchHero();
            m.setHero_id(hero_id);
            m.setType(5);
            m.setTitle(getString(R.string.moth));
            matches.add(m);
            matchesAdapter = new MatchesAdapter(this, matches);
            matchesAdapter.setHasfoot(true);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            final String id = sharedPreferences.getString("id", "");
            swipeRefreshLayout.setRefreshing(true);
            recyclerView.setAdapter(matchesAdapter);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    matches.clear();
                    MatchHero m = new MatchHero();
                    m.setHero_id(hero_id);
                    m.setType(7);
                    matches.add(m);
                    m = new MatchHero();
                    m.setHero_id(hero_id);
                    m.setType(5);
                    m.setTitle("Matches on this Hero");
                    matchesAdapter.setHasfoot(true);
                    swipeRefreshLayout.setRefreshing(true);
                    offset = 0;
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl?hero_id=" + hero_id, handler, WL);
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/totals?hero_id=" + hero_id, handler, TOTAL);
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches?hero_id=" + hero_id + "&offset=" + offset + "&limit=" + NUMBER, handler, HERO_MARCHES);
                }
            });
            matchesAdapter.setHasfoot(false);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && matchesAdapter.isHasfoot()) {
                        Fresco.getImagePipeline().resume();
                        if (linearLayoutManager.findLastVisibleItemPosition() == matchesAdapter.getItemCount() - 1) {
                            offset += NUMBER;
                            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches?hero_id=" + hero_id + "&offset=" + offset + "&limit=" + NUMBER, handler, HERO_MARCHES_UPDATE);
                        }
                    } else Fresco.getImagePipeline().pause();
                }
            });
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl?hero_id=" + hero_id, handler, WL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/totals?hero_id=" + hero_id, handler, TOTAL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches?hero_id=" + hero_id + "&offset=" + offset + "&limit=" + NUMBER, handler, HERO_MARCHES);
        }
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
            Intent intent = new Intent(MyHeroActivity.this, SettingsActivity.class);
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
