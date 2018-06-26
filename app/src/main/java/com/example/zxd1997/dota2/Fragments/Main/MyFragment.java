package com.example.zxd1997.dota2.Fragments.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
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
import android.support.v7.widget.CardView;
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

import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
    private MatchesAdapter matchesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextInputLayout textInputLayout;
    private final List<MatchHero> recentMatches = new ArrayList<>();
    private CardView card;
    private TextView recent_match;
    private ProgressBar progressBar;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case VERIFY: {
                    Player t = new Gson().fromJson(message.obj.toString(), Player.class);
                    if (t == null || t.getProfile() == null) {
                        Toast.makeText(getContext(), "No Such Player", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        button1.setVisibility(View.VISIBLE);
                    } else {
                        linearLayout.setVisibility(View.GONE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        id = tmp;
                        editor.putString("id", id);
                        editor.apply();
                        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id, handler, PLAYER_INFO);
                        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl", handler, WL);
                        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/recentMatches", handler, RECENT_MATCHES);
                    }
                    break;
                }
                case PLAYER_INFO: {
                    final Player player = new Gson().fromJson(message.obj.toString(), Player.class);
                    persona_name.setText(player.getName()!=null?player.getName():player.getPersonaname() == null || player.getPersonaname().equals("") ? getString(R.string.anonymous) : player.getPersonaname());
                    account_id.setText(getString(R.string.id, player.getAccount_id()));
                    header.setImageURI(player.getAvatarfull());
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), PlayerActivity.class);
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
                            TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.stars);
                            stars.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(typedArray.getResourceId(star - 1, 0))).build());
                            typedArray.recycle();
                        }
//                        Log.d("rank", "handleMessage: " + t + " " + star);
                        TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.tiers);
                        tier.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(typedArray.getResourceId(t, 0))).build());
                        typedArray.recycle();
                    }
                    card.setVisibility(View.VISIBLE);
                    break;
                }
                case WL: {
                    com.example.zxd1997.dota2.Beans.WL wl = new Gson().fromJson(message.obj.toString(), WL.class);
                    wl.setWinrate();
                    win.setText(getString(R.string.win1, wl.getWin()));
                    lose.setText(getString(R.string.lose1, wl.getLose()));
                    win_rate.setText(String.format("%s%%", getString(R.string.rate, wl.getWinrate() * 100)));
                    break;
                }
                case RECENT_MATCHES: {
                    recentMatches.clear();
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(message.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                        recentMatch.setType(1);
                        if (recentMatch.getHero_id() != 0)
                            recentMatches.add(recentMatch);
                    }
                    recent_match.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    matchesAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                }
            }
            return true;
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        recent_match = view.findViewById(R.id.recent_match);
        progressBar = view.findViewById(R.id.progressBar);
        header = view.findViewById(R.id.header);
        persona_name = view.findViewById(R.id.personaname);
        account_id = view.findViewById(R.id.account_id);
        win = view.findViewById(R.id.win);
        lose = view.findViewById(R.id.lose);
        win_rate = view.findViewById(R.id.win_rate);
        tier = view.findViewById(R.id.tier);
        ranks = view.findViewById(R.id.rank);
        stars = view.findViewById(R.id.star);
        RecyclerView recyclerView = view.findViewById(R.id.recent_matches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapter = new MatchesAdapter(getContext(), recentMatches);
        matchesAdapter.setHasfoot(false);
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
        card = view.findViewById(R.id.my_card);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id, handler, PLAYER_INFO);
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/wl", handler, WL);
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id + "/recentMatches", handler, RECENT_MATCHES);

            }
        });

        if (sharedPreferences.getString("id", "").equals("")) {
            ViewStub viewStub=view.findViewById(R.id.view_stub);
            viewStub.setVisibility(View.VISIBLE);
            linearLayout = view.findViewById(R.id.no_binding);
            button = view.findViewById(R.id.connect);
            button1 = view.findViewById(R.id.verify);
            textInputLayout = view.findViewById(R.id.text_input);
            linearLayout.setVisibility(View.VISIBLE);
            card.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.GONE);
            recent_match.setVisibility(View.GONE);
            swipeRefreshLayout.setColorSchemeResources(R.color.lose);
            textView = view.findViewById(R.id.text_notice);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setVisibility(View.INVISIBLE);
                    textInputLayout.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                    button1.setVisibility(View.VISIBLE);
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        Receiver receiver = new Receiver();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).registerReceiver(receiver, intentFilter);
        return view;
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ViewStub viewStub=view.findViewById(R.id.view_stub);
            if(viewStub!=null)viewStub.setVisibility(View.VISIBLE);
            linearLayout = view.findViewById(R.id.no_binding);
            button = view.findViewById(R.id.connect);
            button1 = view.findViewById(R.id.verify);
            textInputLayout = view.findViewById(R.id.text_input);
            linearLayout.setVisibility(View.VISIBLE);
            card.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.GONE);
            recent_match.setVisibility(View.GONE);
            textView = view.findViewById(R.id.text_notice);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setVisibility(View.INVISIBLE);
                    textInputLayout.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                    button1.setVisibility(View.VISIBLE);
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }
            });
            card.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.GONE);
            recent_match.setVisibility(View.GONE);
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
