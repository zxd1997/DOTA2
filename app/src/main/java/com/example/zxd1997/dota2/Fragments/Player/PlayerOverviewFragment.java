package com.example.zxd1997.dota2.Fragments.Player;


import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.Beans.Total;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class PlayerOverviewFragment extends Fragment {
    private final static int WL_FULL = 1;
    private final static int WL_RECENT = 2;
    private final static int TOTAL_FULL = 3;
    private final static int TOTAL_RECENT = 4;
    private final static int KILLS = 5;
    private final static int DEATHS = 6;
    private final static int ASSISTS = 7;
    private final static int GPM = 8;
    private final static int XPM = 9;
    private final static int LASTHIT = 10;
    private final static int DENIES = 11;
    private final static int DAMAGE = 12;
    private final static int TOWER = 13;
    private final static int HEALING = 14;
    private final static int DURATION = 15;
    private final static int WL_FINISH = 16;
    private final static int TOTAL_FINISH = 17;
    private final static int RECORD_FINISH = 18;
    int wl_count = 0;
    int total_count = 0;
    int record_count = 0;
    private final List<Total> totals_full = new ArrayList<>();
    private final List<Total> totals_recent = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final DecimalFormat df1 = new DecimalFormat("0.0");
    private WL wl_full;
    private WL wl_recent;
    private TextView record;
    private Player player;
    private TextView win;
    private TextView lose;
    private TextView total;
    private TextView win_rate;
    private TextView kda;
    private TextView damage;
    private TextView gpm;
    private TextView xpm;
    private ProgressBar progressBar;
    private View view;
    private RecyclerView recyclerView;
    private MatchesAdapter matchesAdapter;
    private List<MatchHero> records = new ArrayList<>();
    public PlayerOverviewFragment() {

    }

    public static PlayerOverviewFragment newInstance() {
        return new PlayerOverviewFragment();
    }

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WL_FULL: {
                    wl_full = new Gson().fromJson(msg.obj.toString(), WL.class);
                    wl_full.setWinrate();
                    wl_count++;
                    if (wl_count == 2) {
                        Message message = new Message();
                        message.what = WL_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case WL_RECENT: {
                    wl_recent = new Gson().fromJson(msg.obj.toString(), WL.class);
                    wl_recent.setWinrate();
                    wl_count++;
                    if (wl_count == 2) {
                        Message message = new Message();
                        message.what = WL_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case WL_FINISH: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final SpannableStringBuilder t = new SpannableStringBuilder();
                            SpannableString rec_win = new SpannableString(wl_recent.getWin() + "");
                            SpannableString full_win = new SpannableString(wl_full.getWin() + "");
                            rec_win.setSpan(new ForegroundColorSpan(Objects.requireNonNull(getContext()).getResources().getColor(R.color.win)), 0, rec_win.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            rec_win.setSpan(new RelativeSizeSpan(1.4f), 0, rec_win.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            full_win.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.win)), 0, full_win.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t.append(rec_win).append("/").append(full_win);
                            final SpannableStringBuilder t1 = new SpannableStringBuilder();
                            SpannableString rec_lose = new SpannableString(wl_recent.getLose() + "");
                            SpannableString full_lose = new SpannableString(wl_full.getLose() + "");
                            rec_lose.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.lose)), 0, rec_lose.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            rec_lose.setSpan(new RelativeSizeSpan(1.4f), 0, rec_lose.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            full_lose.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.lose)), 0, full_lose.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t1.append(rec_lose).append("/").append(full_lose);
                            final SpannableStringBuilder t2 = new SpannableStringBuilder();
                            SpannableString rec_win_rate = new SpannableString(df.format(wl_recent.getWinrate() * 100) + "%");
                            SpannableString full_win_rate = new SpannableString(df.format(wl_full.getWinrate() * 100) + "%");
                            rec_win_rate.setSpan(new RelativeSizeSpan(1.3f), 0, rec_win_rate.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t2.append(rec_win_rate).append("/").append(full_win_rate);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    win.setText(t);
                                    total.setText(String.valueOf(wl_full.getWin() + wl_full.getLose()));
                                    lose.setText(t1);
                                    win_rate.setText(t2);
                                }
                            });
                        }
                    }).start();
                    break;
                }
                case TOTAL_FULL: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        Total total = new Gson().fromJson(e, Total.class);
                        totals_full.add(total);
                    }
                    total_count++;
                    if (total_count == 2) {
                        Message message = new Message();
                        message.what = TOTAL_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case TOTAL_RECENT: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        Total total = new Gson().fromJson(e, Total.class);
                        totals_recent.add(total);
                    }
                    total_count++;
                    if (total_count == 2) {
                        Message message = new Message();
                        message.what = TOTAL_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case TOTAL_FINISH: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final SpannableStringBuilder t = new SpannableStringBuilder();
                            SpannableString rec_kda = new SpannableString(df.format((float) totals_recent.get(3).getSum() / totals_recent.get(3).getN()) + "");
                            SpannableString full_kda = new SpannableString(df.format((float) totals_full.get(3).getSum() / totals_full.get(3).getN()) + "");
                            rec_kda.setSpan(new RelativeSizeSpan(1.4f), 0, rec_kda.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t.append(rec_kda).append("/").append(full_kda);
                            final SpannableStringBuilder t1 = new SpannableStringBuilder();
                            SpannableString rec_damage = new SpannableString(df.format((float) totals_recent.get(11).getSum() / totals_recent.get(11).getN() / 1000) + "k");
                            SpannableString full_damage = new SpannableString(df.format((float) totals_full.get(11).getSum() / totals_full.get(11).getN() / 1000) + "k");
                            rec_damage.setSpan(new RelativeSizeSpan(1.3f), 0, rec_damage.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t1.append(rec_damage).append("/").append(full_damage);
                            final SpannableStringBuilder t2 = new SpannableStringBuilder();
                            SpannableString rec_gpm = new SpannableString(df1.format((float) totals_recent.get(4).getSum() / totals_recent.get(4).getN()));
                            SpannableString full_gpm = new SpannableString(df1.format((float) totals_full.get(4).getSum() / totals_full.get(4).getN()));
                            rec_gpm.setSpan(new RelativeSizeSpan(1.3f), 0, rec_gpm.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t2.append(rec_gpm).append("/").append(full_gpm);
                            final SpannableStringBuilder t3 = new SpannableStringBuilder();
                            SpannableString rec_xpm = new SpannableString(df1.format((float) totals_recent.get(5).getSum() / totals_recent.get(5).getN()));
                            SpannableString full_xpm = new SpannableString(df1.format((float) totals_full.get(5).getSum() / totals_full.get(5).getN()));
                            rec_xpm.setSpan(new RelativeSizeSpan(1.3f), 0, rec_xpm.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t3.append(rec_xpm).append("/").append(full_xpm);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    kda.setText(t);
                                    damage.setText(t1);
                                    gpm.setText(t2);
                                    xpm.setText(t3);
                                }
                            });
                        }
                    }).start();
                    break;
                }
                case RECORD_FINISH: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Collections.sort(records, new Comparator<MatchHero>() {
                                @Override
                                public int compare(MatchHero o1, MatchHero o2) {
                                    return o1.getNo() - o2.getNo();
                                }
                            });
                            matchesAdapter = new MatchesAdapter(getContext(), records);
                            matchesAdapter.setHasfoot(false);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    record.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(matchesAdapter);
                                    progressBar.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }).start();
                    break;
                }
                case KILLS: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.hightest_kills) + recentMatch.getKills());
                    recentMatch.setNo(1);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case DEATHS: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.highest_death) + recentMatch.getDeaths());
                    recentMatch.setNo(2);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case ASSISTS: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.highest_assists) + recentMatch.getAssists());
                    recentMatch.setNo(3);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case GPM: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.hightest_gpm) + recentMatch.getGold_per_min());
                    recentMatch.setNo(4);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case XPM: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.hightest_xpm) + recentMatch.getXp_per_min());
                    recentMatch.setNo(5);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case LASTHIT: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.highest_lh) + recentMatch.getLast_hits());
                    recentMatch.setNo(6);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case DENIES: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.hightest_denies) + recentMatch.getDenies());
                    recentMatch.setNo(7);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case DAMAGE: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.hightest_damage) + recentMatch.getHero_damage());
                    recentMatch.setNo(8);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case TOWER: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.hightest_td) + recentMatch.getTower_damage());
                    recentMatch.setNo(9);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case HEALING: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.hightest_hh) + recentMatch.getHero_healing());
                    recentMatch.setNo(10);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
                case DURATION: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    RecentMatch recentMatch = new Gson().fromJson(jsonArray.get(0), RecentMatch.class);
                    recentMatch.setType(3);
                    recentMatch.setTitle(getString(R.string.longest_duration) + Tools.getTime(recentMatch.getDuration()));
                    recentMatch.setNo(11);
                    records.add(recentMatch);
                    record_count++;
                    if (record_count == 11) {
                        Message message = new Message();
                        message.what = RECORD_FINISH;
                        handler.sendMessage(message);
                    }
                    break;
                }
            }
            return true;
        }
    });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player_overview, container, false);
        PlayerActivity playerActivity = (PlayerActivity) getActivity();
        player = Objects.requireNonNull(playerActivity).getPlayer();
        if (player == null) {
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            progressBar = view.findViewById(R.id.progressBar);
            SimpleDraweeView player_header = view.findViewById(R.id.player_header);
            TextView player_name = view.findViewById(R.id.player_nickname);
            TextView player_id = view.findViewById(R.id.player_id);
            TextView steam_profile = view.findViewById(R.id.steam_profile);
            SimpleDraweeView tier = view.findViewById(R.id.tier);
            TextView ranks = view.findViewById(R.id.rank);
            SimpleDraweeView stars = view.findViewById(R.id.star);
            TextView tier_name = view.findViewById(R.id.tier_name);
            win = view.findViewById(R.id.player_win);
            lose = view.findViewById(R.id.player_lose);
            total = view.findViewById(R.id.player_total);
            win_rate = view.findViewById(R.id.player_win_rate);
            kda = view.findViewById(R.id.player_kda);
            damage = view.findViewById(R.id.player_damage);
            gpm = view.findViewById(R.id.player_gpm);
            xpm = view.findViewById(R.id.player_xpm);
            recyclerView = view.findViewById(R.id.record);
            record = view.findViewById(R.id.records);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        Fresco.getImagePipeline().resume();
                    else Fresco.getImagePipeline().pause();
                }
            });
            player_header.setImageURI(player.getAvatarfull());
            player_name.setText(player.getName() != null ? player.getName() : player.getPersonaname() == null || player.getPersonaname().equals("") ? getString(R.string.anonymous) : player.getPersonaname());
            player_id.setText(getString(R.string.id, player.getAccount_id()));
            SpannableString s = new SpannableString(player.getProfileurl());
            s.setSpan(new URLSpan(player.getProfileurl()), 0, player.getProfileurl().length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            steam_profile.setText(s);
            steam_profile.setMovementMethod(LinkMovementMethod.getInstance());
            if (player.getRank_tier() != 0) {
                int t = player.getRank_tier() / 10;
                int star = player.getRank_tier() % 10;
                int rank = player.getLeaderboard_rank();
//                Log.d("rank", "handleMessage: " + rank);
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
//                Log.d("rank", "handleMessage: " + t + " " + star);
                TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.tiers);
                tier.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(typedArray.getResourceId(t, 0))).build());
                typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.tier_names);
                tier_name.setText(String.valueOf(typedArray.getString(t)));
                typedArray.recycle();
            }
            progressBar.setVisibility(View.VISIBLE);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/wl", handler, WL_FULL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/wl?limit=20", handler, WL_RECENT);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/totals", handler, TOTAL_FULL);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/totals?limit=20", handler, TOTAL_RECENT);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=kills" + "&limit=1", handler, KILLS);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=deaths" + "&limit=1", handler, DEATHS);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=assists" + "&limit=1", handler, ASSISTS);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=gold_per_min" + "&limit=1", handler, GPM);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=xp_per_min" + "&limit=1", handler, XPM);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=last_hits" + "&limit=1", handler, LASTHIT);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=denies" + "&limit=1", handler, DENIES);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=hero_damage" + "&limit=1", handler, DAMAGE);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=tower_damage" + "&limit=1", handler, TOWER);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=hero_healing" + "&limit=1", handler, HEALING);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?sort=duration" + "&limit=1", handler, DURATION);

        }
        return view;
    }

}
