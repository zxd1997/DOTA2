package com.example.zxd1997.dota2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeroesFragment extends Fragment {


    public HeroesFragment() {
    }

    public static HeroesFragment newInstance() {
        HeroesFragment fragment = new HeroesFragment();
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
        View view=inflater.inflate(R.layout.fragment_heroes, container, false);
        return view;
    }


}
