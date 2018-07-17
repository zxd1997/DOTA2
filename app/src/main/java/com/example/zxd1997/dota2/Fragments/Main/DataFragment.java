package com.example.zxd1997.dota2.Fragments.Main;


import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Adapters.HeroDataAdapter;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {
    HeroDataAdapter heroDataAdapter;
    int type = 1;
    int order = 1;
    List<Hero> heroes = new ArrayList<>();

    //    SparseArray<Hero> heroes=new SparseArray<Hero>(MainActivity.heroStats);
    public DataFragment() {
        // Required empty public constructor
    }


    public static DataFragment newInstance() {
        return new DataFragment();
    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_data, container, false);
        Spinner spinner = view.findViewById(R.id.spinner1);
        String[] arr = new String[10];
        TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.tier_names);
        for (int i = 1; i <= 9; i++) {
            arr[i] = typedArray.getString(i);
        }
        arr[0] = typedArray.getString(10);
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr));
        typedArray.recycle();
        Spinner spinner1 = view.findViewById(R.id.spinner2);
        String[] arr1 = {getString(R.string.pick), getString(R.string.winrate2)};
        spinner1.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr1));
        RecyclerView recyclerView = view.findViewById(R.id.hero_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new Thread(() -> {
            for (int i = 0; i < MainActivity.heroStats.size(); i++)
                heroes.add(MainActivity.heroStats.valueAt(i));
            Collections.sort(heroes, (o1, o2) -> o2.getTotal_picks() - o1.getTotal_picks());
            view.post(() -> {
                heroDataAdapter = new HeroDataAdapter(1, getContext(), heroes);
                recyclerView.setAdapter(heroDataAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        heroDataAdapter.setType(position + 1);
                        type = position + 1;
                        sort();
                        heroDataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        order = position + 1;
                        sort();
                        heroDataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            });
        }).start();
        return view;
    }

    void sort() {
        switch (type) {
            case 1: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getTotal_picks() - o1.getTotal_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getTotal_wins() / o2.getTotal_picks() * 100000 - (float) o1.getTotal_wins() / o1.getTotal_picks() * 100000));
                break;
            }
            case 2: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getHerald_picks() - o1.getHerald_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getHerald_wins() / o2.getHerald_picks() * 100000 - (float) o1.getHerald_wins() / o1.getHerald_picks() * 100000));
                break;
            }
            case 3: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getGuardian_picks() - o1.getGuardian_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getGuardian_wins() / o2.getGuardian_picks() * 100000 - (float) o1.getGuardian_wins() / o1.getGuardian_picks() * 100000));
                break;
            }
            case 4: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getCrusader_picks() - o1.getCrusader_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getCrusader_wins() / o2.getCrusader_picks() * 100000 - (float) o1.getCrusader_wins() / o1.getCrusader_picks() * 100000));
                break;
            }
            case 5: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getArchon_picks() - o1.getArchon_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getArchon_wins() / o2.getArchon_picks() * 100000 - (float) o1.getArchon_wins() / o1.getArchon_picks() * 100000));
                break;
            }
            case 6: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getLegend_picks() - o1.getLegend_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getLegend_wins() / o2.getLegend_picks() * 100000 - (float) o1.getLegend_wins() / o1.getLegend_picks() * 100000));
                break;
            }
            case 7: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getAncient_picks() - o1.getAncient_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getAncient_wins() / o2.getAncient_picks() * 100000 - (float) o1.getAncient_wins() / o1.getAncient_picks() * 100000));
                break;
            }
            case 8: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getDivine_picks() - o1.getDivine_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getDivine_wins() / o2.getDivine_picks() * 100000 - (float) o1.getDivine_wins() / o1.getDivine_picks() * 100000));
                break;
            }
            case 9: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getImmortal_picks() - o1.getImmortal_picks());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getImmortal_wins() / o2.getImmortal_picks() * 100000 - (float) o1.getImmortal_wins() / o1.getImmortal_picks() * 100000));
                break;
            }
            case 10: {
                if (order == 1)
                    Collections.sort(heroes, (o1, o2) -> o2.getPro_pick() - o1.getPro_pick());
                else
                    Collections.sort(heroes, (o1, o2) -> (int) ((float) o2.getPro_win() / o2.getPro_pick() * 100000) - (int) ((float) o1.getPro_win() / o1.getPro_pick() * 100000));
                break;
            }
        }
    }
}
