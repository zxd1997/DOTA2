package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogsFragment extends Fragment {
    private final List<Match.Objective> logs = new ArrayList<>();
    private final List<Match.Objective> current_logs = new ArrayList<>();
    private final static int LOAD = 0;
    private View view;
    private RecyclerView recyclerView;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (getContext() != null) {
                if (msg.what == LOAD) {
                    CheckBox rune = view.findViewById(R.id.chk_rune);
                    CheckedListener checkedListener = new CheckedListener();
                    rune.setOnCheckedChangeListener(checkedListener);
                    CheckBox building = view.findViewById(R.id.chk_buildings);
                    building.setOnCheckedChangeListener(checkedListener);
                    CheckBox combats = view.findViewById(R.id.chk_kill);
                    combats.setOnCheckedChangeListener(checkedListener);
                    CheckBox other = view.findViewById(R.id.chk_other);
                    other.setOnCheckedChangeListener(checkedListener);
                    recyclerView = view.findViewById(R.id.logs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    LogAdapter logAdapter = new LogAdapter(getContext(), current_logs, recyclerView);
                    recyclerView.setAdapter(logAdapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()));
                    Intent intent = new Intent("loaded");
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
            return true;
        }
    });

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
        view = inflater.inflate(R.layout.fragment_log, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        final Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
//            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Match.Objective chat : match.getChat()) {
                        if (!"chatwheel".equals(chat.getType())) {
                            if (chat.getSlot() < 10) {
                                Match.PPlayer p = match.getPlayers().get(chat.getSlot());
                                chat.setHero_id("hero_" + p.getHero_id());
                                chat.setName(p.getName() != null ? p.getName() : p.getPersonaname() == null || p.getPersonaname().equals("") ? Objects.requireNonNull(getContext()).getResources().getString(R.string.anonymous) : p.getPersonaname());
                                logs.add(chat);
                            } else {
                                if (chat.getSlot() == 10)
                                    chat.setHero_id("radiant_logo");
                                else chat.setHero_id("dire_logo");
                                chat.setName(getString(R.string.spectator) + chat.getUnit());
                                logs.add(chat);
                            }
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
                            p.setPersonaname(pPlayer.getName() != null ? pPlayer.getName() : pPlayer.getPersonaname() == null || pPlayer.getPersonaname().equals("") ? getContext().getResources().getString(R.string.anonymous) : pPlayer.getPersonaname());
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
                        o.setName(p.getName() != null ? p.getName() : p.getPersonaname() == null || p.getPersonaname().equals("") ? getContext().getResources().getString(R.string.anonymous) : p.getPersonaname());
                        o.setHero_id("hero_" + p.getHero_id());
                        logs.add(o);
                    }
                    for (Match.PPlayer p : match.getPlayers()) {
                        for (Match.Objective rune : p.getRunes_log()) {
                            rune.setType("rune_pickup");
                            rune.setHero_id("hero_" + p.getHero_id());
                            rune.setName(p.getName() != null ? p.getName() : p.getPersonaname() == null || p.getPersonaname().equals("") ? getContext().getResources().getString(R.string.anonymous) : p.getPersonaname());
                            rune.setPlayer_slot(p.getPlayer_slot());
                            logs.add(rune);
                        }
                        for (Match.Objective kill : p.getKills_log()) {
                            kill.setType("kill");
                            kill.setName(p.getName() != null ? p.getName() : p.getPersonaname() == null || p.getPersonaname().equals("") ? getContext().getResources().getString(R.string.anonymous) : p.getPersonaname());
                            kill.setHero_id("hero_" + p.getHero_id());
                            kill.setPlayer_slot(p.getPlayer_slot());
                            logs.add(kill);
                        }
                        for (Match.Objective buyback : p.getBuyback_log()) {
                            buyback.setName(p.getName() != null ? p.getName() : p.getPersonaname() == null || p.getPersonaname().equals("") ? getContext().getResources().getString(R.string.anonymous) : p.getPersonaname());
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
                    Message message = new Message();
                    message.what = LOAD;
                    handler.sendMessage(new Message());
                }
            }).start();
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
        public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < logs.size(); i++) {
                        Match.Objective o = logs.get(i);
                        final int t = i;
                        switch (o.getType()) {
                            case "kill": {
                                if (buttonView.getId() == R.id.chk_kill)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolder)
                                                ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "buyback_log": {
                                if (buttonView.getId() == R.id.chk_kill)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolderNotKill)
                                                ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "building_kill": {
                                if (buttonView.getId() == R.id.chk_buildings)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolder)
                                                ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "CHAT_MESSAGE_COURIER_LOST": {
                                if (buttonView.getId() == R.id.chk_other)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolder)
                                                ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "rune_pickup": {
                                if (buttonView.getId() == R.id.chk_rune)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolderNotKill)
                                                ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "CHAT_MESSAGE_ROSHAN_KILL": {
                                if (buttonView.getId() == R.id.chk_other)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolder)
                                                ((LogAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "CHAT_MESSAGE_FIRSTBLOOD": {
                                if (buttonView.getId() == R.id.chk_kill)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolderNotKill)
                                                ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "CHAT_MESSAGE_AEGIS": {
                                if (buttonView.getId() == R.id.chk_other)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolderNotKill)
                                                ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            case "chat": {
                                if (buttonView.getId() == R.id.chk_other)
                                    view.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (recyclerView.getChildViewHolder(recyclerView.getChildAt(t)) instanceof LogAdapter.ViewHolderNotKill)
                                                ((LogAdapter.ViewHolderNotKill) recyclerView.getChildViewHolder(recyclerView.getChildAt(t))).setVisibility(buttonView.isChecked());
                                        }
                                    });
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }
            }).start();
        }
    }
}
