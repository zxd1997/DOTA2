package com.example.zxd1997.dota2.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EconomyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EconomyFragment extends Fragment {
    Match match;

    public EconomyFragment() {
        // Required empty public constructor
    }

    public static EconomyFragment newInstance() {
        EconomyFragment fragment = new EconomyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MatchActivity activity = (MatchActivity) getActivity();
        match = activity.getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
            getActivity().finish();
        } else {

        }
        return inflater.inflate(R.layout.fragment_economy, container, false);
    }

}
