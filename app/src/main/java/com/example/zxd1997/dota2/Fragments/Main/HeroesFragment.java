package com.example.zxd1997.dota2.Fragments.Main;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Adapters.HeroesAdapter;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Objects;

public class HeroesFragment extends Fragment {
    private final int HEROSTATS = 42;
    private final SparseArray<Hero> heroStats = MainActivity.heroStats;
    private final SparseArray<String> heroes = new SparseArray<>();
    private RecyclerView recyclerView;
    private View view;
    private HeroesAdapter heroesAdapter;
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
                        TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.heroes);
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(json).getAsJsonArray();
                        for (JsonElement e : jsonArray) {
                            Hero hero = new Gson().fromJson(e, Hero.class);
                            heroStats.append(hero.getId(), hero);
                            heroes.append(hero.getId(), typedArray.getString(hero.getId()));
                        }
                        typedArray.recycle();
                        view.post(() -> {
                            heroesAdapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.VISIBLE);
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
        if (heroStats.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            OKhttp.getFromService("https://api.opendota.com/api/heroStats", handler, HEROSTATS);
        }
        heroesAdapter = new HeroesAdapter(heroes, getActivity());
        recyclerView.setAdapter(heroesAdapter);
        return view;
    }
}
