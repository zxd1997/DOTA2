package com.example.zxd1997.dota2.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.zxd1997.dota2.Adapters.DDetailAdapter;
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

public class DetailFragment extends Fragment {

    Match match;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        match = activity.getMatch();
        if (match == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
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
            contents.add(new Content(true, 0));
            contents1.add(new Content(true, 0));
            for (int i = 5; i < match.getPlayers().size(); i++) {
                contents.add(new Content(true, match.getPlayers().get(i).getHero_id()));
                contents1.add(new Content(true, match.getPlayers().get(i).getHero_id()));
            }
            for (int i = 0; i < 5; i++) {
                contents.add(new Content(true, match.getPlayers().get(i).getHero_id()));
                contents1.add(new Content(true, match.getPlayers().get(i).getHero_id()));
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
            kill.setLayoutManager(new GridLayoutManager(getContext(), 6));
            kill.setAdapter(new KillsAdapter(getContext(), contents));
            RecyclerView damage = view.findViewById(R.id.damage_recycler);
            damage.setLayoutManager(new GridLayoutManager(getContext(), 6));
            damage.setAdapter(new KillsAdapter(getContext(), contents1));
            kill.setNestedScrollingEnabled(false);
            damage.setNestedScrollingEnabled(false);
            RecyclerView ddtail = view.findViewById(R.id.ddetail);
            ddtail.setLayoutManager(new LinearLayoutManager(getContext()));
            ddtail.setNestedScrollingEnabled(false);
            ddtail.setAdapter(new DDetailAdapter(getContext(), match.getPlayers()));
            ddtail.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
        return view;
    }

}
