package com.example.zxd1997.dota2.Fragments.Main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Adapters.HeroStatAdapter;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.HeroStat;
import com.example.zxd1997.dota2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeroStatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroStatFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "name";

    private int id;


    public HeroStatFragment() {
        // Required empty public constructor
    }

    public static HeroStatFragment newInstance(int param1, String param2) {
        HeroStatFragment fragment = new HeroStatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hero_stat, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.hero_stats);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<HeroStat> heroStats = new ArrayList<>();
        Hero hero = MainActivity.heroStats.get(id);
        heroStats.add(new HeroStat(0, getString(R.string.hero_stats)));
        heroStats.add(new HeroStat(hero.getId(), hero.getTotal_picks(), (double) hero.getTotal_wins() / hero.getTotal_picks() * 100, 10));
        heroStats.add(new HeroStat(hero.getId(), hero.getPro_pick(), hero.getPro_ban(), (double) hero.getPro_win() / hero.getPro_pick() * 100, 9));
        heroStats.add(new HeroStat(hero.getId(), hero.getImmortal_picks(), (double) hero.getImmortal_wins() / hero.getImmortal_picks() * 100, 8));
        heroStats.add(new HeroStat(hero.getId(), hero.getDivine_picks(), (double) hero.getDivine_wins() / hero.getDivine_picks() * 100, 7));
        heroStats.add(new HeroStat(hero.getId(), hero.getAncient_picks(), (double) hero.getAncient_wins() / hero.getAncient_picks() * 100, 6));
        heroStats.add(new HeroStat(hero.getId(), hero.getLegend_picks(), (double) hero.getLegend_wins() / hero.getLegend_picks() * 100, 5));
        heroStats.add(new HeroStat(hero.getId(), hero.getArchon_picks(), (double) hero.getArchon_wins() / hero.getArchon_picks() * 100, 4));
        heroStats.add(new HeroStat(hero.getId(), hero.getCrusader_picks(), (double) hero.getCrusader_wins() / hero.getCrusader_picks() * 100, 3));
        heroStats.add(new HeroStat(hero.getId(), hero.getGuardian_picks(), (double) hero.getGuardian_wins() / hero.getGuardian_picks() * 100, 2));
        heroStats.add(new HeroStat(hero.getId(), hero.getHerald_picks(), (double) hero.getHerald_wins() / hero.getHerald_picks() * 100, 1));
        recyclerView.setAdapter(new HeroStatAdapter(getContext(), heroStats));
        return view;
    }

}
