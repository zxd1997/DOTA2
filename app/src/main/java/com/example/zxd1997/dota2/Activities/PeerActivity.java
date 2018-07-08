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
import android.widget.Toast;

import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.MatchPlayer;
import com.example.zxd1997.dota2.Beans.Player;
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
    private final int NUMBER = 20;
    private final int MATCHES = 20;
    private final int UPDATE = 21;
    private static final int PLAYER_INFO = 1;
    private static final int WL = 2;
    private final List<MatchHero> matches = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private MatchesAdapter matchesAdapter;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj.toString().contains("rate limit exceeded")) {
                Toast.makeText(PeerActivity.this, R.string.api_rate, Toast.LENGTH_LONG).show();
            } else
                switch (msg.what) {
                case PLAYER_INFO: {
                    final Player player = new Gson().fromJson(msg.obj.toString(), Player.class);
                    matchesAdapter.setHasfoot(true);
                    MatchPlayer m = (MatchPlayer) matches.get(0);
                    m.setId(player.getAccount_id());
                    m.setAvatar(player.getAvatarfull());
                    m.setName(player.getName() != null ? player.getName() : player.getPersonaname() == null || player.getPersonaname().equals("") ? getString(R.string.anonymous) : player.getPersonaname());
                    m.setRank_tier(player.getRank_tier());
                    m.setRank(player.getLeaderboard_rank());
                    matchesAdapter.notifyItemChanged(0);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                }
                case WL: {
                    com.example.zxd1997.dota2.Beans.WL wl = new Gson().fromJson(msg.obj.toString(), com.example.zxd1997.dota2.Beans.WL.class);
                    wl.setWinrate();
                    MatchPlayer matchPlayer = (MatchPlayer) matches.get(0);
                    matchPlayer.setWin(wl.getWin());
                    matchPlayer.setLose(wl.getLose());
                    matchPlayer.setWinrate(wl.getWinrate());
                    matchesAdapter.notifyItemChanged(0);
                    break;
                }
                case MATCHES: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                        recentMatch.setType(1);
                        matches.add(recentMatch);
                    }
                    matchesAdapter.setHasfoot(true);
                    matchesAdapter.notifyDataSetChanged();
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
                        matches.add(recentMatch);
                    }
                    matchesAdapter.notifyItemRangeChanged(t, matchesAdapter.getItemCount());
                }
            }
            return true;
        }
    });
    private int offset = 0;
    private long with_id;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Update.setDensity(this, getApplication());
        getWindow().setStatusBarColor(Color.parseColor("#FFCC0000"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        setContentView(R.layout.activity_peer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        with_id = intent.getLongExtra("id", 0);
        String name = intent.getStringExtra("name");
        if (with_id == 0) {
            Intent intent1 = new Intent(PeerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        getSupportActionBar().setTitle(getString(R.string.matches_with) + " " + name);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        id = sharedPreferences.getString("id", "");
        RecyclerView recyclerView = findViewById(R.id.player_matches);
        swipeRefreshLayout = findViewById(R.id.swipe_player);
        swipeRefreshLayout.setColorSchemeResources(R.color.lose);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MatchPlayer m = new MatchPlayer();
        m.setType(8);
        matches.add(0, m);
        MatchHero recent = new MatchHero();
        recent.setTitle(getString(R.string.matches_play_together));
        recent.setType(5);
        matches.add(1, recent);
        matchesAdapter = new MatchesAdapter(this, matches);
        recyclerView.setAdapter(matchesAdapter);
        matchesAdapter.setHasfoot(true);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            matchesAdapter.setHasfoot(true);
            swipeRefreshLayout.setRefreshing(true);
            matches.clear();
            matchesAdapter.notifyDataSetChanged();
            MatchPlayer m1 = new MatchPlayer();
            m1.setType(8);
            matches.add(0, m1);
            matchesAdapter.notifyItemInserted(0);
            MatchHero recent1 = new MatchHero();
            recent1.setTitle(getString(R.string.matches_play_together));
            recent1.setType(5);
            matches.add(1, recent1);
            offset = 0;
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + with_id, handler, PLAYER_INFO);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + with_id + "/wl", handler, WL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches" + "?offset=" + offset + "&limit=" + NUMBER + "&included_account_id=" + with_id, handler, MATCHES);
        });
        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + with_id, handler, PLAYER_INFO);
        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + with_id + "/wl", handler, WL);
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
