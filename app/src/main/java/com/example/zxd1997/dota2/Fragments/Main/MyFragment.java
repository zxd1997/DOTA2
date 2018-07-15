package com.example.zxd1997.dota2.Fragments.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.MatchPlayer;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyFragment extends Fragment {
    private static final int VERIFY = 0;
    private static final int PLAYER_INFO = 1;
    private static final int WL = 2;
    private static final int RECENT_MATCHES = 3;
    private LinearLayout linearLayout;
    private SharedPreferences sharedPreferences;
    private String tmp;
    private Button button1;
    private Button button;
    private String id = "";
    private TextView textView;
    private MatchesAdapter matchesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextInputLayout textInputLayout;
    Receiver receiver = new Receiver();
    BindReceiver receiver1 = new BindReceiver();
    boolean bind = false;
    private final List<MatchHero> recentMatches = new ArrayList<>();
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.obj.toString().contains("rate limit exceeded")) {
                Toast.makeText(getContext(), R.string.api_rate, Toast.LENGTH_LONG).show();
            } else
                switch (message.what) {
                    case VERIFY: {
                        if (getContext() != null) {
                            Player t = new Gson().fromJson(message.obj.toString(), Player.class);
                            if (t == null || t.getProfile() == null) {
                                Toast.makeText(getContext(), R.string.no_such_player, Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                button1.setVisibility(View.VISIBLE);
                            } else {
                                linearLayout.setVisibility(View.GONE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                id = tmp;
                                editor.putString("id", id);
                                editor.apply();
                                recentMatches.clear();
                                matchesAdapter.notifyDataSetChanged();
                                MatchPlayer m = new MatchPlayer();
                                m.setType(8);
                                recentMatches.add(0, m);
                                MatchHero recent = new MatchHero();
                                recent.setType(5);
                                recent.setTitle(getString(R.string.recent_matches));
                                recentMatches.add(1, recent);
                                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id, handler, PLAYER_INFO);
                                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl", handler, WL);
                                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/recentMatches", handler, RECENT_MATCHES);
                            }
                        }
                        break;
                    }
                    case PLAYER_INFO: {
                        bind = false;
//                        Log.d("msg", "handleMessage: " + message.obj.toString());
                        final Player player = new Gson().fromJson(message.obj.toString(), Player.class);
                        if (getContext() != null) {
                            progressBar.setVisibility(View.GONE);
                            matchesAdapter.setHasfoot(true);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            MatchPlayer m = (MatchPlayer) recentMatches.get(0);
                            m.setId(player.getAccount_id());
                            m.setAvatar(player.getAvatarfull());
                            m.setName(player.getName() != null ? player.getName() : player.getPersonaname() == null || player.getPersonaname().equals("") ? getString(R.string.anonymous) : player.getPersonaname());
                            m.setRank_tier(player.getRank_tier());
                            m.setRank(player.getLeaderboard_rank());
                            matchesAdapter.notifyItemChanged(0);
                            swipeRefreshLayout.setRefreshing(false);
                            matchesAdapter.setHasfoot(false);
                        }
                        break;
                    }
                    case WL: {
                        com.example.zxd1997.dota2.Beans.WL wl = new Gson().fromJson(message.obj.toString(), WL.class);
                        if (getContext() != null) {
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
//                        MatchesAdapter.PlayerCard playerCard = (MatchesAdapter.PlayerCard) recyclerView.getChildViewHolder(recyclerView.getChildAt(0));
                            wl.setWinrate();
                            MatchPlayer matchPlayer = (MatchPlayer) recentMatches.get(0);
                            matchPlayer.setWin(wl.getWin());
                            matchPlayer.setLose(wl.getLose());
                            matchPlayer.setWinrate(wl.getWinrate());
                            matchesAdapter.notifyItemChanged(0);
                            matchesAdapter.setHasfoot(false);
                            break;
                        }
                    }
                    case RECENT_MATCHES: {
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(message.obj.toString()).getAsJsonArray();
                        for (JsonElement e : jsonArray) {
                            RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                            recentMatch.setType(1);
                            if (recentMatch.getHero_id() != 0)
                                recentMatches.add(recentMatch);
                        }
                        if (getContext() != null) {
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            matchesAdapter.setHasfoot(false);
                            matchesAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                }
            return true;
        }
    });
    private ProgressBar progressBar;

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(receiver1);
        System.gc();
        System.runFinalization();
    }

    private View view;

    public MyFragment() {
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.recent_matches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recentMatches.clear();
        MatchPlayer m = new MatchPlayer();
        m.setType(8);
        recentMatches.add(0, m);
        MatchHero recent = new MatchHero();
        recent.setType(5);
        recent.setTitle(getString(R.string.recent_matches));
        recentMatches.add(1, recent);
        matchesAdapter = new MatchesAdapter(getContext(), recentMatches);
        recyclerView.setAdapter(matchesAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Fresco.getImagePipeline().resume();
                } else Fresco.getImagePipeline().pause();
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.lose);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            recentMatches.clear();
            MatchPlayer m1 = new MatchPlayer();
            m1.setType(8);
            recentMatches.add(0, m1);
            MatchHero recent1 = new MatchHero();
            recent1.setType(5);
            recent1.setTitle(getString(R.string.recent_matches));
            recentMatches.add(1, recent1);
            matchesAdapter.notifyDataSetChanged();
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id, handler, PLAYER_INFO);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl", handler, WL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/recentMatches", handler, RECENT_MATCHES);
        });

        if (sharedPreferences.getString("id", "").equals("")) {
            ViewStub viewStub = view.findViewById(R.id.view_stub);
            viewStub.setVisibility(View.VISIBLE);
            linearLayout = view.findViewById(R.id.no_binding);
            button = view.findViewById(R.id.connect);
            button1 = view.findViewById(R.id.verify);
            textInputLayout = view.findViewById(R.id.text_input);
            linearLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            textView = view.findViewById(R.id.text_notice);
            button.setOnClickListener(v -> {
                textView.setVisibility(View.INVISIBLE);
                textInputLayout.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                button1.setVisibility(View.VISIBLE);
            });
            button1.setOnClickListener(v -> {
                TextInputEditText textInputEditText = view.findViewById(R.id.steam_id);
                if (textInputEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please input your Steam 32 ID!", Toast.LENGTH_LONG).show();
                } else {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (Objects.requireNonNull(imm).isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    tmp = textInputEditText.getText().toString();
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + tmp, handler, VERIFY);
                    button1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            id = sharedPreferences.getString("id", "");
        }
        if (!id.equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id, handler, PLAYER_INFO);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl", handler, WL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/recentMatches", handler, RECENT_MATCHES);
        }
        String DISCONNECT = "disconnect from id";
        IntentFilter intentFilter = new IntentFilter(DISCONNECT);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).registerReceiver(receiver, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("new bind");
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).registerReceiver(receiver1, intentFilter1);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bind) {
            linearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            id = sharedPreferences.getString("id", "");
            recentMatches.clear();
            matchesAdapter.notifyDataSetChanged();
            MatchPlayer m = new MatchPlayer();
            m.setType(8);
            recentMatches.add(0, m);
            MatchHero recent = new MatchHero();
            recent.setType(5);
            recent.setTitle(getString(R.string.recent_matches));
            recentMatches.add(1, recent);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id, handler, PLAYER_INFO);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl", handler, WL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/recentMatches", handler, RECENT_MATCHES);
            bind = false;
        }
    }

    public class BindReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            bind = true;
        }
    }
    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ViewStub viewStub = view.findViewById(R.id.view_stub);
            if (viewStub != null) viewStub.setVisibility(View.VISIBLE);
            linearLayout = view.findViewById(R.id.no_binding);
            button = view.findViewById(R.id.connect);
            button1 = view.findViewById(R.id.verify);
            textInputLayout = view.findViewById(R.id.text_input);
            linearLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            textView = view.findViewById(R.id.text_notice);
            button.setOnClickListener(v -> {
                textView.setVisibility(View.INVISIBLE);
                textInputLayout.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                button1.setVisibility(View.VISIBLE);
            });
            button1.setOnClickListener(v -> {
                TextInputEditText textInputEditText = view.findViewById(R.id.steam_id);
                if (textInputEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please input your Steam 32 ID!", Toast.LENGTH_LONG).show();
                } else {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (Objects.requireNonNull(imm).isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    tmp = textInputEditText.getText().toString();
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + tmp, handler, VERIFY);
                    button1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            swipeRefreshLayout.setVisibility(View.GONE);
            id = "";
            linearLayout.setVisibility(View.VISIBLE);
            textInputLayout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
            button.setVisibility(View.GONE);
            button1.setVisibility(View.VISIBLE);
            recentMatches.clear();
        }
    }
}
