package com.example.zxd1997.dota2.Fragments.Main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Adapters.TabFragmentAdapter;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Views.MyViewPager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class HeroesAndItemsFragment extends Fragment {
    private final int HEROSTATS = 42;
    private final SparseArray<Hero> heroStats = MainActivity.heroStats;
    private RecyclerView recyclerView;
    private View view;

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj.toString().contains("rate limit exceeded")) {
                Toast.makeText(getContext(), R.string.api_rate, Toast.LENGTH_LONG).show();
            } else
                switch (msg.what) {
                    case HEROSTATS: {
                        final String json = msg.obj.toString();
                        new Thread(() -> {
                            JsonParser parser = new JsonParser();
                            JsonArray jsonArray = parser.parse(json).getAsJsonArray();
                            for (JsonElement e : jsonArray) {
                                Hero hero = new Gson().fromJson(e, Hero.class);
                                heroStats.append(hero.getId(), hero);
                            }
                            view.post(() -> {
                                List<Fragment> fragments = new ArrayList<>();
                                fragments.add(HeroesFragment.newInstance());
                                fragments.add(DataFragment.newInstance());
                                fragments.add(ItemsFragment.newInstance());
                                fragments.add(GameOthersFragment.newInstance());
                                MyViewPager viewPager = view.findViewById(R.id.hero_item_pager);
                                TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), fragments);
                                TabLayout tabLayout = view.findViewById(R.id.tabs);
                                viewPager.setAdapter(tabFragmentAdapter);
                                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
                                viewPager.setVisibility(View.VISIBLE);
                            });
                        }).start();
//                    for (int i=0;i<heroStats.size();i++){
//                        Log.d(TAG, "handleMessage: "+heroStats.keyAt(i)+" "+heroStats.valueAt(i).getLocalized_name());
//                    }
                        break;
                    }
                }
            return true;
        }
    });

    public HeroesAndItemsFragment() {
    }

    public static HeroesAndItemsFragment newInstance() {
        return new HeroesAndItemsFragment();
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
        view = inflater.inflate(R.layout.fragment_hero_and_items, container, false);
        if (heroStats.size() == 0) {
            OKhttp.getFromService("https://api.opendota.com/api/heroStats", handler, HEROSTATS);
        } else {
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(HeroesFragment.newInstance());
            fragments.add(DataFragment.newInstance());
            fragments.add(ItemsFragment.newInstance());
            fragments.add(GameOthersFragment.newInstance());
            MyViewPager viewPager = view.findViewById(R.id.hero_item_pager);
            TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), fragments);
            TabLayout tabLayout = view.findViewById(R.id.tabs);
            viewPager.setAdapter(tabFragmentAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            viewPager.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
