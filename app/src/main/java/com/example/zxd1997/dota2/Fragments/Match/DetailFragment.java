package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Adapters.KillsAdapter;
import com.example.zxd1997.dota2.Beans.Content;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            for (Match.PPlayer i : match.getPlayers()) {
                Log.d("name", "onCreateView: " + i.getPersonaname());
                for (Map.Entry<String, Integer> e : i.getDamage_inflictor().entrySet()) {
                    Log.d("damage", "onCreateView: " + e.getKey() + " " + e.getValue());
                }
            }
            List<Content> contents = new ArrayList<>();
            List<Content> contents1 = new ArrayList<>();
            contents.add(new Content(true, -1, R.string.kills));
            contents.add(new Content(true, -2, 0));
            contents1.add(new Content(true, -1, R.string.damage));
            contents1.add(new Content(true, -2, 0));
            for (int i = 5; i < match.getPlayers().size(); i++) {
                int color = Objects.requireNonNull(getContext()).getResources().getColor(getContext().getResources().getIdentifier("slot_" + match.getPlayers().get(i).getPlayer_slot(), "color", getContext().getPackageName()));
                Log.d("color", "onCreateView: " + color);
                contents.add(new Content(true, match.getPlayers().get(i).getHero_id(), color));
                contents1.add(new Content(true, match.getPlayers().get(i).getHero_id(), color));
            }
            for (int i = 0; i < 5; i++) {
                int color = getContext().getResources().getColor(getContext().getResources().getIdentifier("slot_" + match.getPlayers().get(i).getPlayer_slot(), "color", getContext().getPackageName()));
                contents.add(new Content(true, match.getPlayers().get(i).getHero_id(), color));
                contents1.add(new Content(true, match.getPlayers().get(i).getHero_id(), color));
                Map<String, Integer> killed = match.getPlayers().get(i).getKilled();
                Map<String, Integer> killed_by = match.getPlayers().get(i).getKilled_by();
                Map<String, Integer> damage = match.getPlayers().get(i).getDamage();
                Map<String, Integer> damage_taken = match.getPlayers().get(i).getDamage_taken();
                for (int j = 5; j < match.getPlayers().size(); j++) {
                    String name = null;
                    for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
                        if (entry.getValue().getId() == match.getPlayers().get(j).getHero_id()) {
                            name = entry.getKey();
                        }
                    }
                    Log.d("hero", "KillsAdapter: " + name);
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
                    contents.add(new Content(false, t));
                    t = new SpannableStringBuilder();
                    DecimalFormat df = new DecimalFormat("0.0");
                    kk = new SpannableString(dam > 1 ? df.format(dam) + "k" : (int) (dam * 1000) + "");
                    kk.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.win)), 0, kk.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(kk);
                    t.append("/");
                    dd = new SpannableString(dam_t >= 1 ? df.format(dam_t) + "k" : (int) (dam_t * 1000) + "");
                    dd.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.lose)), 0, dd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(dd);
                    contents1.add(new Content(false, t));
                }
            }
            RecyclerView kill = view.findViewById(R.id.kills_recycler);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
            contents.addAll(contents1);
            final KillsAdapter killsAdapter = new KillsAdapter(getContext(), contents);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return killsAdapter.getItemViewType(position) == 2 ? 6 : 1;
                }
            });
            kill.setLayoutManager(gridLayoutManager);
            kill.setAdapter(killsAdapter);
            kill.setNestedScrollingEnabled(false);
            RecyclerView d_detail = view.findViewById(R.id.ddetail);
            d_detail.setNestedScrollingEnabled(false);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 20);
            final CastAdapter castAdapter = new CastAdapter(getContext(), match.getPlayers(), 0);
            gridLayoutManager1.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (castAdapter.getItemViewType(position)) {
                        case -1:
                        case 9:
                        case 10:
                            return 20;
                        case 4:
                            return 1;
                        case 5:
                            return 3;
                        case 6:
                            return 20;
                        default:
                            return 2;
                    }
                }
            });
            d_detail.setLayoutManager(gridLayoutManager1);
            d_detail.setAdapter(castAdapter);
        }
        return view;
    }

}