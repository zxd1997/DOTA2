package com.example.zxd1997.dota2.Fragments.Main;


import android.content.res.TypedArray;
import android.os.Bundle;
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
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeroesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroesFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    private final SparseArray<Hero> heroStats = MainActivity.heroStats;
    private final SparseArray<String> heroes = new SparseArray<>();

    public HeroesFragment() {
        // Required empty public constructor
    }

    public static HeroesFragment newInstance() {
        return new HeroesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_heroes, container, false);
        recyclerView = view.findViewById(R.id.heroes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    Fresco.getImagePipeline().resume();
                else Fresco.getImagePipeline().pause();
            }
        });
        TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.heroes);
        for (int i = 0; i < heroStats.size(); i++) {
            Hero hero = heroStats.valueAt(i);
            heroes.append(hero.getId(), typedArray.getString(hero.getId()));
        }
        typedArray.recycle();
        HeroesAdapter heroesAdapter = new HeroesAdapter(heroes, getActivity());
        recyclerView.setAdapter(heroesAdapter);
        return view;
    }

}
