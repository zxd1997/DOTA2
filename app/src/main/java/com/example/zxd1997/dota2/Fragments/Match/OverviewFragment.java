package com.example.zxd1997.dota2.Fragments.Match;

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
import com.example.zxd1997.dota2.Utils.Tools;

import java.util.Objects;

public class OverviewFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        } else {
            Log.d("fragment", "onCreateView: " + match.getMatch_id());
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
            Log.d("mode", "onCreateView: " + match.getGame_mode() + " " + match.getRegion() + " ");
            game_mode.setText(typedArray.getText(match.getGame_mode()).toString());
            typedArray = getContext().getResources().obtainTypedArray(R.array.region);
            region.setText(typedArray.getText(match.getRegion()).toString());
            typedArray.recycle();
            radiant_score.setText(String.valueOf(match.getRadiant_score()));
            dire_score.setText(String.valueOf(match.getDire_score()));
            match_id.setText(String.valueOf(match.getMatch_id()));
            time.setText(Tools.getBefore(match.getStart_time()));
            duration.setText(Tools.getTime(match.getDuration()));
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