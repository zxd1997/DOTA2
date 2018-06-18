package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
    private CheckBox rune;
    private CheckBox building;
    private CheckBox combats;
    private CheckBox other;
    private LogAdapter logAdapter;
    private RecyclerView recyclerView;

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
            for (Match.Objective chat : match.getChat()) {
                if (!"chatwheel".equals(chat.getType())) {
                    Match.PPlayer p = match.getPlayers().get(chat.getSlot());
                    chat.setHero_id("hero_" + p.getHero_id());
                    chat.setName(p.getPersonaname());
                    logs.add(chat);
                }
            }
            for (Match.TeamFight teamFight : match.getTeamfights()) {
                teamFight.setTime(teamFight.getStart());
                teamFight.setType("team_fight");
                int i = 0;
                for (Match.TeamFight.TeamFightPlayer p : teamFight.getPlayers()) {
                    Match.PPlayer pPlayer = match.getPlayers().get(i);
                    i++;
                    p.setPlayer_slot(pPlayer.getPlayer_slot());
                    p.setHero_id("hero_" + pPlayer.getHero_id());
                    p.setPersonaname(pPlayer.getPersonaname());
                }
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
                Match.PPlayer p = match.getPlayers().get(o.getSlot());
                o.setName(p.getPersonaname());
                o.setHero_id("hero_" + p.getHero_id());
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
            logAdapter = new LogAdapter(getContext(), current_logs, recyclerView);
            recyclerView.setAdapter(logAdapter);
            recyclerView.setNestedScrollingEnabled(false);
//            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }


    class CheckedListener implements CheckBox.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            for (int i = 0; i < logAdapter.getItemCount(); i++) {
                Match.Objective o = logs.get(i);
                switch (o.getType()) {
                    case "kill": {
                        if (buttonView.getId() == R.id.chk_kill)
                            ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "buyback_log": {
                        if (buttonView.getId() == R.id.chk_kill)
                            ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "building_kill": {
                        if (buttonView.getId() == R.id.chk_buildings && recyclerView.getChildAt(i) != null)
                            ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "CHAT_MESSAGE_COURIER_LOST": {
                        if (buttonView.getId() == R.id.chk_other)
                            ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "rune_pickup": {
                        if (buttonView.getId() == R.id.chk_rune)
                            ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "CHAT_MESSAGE_ROSHAN_KILL": {
                        if (buttonView.getId() == R.id.chk_other)
                            ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "CHAT_MESSAGE_FIRSTBLOOD": {
                        if (buttonView.getId() == R.id.chk_kill)
                            ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "CHAT_MESSAGE_AEGIS": {
                        if (buttonView.getId() == R.id.chk_other)
                            ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    case "chat": {
                        if (buttonView.getId() == R.id.chk_other)
                            ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(i))).setVisibility(buttonView.isChecked());
                        break;
                    }
                    default: {
                        break;
                    }
                }
                Log.d(TAG, "onCheckedChanged: " + recyclerView.getChildCount());
            }
        }
    }
}
