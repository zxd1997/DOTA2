package com.example.zxd1997.dota2.Fragments.Match;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.AbilityBuildAdapter;
import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Adapters.PlayerAdapter;
import com.example.zxd1997.dota2.Beans.Abilities;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.GridItemDecoration;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OverviewFragment extends Fragment {
    private final int ABILITY = 1;
    private final int HEADER = -1;
    private final int PLAYER = -2;
    private final int LEVEL = -3;

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    public OverviewFragment() {
        // Required empty public constructor
    }

    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @SuppressLint("Recycle")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_overview, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        final Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
//            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        } else {
//            Log.d("fragment", "onCreateView: " + match.getMatch_id());
            TextView game_mode = view.findViewById(R.id.gamemode);
            TextView duration = view.findViewById(R.id.duration);
            TextView radiant_score = view.findViewById(R.id.radiant_score);
            TextView dire_score = view.findViewById(R.id.dire_score);
            TextView time = view.findViewById(R.id.start_time);
            TextView match_id = view.findViewById(R.id.match_id);
            TextView region = view.findViewById(R.id.region);
            TextView skill = view.findViewById(R.id.skill);
            TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.skills);
            skill.setText(typedArray.getText(match.getSkill()));
            typedArray = getContext().getResources().obtainTypedArray(R.array.skills_color);
            skill.setTextColor(getContext().getResources().getColor(typedArray.getResourceId(match.getSkill(), 0)));
            if (match.isRadiant_win()) {
                TextView radiant_victory = view.findViewById(R.id.radiant_victory);
                radiant_victory.setVisibility(View.VISIBLE);
            } else {
                TextView dire_victory = view.findViewById(R.id.dire_victory);
                dire_victory.setVisibility(View.VISIBLE);
            }
            typedArray = getContext().getResources().obtainTypedArray(R.array.game_mode);
//            Log.d("mode", "onCreateView: " + match.getGame_mode() + " " + match.getRegion() + " ");
            game_mode.setText(typedArray.getText(match.getGame_mode()).toString());
            typedArray = getContext().getResources().obtainTypedArray(R.array.region);
            region.setText(typedArray.getText(match.getRegion()).toString());
            typedArray.recycle();
            radiant_score.setText(String.valueOf(match.getRadiant_score()));
            dire_score.setText(String.valueOf(match.getDire_score()));
            match_id.setText(String.valueOf(match.getMatch_id()));
            time.setText(Tools.getBefore(match.getStart_time()));
            duration.setText(Tools.getTime(match.getDuration()));
            final RecyclerView recyclerView = view.findViewById(R.id.players);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), match);
            recyclerView.setAdapter(playerAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            final RecyclerView recyclerView1 = view.findViewById(R.id.ability_builds);
            RecyclerView bp = view.findViewById(R.id.ban_pick);
            final List<Abilities> abilities = new ArrayList<>();
            if (match.getPlayers().get(0).getAbility_upgrades_arr() != null) {
                new Thread(() -> {
                    List<Cast> bplist = new ArrayList<>();
                    if (match.getPicks_bans() != null) {
                        List<Cast> radiant_pick = new ArrayList<>();
                        List<Cast> dire_ban = new ArrayList<>();
                        List<Cast> dire_pick = new ArrayList<>();
                        for (Match.BP bp1 : match.getPicks_bans()) {
                            if (bp1.getTeam() == 0 && !bp1.isIs_pick())
                                bplist.add(new Cast(bp1.getOrder(), "hero_" + bp1.getHero_id(), 17, bp1.isIs_pick()));
                            else if (bp1.getTeam() == 0 && bp1.isIs_pick())
                                radiant_pick.add(new Cast(bp1.getOrder(), "hero_" + bp1.getHero_id(), 17, bp1.isIs_pick()));
                            else if (bp1.getTeam() == 1 && !bp1.isIs_pick())
                                dire_ban.add(new Cast(bp1.getOrder(), "hero_" + bp1.getHero_id(), 17, bp1.isIs_pick()));
                            else if (bp1.getTeam() == 1 && bp1.isIs_pick())
                                dire_pick.add(new Cast(bp1.getOrder(), "hero_" + bp1.getHero_id(), 17, bp1.isIs_pick()));
                        }
                        bplist.addAll(radiant_pick);
                        bplist.add(new Cast(0, "", 6));
                        bplist.addAll(dire_ban);
                        bplist.addAll(dire_pick);
                        radiant_pick.clear();
                        dire_pick.clear();
                        dire_ban.clear();
                    }
                    final int ABILITY = 1;
                    final int NULL_ABILITY = 3;
                    final int TALENT = 2;
                    final int HEADER = -1;
                    final int PLAYER = -2;
                    final int LEVEL = -3;
                    abilities.add(new Abilities(0, "", 0, PLAYER));
                    for (int i = 0; i < 25; i++) {
                        abilities.add(new Abilities(0, i + 1 + "", 0, LEVEL));
                    }
                    for (Match.PPlayer p : match.getPlayers()) {
                        abilities.add(new Abilities(Tools.getResId("hero_" + p.getHero_id(), R.drawable.class), p.getName() != null ? p.getName() : p.getPersonaname() == null ? getString(R.string.anonymous) : p.getPersonaname()
                                , Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class), HEADER));
                        List<Integer> ability_upgrades_arr = p.getAbility_upgrades_arr();
                        if (ability_upgrades_arr.size() < 17) {
                            for (int i = 0; i < ability_upgrades_arr.size(); i++) {
                                int id = ability_upgrades_arr.get(i);
                                int resid = Tools.getResId("ability_" + id, R.drawable.class);
                                if (resid == 0) {
                                    abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                                } else {
                                    abilities.add(new Abilities(id, "", resid, ABILITY));
                                }
                            }
                            for (int i = 0; i < 25 - ability_upgrades_arr.size(); i++) {
                                abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            }
                        } else if (ability_upgrades_arr.size() < 18) {
                            for (int i = 0; i < ability_upgrades_arr.size() - 1; i++) {
                                int id = ability_upgrades_arr.get(i);
                                int resid = Tools.getResId("ability_" + id, R.drawable.class);
                                if (resid == 0) {
                                    abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                                } else {
                                    abilities.add(new Abilities(id, "", resid, ABILITY));
                                }
                            }
                            abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            int id = ability_upgrades_arr.get(ability_upgrades_arr.size() - 1);
                            int resid = Tools.getResId("ability_" + id, R.drawable.class);
                            if (resid == 0) {
                                abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                            } else {
                                abilities.add(new Abilities(id, "", resid, ABILITY));
                            }
                            for (int i = 0; i < 25 - 18; i++) {
                                abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            }
                        } else if (ability_upgrades_arr.size() < 19) {
                            for (int i = 0; i < ability_upgrades_arr.size() - 2; i++) {
                                int id = ability_upgrades_arr.get(i);
                                int resid = Tools.getResId("ability_" + id, R.drawable.class);
                                if (resid == 0) {
                                    abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                                } else {
                                    abilities.add(new Abilities(id, "", resid, ABILITY));
                                }
                            }
                            abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            int id = ability_upgrades_arr.get(ability_upgrades_arr.size() - 2);
                            int resid = Tools.getResId("ability_" + id, R.drawable.class);
                            if (resid == 0) {
                                abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                            } else {
                                abilities.add(new Abilities(id, "", resid, ABILITY));
                            }
                            abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            id = ability_upgrades_arr.get(ability_upgrades_arr.size() - 1);
                            resid = Tools.getResId("ability_" + id, R.drawable.class);
                            if (resid == 0) {
                                abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                            } else {
                                abilities.add(new Abilities(id, "", resid, ABILITY));
                            }
                            for (int i = 0; i < 25 - 20; i++) {
                                abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            }
                        } else if (ability_upgrades_arr.size() == 19) {
                            for (int i = 0; i < ability_upgrades_arr.size() - 3; i++) {
                                int id = ability_upgrades_arr.get(i);
                                int resid = Tools.getResId("ability_" + id, R.drawable.class);
                                if (resid == 0) {
                                    abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                                } else {
                                    abilities.add(new Abilities(id, "", resid, ABILITY));
                                }
                            }
                            abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            int id = ability_upgrades_arr.get(ability_upgrades_arr.size() - 3);
                            int resid = Tools.getResId("ability_" + id, R.drawable.class);
                            if (resid == 0) {
                                abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                            } else {
                                abilities.add(new Abilities(id, "", resid, ABILITY));
                            }
                            abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            id = ability_upgrades_arr.get(ability_upgrades_arr.size() - 2);
                            resid = Tools.getResId("ability_" + id, R.drawable.class);
                            if (resid == 0) {
                                abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                            } else {
                                abilities.add(new Abilities(id, "", resid, ABILITY));
                            }
                            for (int i = 0; i < 24 - 20; i++) {
                                abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            }
                            id = ability_upgrades_arr.get(ability_upgrades_arr.size() - 1);
                            resid = Tools.getResId("ability_" + id, R.drawable.class);
                            if (resid == 0) {
                                abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                            } else {
                                abilities.add(new Abilities(id, "", resid, ABILITY));
                            }
                        } else if (p.getAbility_upgrades_arr().size() < 25) {
                            for (int i = 0; i < ability_upgrades_arr.size(); i++) {
                                int id = ability_upgrades_arr.get(i);
                                int resid = Tools.getResId("ability_" + id, R.drawable.class);
                                if (resid == 0) {
                                    abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                                } else {
                                    abilities.add(new Abilities(id, "", resid, ABILITY));
                                }
                            }
                            for (int i = 0; i < 25 - p.getAbility_upgrades_arr().size(); i++) {
                                abilities.add(new Abilities(0, "", 0, NULL_ABILITY));
                            }
                        } else {
                            for (int i = 0; i < ability_upgrades_arr.size(); i++) {
                                int id = ability_upgrades_arr.get(i);
                                int resid = Tools.getResId("ability_" + id, R.drawable.class);
                                if (resid == 0) {
                                    abilities.add(new Abilities(id, MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(id))).getDname(), R.drawable.talent_tree, TALENT));
                                } else {
                                    abilities.add(new Abilities(id, "", resid, ABILITY));
                                }
                            }
                        }
                    }
//                    for (int j = 0; j < 26; j++) {
//                        int t = 0;
//                        for (int i = 0; i <= match.getPlayers().size(); i++) {
//                            abilities_modify.add(abilities.get(t + j));
//                            t += 26;
//                        }
//                    }
                    view.post(() -> {
                        if (match.getPicks_bans() != null) {
                            bp.setVisibility(View.VISIBLE);
                            view.findViewById(R.id.bp1).setVisibility(View.VISIBLE);
                            GridLayoutManager bpManager = new GridLayoutManager(getContext(), 6, GridLayoutManager.VERTICAL, false);
                            CastAdapter bpAdapter = new CastAdapter(getContext(), bplist);
                            bp.setLayoutManager(bpManager);
                            bp.setAdapter(bpAdapter);
                            bp.addItemDecoration(new GridItemDecoration());
                        }
//                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), match.getPlayers().size()+1, GridLayoutManager.HORIZONTAL, false);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 30, GridLayoutManager.VERTICAL, false);
                        final AbilityBuildAdapter abilityBuildAdapter = new AbilityBuildAdapter(getContext(), abilities);
                        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                switch (abilityBuildAdapter.getItemViewType(position)) {
                                    case HEADER:
                                    case PLAYER:
                                        return 5;
                                    default:
                                        return 1;
                                }
                            }
                        });
                        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                                    Fresco.getImagePipeline().resume();
                                else Fresco.getImagePipeline().pause();
                            }
                        });
                        recyclerView1.setLayoutManager(gridLayoutManager);
                        recyclerView1.setAdapter(abilityBuildAdapter);
                        recyclerView1.setNestedScrollingEnabled(false);
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
                        Intent intent = new Intent("loaded");
                        localBroadcastManager.sendBroadcast(intent);
                    });
                }).start();
            } else {
                view.findViewById(R.id.ab).setVisibility(View.GONE);
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()));
                Intent intent = new Intent("loaded");
                localBroadcastManager.sendBroadcast(intent);
            }
        }
        return view;
    }

}
