package com.example.zxd1997.dota2.Fragments.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameOthersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameOthersFragment extends Fragment {

    public GameOthersFragment() {
        // Required empty public constructor
    }

    public static GameOthersFragment newInstance() {
        GameOthersFragment fragment = new GameOthersFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_others, container, false);
    }

}
