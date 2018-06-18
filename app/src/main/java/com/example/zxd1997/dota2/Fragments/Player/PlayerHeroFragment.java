package com.example.zxd1997.dota2.Fragments.Player;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerHeroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerHeroFragment extends Fragment {


    public PlayerHeroFragment() {
        // Required empty public constructor
    }

    public static PlayerHeroFragment newInstance() {
        return new PlayerHeroFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_match, container, false);
        return view;
    }

}
