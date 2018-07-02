package com.example.zxd1997.dota2.Fragments.Main;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Adapters.HeroesAdapter;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.R;

import java.util.Map;
import java.util.Objects;

public class HeroesFragment extends Fragment {

    public HeroesFragment() {
    }

    public static HeroesFragment newInstance() {
        return new HeroesFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_heroes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.heroes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        SparseArray<String> heroes = new SparseArray<String>();
        TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.heroes);
        for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
            int id = entry.getValue().getId();
            heroes.append(id, typedArray.getString(id));
        }
        typedArray.recycle();
        recyclerView.setAdapter(new HeroesAdapter(heroes, getContext()));
        return view;
    }



}
