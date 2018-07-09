package com.example.zxd1997.dota2.Fragments.Match;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.GridItemDecoration;

import java.util.ArrayList;


public class TeamFightDialogFragment extends BottomSheetDialogFragment {

    public static TeamFightDialogFragment newInstance(ArrayList<Cast> contents) {
        final TeamFightDialogFragment fragment = new TeamFightDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList("contents", contents);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        assert this.getArguments() != null;
        ArrayList<Cast> contents = this.getArguments().getParcelableArrayList("contents");
        RecyclerView teamfight_list = view.findViewById(R.id.ddetail);
        teamfight_list.setTransitionName("teamfight");
        final CastAdapter castAdapter = new CastAdapter(getContext(), contents);
        teamfight_list.addItemDecoration(new GridItemDecoration());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 8);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (castAdapter.getItemViewType(position) == -1 || castAdapter.getItemViewType(position) == 11 || castAdapter.getItemViewType(position) == 12 || castAdapter.getItemViewType(position) == 15 || castAdapter.getItemViewType(position) == 16) ? 8 : 1;
            }
        });
        teamfight_list.setLayoutManager(gridLayoutManager);
        teamfight_list.setAdapter(castAdapter);
        return view;
    }

}
