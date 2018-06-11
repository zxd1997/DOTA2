package com.example.zxd1997.dota2.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.AbilityBuildAdapter;
import com.example.zxd1997.dota2.Adapters.PlayerAdapter;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;

import java.util.Objects;

public class OverviewFragment extends Fragment {
    private Match match;

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
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        } else {
            Log.d("fragment", "onCreateView: " + match.getMatch_id());
            TextView gamemode = view.findViewById(R.id.gamemode);
            TextView duration = view.findViewById(R.id.duration);
            TextView radiant_score = view.findViewById(R.id.radiant_score);
            TextView dire_score = view.findViewById(R.id.dire_score);
            TextView time = view.findViewById(R.id.start_time);
            TextView match_id = view.findViewById(R.id.match_id);
            TextView region = view.findViewById(R.id.region);
            TextView skill = view.findViewById(R.id.skill);
            @SuppressLint("Recycle") TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.skills);
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
            Log.d("mode", "onCreateView: " + match.getGame_mode() + " " + match.getRegion() + " ");
            gamemode.setText(typedArray.getText(match.getGame_mode()).toString());
            typedArray = getContext().getResources().obtainTypedArray(R.array.region);
            region.setText(typedArray.getText(match.getRegion()).toString());
            radiant_score.setText(String.valueOf(match.getRadiant_score()));
            dire_score.setText(String.valueOf(match.getDire_score()));
            match_id.setText(String.valueOf(match.getMatch_id()));
            long now = System.currentTimeMillis() / 1000;
            long year = (now - match.getStart_time()) / (3600 * 24 * 30 * 12);
            long month = (now - match.getStart_time()) / (3600 * 24 * 30);
            long day = (now - match.getStart_time()) / (3600 * 24);
            long hour = (now - match.getStart_time()) / 3600;
            long minute = (now - match.getStart_time()) / 60;
            String tmp;
            if (year > 0) {
                tmp = year + " Years ago";
            } else if (month > 0) {
                tmp = month + " Months ago";
            } else if (day > 0) {
                tmp = day + " Days ago";
            } else if (hour > 0) {
                tmp = hour + " Hours ago";
            } else if (minute > 3) {
                tmp = minute + "Minutes ago";
            } else tmp = "Just Now";
            time.setText(tmp);
            int h = match.getDuration() / 3600;
            int m = match.getDuration() % 3600 / 60;
            int s = match.getDuration() % 3600 % 60;
            Log.d("time", "onCreateView: " + h + ":" + m + ":" + s);
            StringBuilder t = new StringBuilder();
            if (h > 0) {
                t.append((h < 10) ? "0" + h + ":" : h + ":");
            }
            t.append((m < 10) ? "0" + m + ":" : m + ":");
            t.append((s < 10) ? "0" + s : s);
            duration.setText(t);
            RecyclerView recyclerView = view.findViewById(R.id.players);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), match);
            recyclerView.setAdapter(playerAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            RecyclerView recyclerView1 = view.findViewById(R.id.ability_builds);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 30);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position % 26 == 0) return 5;
                    return 1;
                }
            });
            recyclerView1.setLayoutManager(gridLayoutManager);
            AbilityBuildAdapter abilityBuildAdapter = new AbilityBuildAdapter(getContext(), match);
            recyclerView1.setAdapter(abilityBuildAdapter);
            recyclerView1.setNestedScrollingEnabled(false);
        }
        return view;
    }

}
