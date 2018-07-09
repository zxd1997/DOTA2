package com.example.zxd1997.dota2.Fragments.Match;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.R;

import java.util.ArrayList;


public class TeamFIghtDialogFragment extends BottomSheetDialogFragment {

    public static TeamFIghtDialogFragment newInstance(ArrayList<Cast> contents) {
        final TeamFIghtDialogFragment fragment = new TeamFIghtDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList("contents", contents);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamfight_list_dialog, container, false);
        assert this.getArguments() != null;
        ArrayList<Cast> contents = this.getArguments().getParcelableArrayList("contents");
        RecyclerView teamfight_list = view.findViewById(R.id.team_fight_list);
        final CastAdapter castAdapter = new CastAdapter(getContext(), contents);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 8);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (castAdapter.getItemViewType(position) == -1 || castAdapter.getItemViewType(position) == 11 || castAdapter.getItemViewType(position) == 12 || castAdapter.getItemViewType(position) == 15 || castAdapter.getItemViewType(position) == 16) ? 8 : 1;
            }
        });
//      teamFight.map.setPoints(points);
//      teamFight.map.invalidate();
        teamfight_list.setLayoutManager(gridLayoutManager);
        teamfight_list.setAdapter(castAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

}
