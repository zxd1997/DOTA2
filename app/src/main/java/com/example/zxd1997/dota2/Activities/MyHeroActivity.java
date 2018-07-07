package com.example.zxd1997.dota2.Activities;

import android.app.Activity;
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
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.MyHero;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.Beans.Total;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.example.zxd1997.dota2.Utils.Update;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyHeroActivity extends AppCompatActivity {
    private final int WL = 30;
    private final int TOTAL = 31;
    private final int HERO_MARCHES = 32;
    private final int HERO_MARCHES_UPDATE = 33;
    private final int NUMBER = 20;
    RecyclerView recyclerView;
    MatchesAdapter matchesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Activity activity;
    List<MatchHero> matches = new ArrayList<>();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WL: {
                    com.example.zxd1997.dota2.Beans.WL wl = new Gson().fromJson(msg.obj.toString(), com.example.zxd1997.dota2.Beans.WL.class);
                    wl.setWinrate();
                    MyHero myHero = (MyHero) matches.get(0);
                    myHero.setWl(wl);
                    matchesAdapter.notifyItemChanged(0);
                    break;
                }
                case TOTAL: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    List<Total> totals = new ArrayList<>();
                    for (JsonElement e : jsonArray) {
                        Total total = new Gson().fromJson(e, Total.class);
                        totals.add(total);
                    }
                    MyHero myHero = (MyHero) matches.get(0);
                    myHero.setTotal(totals);
                    matchesAdapter.notifyItemChanged(0);
                    break;
                }
                case HERO_MARCHES: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                        recentMatch.setType(1);
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
            Objects.requireNonNull(getSupportActionBar()).setTitle(typedArray.getText(hero_id));
            typedArray.recycle();
            ImageView imageView = findViewById(R.id.h_head);
            imageView.setImageResource(Tools.getResId("hero_" + hero_id, R.drawable.class));
            imageView.setTransitionName("hero_" + hero_id);
            activity = this;
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    TypedArray typedArray = getResources().obtainTypedArray(R.array.heroes);
                    setTitle(typedArray.getString(hero_id));
                    typedArray.recycle();
                    recyclerView = findViewById(R.id.player_matches);
                    swipeRefreshLayout = findViewById(R.id.swipe_player);
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    swipeRefreshLayout.setColorSchemeResources(R.color.lose);
                    MyHero m = new MyHero();
                    m.setHero_id(hero_id);
                    m.setType(7);
                    matches.add(m);
                    MatchHero m1 = new MatchHero();
                    m1.setHero_id(hero_id);
                    m1.setType(5);
                    m1.setTitle(getString(R.string.moth));
                    matches.add(m1);
                    matchesAdapter = new MatchesAdapter(activity, matches);
                    matchesAdapter.setHasfoot(true);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
                    final String id = sharedPreferences.getString("id", "");
                    swipeRefreshLayout.setRefreshing(true);
                    recyclerView.setAdapter(matchesAdapter);
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            matches.clear();
                            matchesAdapter.notifyDataSetChanged();
                            MyHero m = new MyHero();
                            m.setHero_id(hero_id);
                            m.setType(7);
                            matches.add(m);
                            MatchHero m1 = new MatchHero();
                            m1.setHero_id(hero_id);
                            m1.setType(5);
                            m1.setTitle(getString(R.string.moth));
                            matches.add(m1);
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

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });

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
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
