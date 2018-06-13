package com.example.zxd1997.dota2.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.LogAdapter;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogsFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    public LogsFragment() {
        // Required empty public constructor
    }

    public static LogsFragment newInstance() {
        return new LogsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            List<Match.TeamFight> teamFights = match.getTeamFights();
            List<Match.Objective> logs = new ArrayList<>();
//            logs.addAll(match.getObjectives());
            for (Match.PPlayer p : match.getPlayers()) {
                for (Match.PPlayer.PObjective rune : p.getRunes_log()) {
                    rune.setType("rune_pickup");
                    rune.setHero_id(p.getHero_id());
                    rune.setName(p.getPersonaname());
                    logs.add(rune);
                }
                for (Match.PPlayer.PObjective kill : p.getKills_log()) {
                    kill.setType("kill");
                    kill.setName(p.getPersonaname());
                    kill.setHero_id(p.getHero_id());
                    kill.setPlayer_slot(p.getPlayer_slot());
                    for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
                        if (entry.getKey().equals(kill.getKey())) {
                            kill.setKey(String.valueOf(entry.getValue().getId()));
                            break;
                        }
                    }
                    logs.add(kill);
                }
                for (Match.PPlayer.PObjective buyback : p.getBuyback_log()) {
                    buyback.setName(p.getPersonaname());
                    buyback.setHero_id(p.getHero_id());
                    logs.add(buyback);
                }
            }
            Collections.sort(logs, new Comparator<Match.Objective>() {
                @Override
                public int compare(Match.Objective o1, Match.Objective o2) {
                    return o1.getTime() - o2.getTime();
                }
            });
            for (Match.Objective objective : logs) {
                Log.d(TAG, "onCreateView: Logs:" + objective.getType() + " " + objective.getKey());
            }
            RecyclerView recyclerView = view.findViewById(R.id.logs);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new LogAdapter(getContext(), logs));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
        return view;
    }

}
