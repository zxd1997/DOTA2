package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.Beans.CastHeader;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.GridItemDecoration;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.Tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailFragment extends Fragment {
    private final List<Cast> cc1 = new ArrayList<>();
    private final List<Cast> cc2 = new ArrayList<>();
    private final int ABILITY = 0;
    private final int ITEM = 1;
    private final int HEADER = -1;
    private final int HERO1 = 7;
    private final int ARROW = 4;
    private final int ENTER = 6;
    private final int PLAYER_HEADER_DAMAGE = 10;
    private final List<Cast> casts = new ArrayList<>();
    private RecyclerView d_detail;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (getContext() != null) {
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 48);
                final CastAdapter castAdapter = new CastAdapter(getContext(), casts);
                gridLayoutManager1.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        switch (castAdapter.getItemViewType(position)) {
                            case -1:
                            case 9:
                            case 10:
                                return 48;
                            case 4:
                                return 3;
                            case 5:
                                return 9;
                            case 13:
                            case 14:
                                return 8;
                            case 6:
                                return 48;
                            default:
                                return 6;
                        }
                    }
                });
                d_detail.setLayoutManager(gridLayoutManager1);
                d_detail.addItemDecoration(new GridItemDecoration());
                d_detail.setAdapter(castAdapter);
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()));
                Intent intent = new Intent("loaded");
                localBroadcastManager.sendBroadcast(intent);
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

    public DetailFragment() {
        // Required empty public constructor
    }

    private Match match;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        match = Objects.requireNonNull(activity).getMatch();
        if (match == null) {
//            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            d_detail = view.findViewById(R.id.ddetail);
            new Thread(() -> {
                cc1.add(new Cast(Color.BLACK, getString(R.string.kills), -1));
                cc1.add(new Cast(0, "", 13));
                cc2.add(new Cast(Color.BLACK, getString(R.string.damage), -1));
                cc2.add(new Cast(0, "", 13));
                List<Match.PPlayer> players = match.getPlayers();
                for (int i = 5; i < players.size(); i++) {
                    Match.PPlayer p = players.get(i);
                    int color = Objects.requireNonNull(getContext()).getResources().getColor(Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class));
//                        Log.d("color", "onCreateView: " + color);
                    cc1.add(new Cast(color, p.getHero_id() + "", 13));
                    cc2.add(new Cast(color, p.getHero_id() + "", 13));
                }
                for (int i = 0; i < 5; i++) {
                    Match.PPlayer p = players.get(i);
                    int color = Objects.requireNonNull(getContext()).getResources().getColor(Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class));
                    cc1.add(new Cast(color, p.getHero_id() + "", 13));
                    cc2.add(new Cast(color, p.getHero_id() + "", 13));
                    Map<String, Integer> killed = p.getKilled();
                    Map<String, Integer> killed_by = p.getKilled_by();
                    Map<String, Integer> damage = p.getDamage();
                    Map<String, Integer> damage_taken = p.getDamage_taken();
                    for (int j = 5; j < players.size(); j++) {
                        String name = null;
                        for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
                            if (entry.getValue().getId() == players.get(j).getHero_id()) {
                                name = entry.getKey();
                            }
                        }
//                            Log.d("hero", "KillsAdapter: " + name);
                        int k = killed.get(name) == null ? 0 : killed.get(name);
                        int d = killed_by.get(name) == null ? 0 : killed_by.get(name);
                        double dam = damage.get(name) == null ? 0 : (double) damage.get(name) / 1000;
                        double dam_t = damage_taken.get(name) == null ? 0 : (double) damage_taken.get(name) / 1000;
                        SpannableStringBuilder t = new SpannableStringBuilder();
                        SpannableString kk = new SpannableString(k + "");
                        kk.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.win)), 0, kk.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        t.append(kk);
                        t.append("/");
                        SpannableString dd = new SpannableString(d + "");
                        dd.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.lose)), 0, dd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        t.append(dd);
                        cc1.add(new Cast(0, t, 14));
                        t = new SpannableStringBuilder();
                        DecimalFormat df = new DecimalFormat("0.0");
                        kk = new SpannableString(dam > 1 ? df.format(dam) + "k" : (int) (dam * 1000) + "");
                        kk.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.win)), 0, kk.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        t.append(kk);
                        t.append("/");
                        dd = new SpannableString(dam_t >= 1 ? df.format(dam_t) + "k" : (int) (dam_t * 1000) + "");
                        dd.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.lose)), 0, dd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        t.append(dd);
                        cc2.add(new Cast(0, t, 14));
                    }
                }
                casts.addAll(cc1);
                casts.addAll(cc2);
                casts.add(new Cast(Color.BLACK, getString(R.string.damage_detail), -1));
                for (Match.PPlayer p : match.getPlayers()) {
                    CastHeader castHeader = new CastHeader(getContext().getResources().getColor(Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class)),
                            p.getName() != null ? p.getName() : p.getPersonaname() == null || p.getPersonaname().equals("") ? getContext().getResources().getString(R.string.anonymous) : p.getPersonaname(),
                            PLAYER_HEADER_DAMAGE, p.getHero_id(), p.getHero_damage(), 0);
                    casts.add(castHeader);
                    casts.add(new Cast(getContext().getResources().getColor(R.color.win), getContext().getResources().getString(R.string.damage_dealt), HEADER));
                    for (Map.Entry<String, Integer> entry : p.getDamage_inflictor().entrySet()) {
                        if (entry.getKey().equals("null")) {
                            casts.add(new Cast(entry.getValue(), "default_attack", ABILITY, 1));
                            if (p.getDamage_targets() != null) {
                                casts.add(new Cast(0, "", ARROW));
                                int i = 0;
                                for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                                    if (i == 6) {
                                        casts.add(new Cast(0, "", ENTER));
                                        casts.add(new Cast(0, "", 5));
                                        casts.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1, 1));
                                    } else
                                    casts.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                                    i++;
                                }
                                casts.add(new Cast(0, "", ENTER));
                            }
                        } else {
                            boolean f = false;
                            for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                                if (entry1.getValue().equals(entry.getKey())) {
                                    casts.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY, 1));
                                    if (p.getDamage_targets() != null) {
                                        casts.add(new Cast(0, "", ARROW));
                                        int i = 0;
                                        for (Map.Entry<String, Integer> entry2 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                                            if (i == 6) {
                                                casts.add(new Cast(0, "", ENTER));
                                                casts.add(new Cast(0, "", 5));
                                                casts.add(new Cast(entry2.getValue(), "hero_" + MainActivity.heroes.get(entry2.getKey()).getId() + "_icon", HERO1, 1));
                                            } else
                                            casts.add(new Cast(entry2.getValue(), "hero_" + MainActivity.heroes.get(entry2.getKey()).getId() + "_icon", HERO1));
                                            i++;
                                        }
                                        casts.add(new Cast(0, "", ENTER));
                                    }
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                Item item = MainActivity.items.get(entry.getKey());
                                casts.add(new Cast(entry.getValue(), item.getId() + "", ITEM, 1));
                                if (p.getDamage_targets() != null) {
                                    casts.add(new Cast(0, "", ARROW));
                                    int i = 0;
                                    for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                                        if (i == 6) {
                                            casts.add(new Cast(0, "", ENTER));
                                            casts.add(new Cast(0, "", 5));
                                            casts.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1, 1));
                                        } else
                                        casts.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                                        i++;
                                    }
                                    casts.add(new Cast(0, "", ENTER));
                                }
                            }
                        }
                    }
                    int d_t = 0;
                    casts.add(new Cast(getContext().getResources().getColor(R.color.lose), getContext().getResources().getString(R.string.damage_taken), HEADER));
                    int i = 0;
                    for (Map.Entry<String, Integer> entry : p.getDamage_inflictor_received().entrySet()) {
                        d_t += entry.getValue();
                        int t = i % 8 == 0 ? 1 : i % 8 == 7 ? 2 : 0;
                        i++;
                        if (entry.getKey().equals("null")) {
                            casts.add(new Cast(entry.getValue(), "default_attack", ABILITY, t));
                        } else {
                            boolean f = false;
                            for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                                if (entry1.getValue().equals(entry.getKey())) {
                                    casts.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY, t));
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                Item item = MainActivity.items.get(entry.getKey());
                                casts.add(new Cast(entry.getValue(), item.getId() + "", ITEM, t));
                            }
                        }
                    }
                    castHeader.setDamage_in(d_t);
                }
                handler.sendMessage(new Message());
            }).start();
        }
        return view;
    }

}
