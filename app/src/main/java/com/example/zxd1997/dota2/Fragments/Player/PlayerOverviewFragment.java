package com.example.zxd1997.dota2.Fragments.Player;


import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.Total;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PlayerOverviewFragment extends Fragment {
    private final static int WL_FULL = 1;
    private final static int WL_RECENT = 2;
    private final static int TOTAL_FULL = 3;
    private final static int TOTAL_RECENT = 4;
    private final List<Total> totals_full = new ArrayList<>();
    private final List<Total> totals_recent = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final DecimalFormat df1 = new DecimalFormat("0.0");
    private WL wl_full;
    private WL wl_recent;
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
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/wl?limit=20", handler, WL_RECENT);
                    break;
                }
                case WL_RECENT: {
                    wl_recent = new Gson().fromJson(msg.obj.toString(), WL.class);
                    wl_recent.setWinrate();
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
                            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/totals", handler, TOTAL_FULL);
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
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/totals?limit=20", handler, TOTAL_RECENT);
                    break;
                }
                case TOTAL_RECENT: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        Total total = new Gson().fromJson(e, Total.class);
                        totals_recent.add(total);
                    }
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
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }).start();
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
        }
        return view;
    }

}
