package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Update;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
    private static final int PLAYER_INFO = 1;
    private static final int WL = 2;
    SwipeRefreshLayout swipeRefreshLayout;
    MatchesAdapter matchesAdapter;
    List<MatchHero> matches = new ArrayList<>();
    int offset = 0;
    TextView mpt;
    private SimpleDraweeView header;
    private SimpleDraweeView tier;
    private SimpleDraweeView stars;
    private TextView textView;
    private TextView persona_name;
    private TextView account_id;
    private TextView win;
    private TextView lose;
    private TextView win_rate;
    private TextView ranks;
    private CardView card;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PLAYER_INFO: {
                    final Player player = new Gson().fromJson(msg.obj.toString(), Player.class);
                    persona_name.setText(player.getName() != null ? player.getName() : player.getPersonaname() == null || player.getPersonaname().equals("") ? getString(R.string.anonymous) : player.getPersonaname());
                    account_id.setText(getString(R.string.id, player.getAccount_id()));
                    header.setImageURI(player.getAvatarfull());
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                            intent.putExtra("id", player.getAccount_id());
                            startActivity(intent);
                        }
                    });
                    ranks.setText("");
                    stars.setImageDrawable(null);
                    tier.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.rank_icon_0)).build());
                    if (player.getRank_tier() != 0) {
                        int t = player.getRank_tier() / 10;
                        int star = player.getRank_tier() % 10;
                        tier.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.rank_icon_0)).build());
                        int rank = player.getLeaderboard_rank();
//                        Log.d("rank", "handleMessage: " + rank);
                        if (rank > 0) {
                            star = 0;
                            t = 8;
                            if (rank == 1) {
                                t = 12;
                            } else if (rank <= 10) {
                                t = 11;
                            } else if (rank <= 100) {
                                t = 10;
                            } else if (rank <= 1000) {
                                t = 9;
                            }
                            ranks.setText(String.valueOf(rank));
                        }
                        if (star > 0) {
                            TypedArray typedArray = Objects.requireNonNull(getApplicationContext()).getResources().obtainTypedArray(R.array.stars);
                            stars.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(typedArray.getResourceId(star - 1, 0))).build());
                            typedArray.recycle();
                        }
//                        Log.d("rank", "handleMessage: " + t + " " + star);
                        TypedArray typedArray = Objects.requireNonNull(getApplicationContext()).getResources().obtainTypedArray(R.array.tiers);
                        tier.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(typedArray.getResourceId(t, 0))).build());
                        typedArray.recycle();
                    }
                    card.setVisibility(View.VISIBLE);
                    mpt.setVisibility(View.VISIBLE);
                    break;
                }
                case WL: {
                    com.example.zxd1997.dota2.Beans.WL wl = new Gson().fromJson(msg.obj.toString(), com.example.zxd1997.dota2.Beans.WL.class);
                    wl.setWinrate();
                    win.setText(getString(R.string.win1, wl.getWin()));
                    lose.setText(getString(R.string.lose1, wl.getLose()));
                    win_rate.setText(String.format("%s%%", getString(R.string.rate, wl.getWinrate() * 100)));
                    break;
                }
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
        header = findViewById(R.id.header);
        persona_name = findViewById(R.id.personaname);
        account_id = findViewById(R.id.account_id);
        win = findViewById(R.id.win);
        lose = findViewById(R.id.lose);
        win_rate = findViewById(R.id.win_rate);
        tier = findViewById(R.id.tier);
        ranks = findViewById(R.id.rank);
        stars = findViewById(R.id.star);
        card = findViewById(R.id.my_card);
        mpt = findViewById(R.id.m_p_t);
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
        swipeRefreshLayout.setColorSchemeResources(R.color.lose);
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
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + with_id, handler, PLAYER_INFO);
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + with_id + "/wl", handler, WL);
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/matches" + "?offset=" + offset + "&limit=" + NUMBER + "&included_account_id=" + with_id, handler, MATCHES);
            }
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
