package com.example.zxd1997.dota2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.R;

public class ItemsFragment extends Fragment {


    public ItemsFragment() {
    }

    public static ItemsFragment newInstance() {
        ItemsFragment fragment = new ItemsFragment();
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
        View view=inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }


}
