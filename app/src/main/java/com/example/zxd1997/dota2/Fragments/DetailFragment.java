package com.example.zxd1997.dota2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;

public class DetailFragment extends Fragment {

    Match match;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(Match match) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("match", match);
        fragment.setArguments(args);
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
        match = (Match) getArguments().getSerializable("match");
        for (Match.PPlayer i : match.getPlayers()) {
            Log.d("name", "onCreateView: " + i.getPersonaname());
        }
        return view;
    }

}
