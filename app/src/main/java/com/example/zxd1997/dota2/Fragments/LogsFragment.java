package com.example.zxd1997.dota2.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.LogAdapter;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogsFragment extends Fragment {
    private final List<Match.Objective> logs = new ArrayList<>();
    private final List<Match.Objective> current_logs = new ArrayList<>();
    private Match match;
    private CheckBox rune;
    private CheckBox building;
    private CheckBox combats;
    private CheckBox other;
    private LogAdapter logAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    @SuppressLint("HandlerLeak")
    private final
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                logAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    };

    public LogsFragment() {
        // Required empty public constructor
    }

    public static LogsFragment newInstance() {
        return new LogsFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            for (Match.TeamFight teamFight : match.getTeamfights()) {
                teamFight.setTime(teamFight.getStart());
                teamFight.setType("team_fight");
                Log.d(TAG, "onCreateView: " + teamFight.getTime() + " " + teamFight.getStart() + " " + teamFight.getDeaths());
                logs.add(teamFight);
            }
            for (Match.Objective o : match.getObjectives()) {
                if (o.getTeam() == 2) {
                    o.setName(Objects.requireNonNull(getContext()).getString(R.string.radiant));
                    o.setHero_id("radiant_logo");
                    o.setPlayer_slot(5);
                    logs.add(o);
                    continue;
                } else if (o.getTeam() == 3) {
                    o.setName(Objects.requireNonNull(getContext()).getString(R.string.dire));
                    o.setHero_id("dire_logo");
                    o.setPlayer_slot(6);
                    logs.add(o);
                    continue;
                }
                if (o.getUnit() != null) {
                    if (o.getUnit().contains("goodguys")) {
                        o.setName(Objects.requireNonNull(getContext()).getString(R.string.radiant));
                        o.setHero_id("radiant_logo");
                        o.setPlayer_slot(5);
                        logs.add(o);
                        continue;
                    } else if (o.getUnit().contains("badguys")) {
                        o.setName(Objects.requireNonNull(getContext()).getString(R.string.dire));
                        o.setHero_id("dire_logo");
                        o.setPlayer_slot(6);
                        logs.add(o);
                        continue;
                    }
                }
                for (Match.PPlayer p : match.getPlayers()) {
                    if (o.getPlayer_slot() == p.getPlayer_slot()) {
                        o.setName(p.getPersonaname());
                        o.setHero_id("hero_" + p.getHero_id());
                        break;
                    }
                }
                logs.add(o);
            }
            for (Match.PPlayer p : match.getPlayers()) {
                for (Match.Objective rune : p.getRunes_log()) {
                    rune.setType("rune_pickup");
                    rune.setHero_id("hero_" + p.getHero_id());
                    rune.setName(p.getPersonaname());
                    rune.setPlayer_slot(p.getPlayer_slot());
                    logs.add(rune);
                }
                for (Match.Objective kill : p.getKills_log()) {
                    kill.setType("kill");
                    kill.setName(p.getPersonaname());
                    kill.setHero_id("hero_" + p.getHero_id());
                    kill.setPlayer_slot(p.getPlayer_slot());
                    logs.add(kill);
                }
                for (Match.Objective buyback : p.getBuyback_log()) {
                    buyback.setName(p.getPersonaname());
                    buyback.setHero_id("hero_" + p.getHero_id());
                    logs.add(buyback);
                }
            }
            Collections.sort(logs, new Comparator<Match.Objective>() {
                @Override
                public int compare(Match.Objective o1, Match.Objective o2) {
                    return o1.getTime() - o2.getTime();
                }
            });
            progressBar = view.findViewById(R.id.progressBar4);
            current_logs.addAll(logs);
            for (Match.Objective objective : logs) {
                Log.d(TAG, "onCreateView: Logs:" + objective.getType() + " " + objective.getKey());
            }
            rune = view.findViewById(R.id.chk_rune);
            rune.setOnCheckedChangeListener(new CheckedListener());
            building = view.findViewById(R.id.chk_buildings);
            building.setOnCheckedChangeListener(new CheckedListener());
            combats = view.findViewById(R.id.chk_kill);
            combats.setOnCheckedChangeListener(new CheckedListener());
            other = view.findViewById(R.id.chk_other);
            other.setOnCheckedChangeListener(new CheckedListener());
            recyclerView = view.findViewById(R.id.logs);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            logAdapter = new LogAdapter(getContext(), current_logs);
            recyclerView.setAdapter(logAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        }
        return view;
    }

    class CheckedListener implements CheckBox.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "onCheckedChanged: " + logs.size());
                    current_logs.clear();
                    for (Match.Objective o : logs) {
                        if (o.getType().equals("team_fight")) {
                            current_logs.add(o);
                        } else if (o.getType().equals("kill")) {
                            if (combats.isChecked())
                                current_logs.add(o);
                        } else if (o.getType().equals("rune_pickup")) {
                            if (rune.isChecked())
                                current_logs.add(o);
                        } else if (o.getType().equals("buyback_log")) {
                            if (combats.isChecked())
                                current_logs.add(o);
                        } else if (o.getType().equals("building_kill")) {
                            if (building.isChecked())
                                current_logs.add(o);
                        } else if (other.isChecked()) {
                            current_logs.add(o);
                        }
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }).start();
        }
    }
}
