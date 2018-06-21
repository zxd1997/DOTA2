package com.example.zxd1997.dota2.Fragments.Player;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.PlayedHero;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerHeroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerHeroFragment extends Fragment {

    final int HEROES = 22;
    Player player;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MatchesAdapter matchesAdapter;
    List<MatchHero> hero_matches = new ArrayList<>();
    public PlayerHeroFragment() {
        // Required empty public constructor
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HEROES: {
                    hero_matches.clear();
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    for (JsonElement e : jsonArray) {
                        PlayedHero playedHero = new Gson().fromJson(e, PlayedHero.class);
                        playedHero.setType(2);
                        hero_matches.add(playedHero);
                    }
                    matchesAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                }
            }
            return true;
        }
    });
    public static PlayerHeroFragment newInstance() {
        return new PlayerHeroFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_match, container, false);
        PlayerActivity activity = (PlayerActivity) getActivity();
        player = Objects.requireNonNull(activity).getPlayer();
        if (player == null) {
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            recyclerView = view.findViewById(R.id.player_matches);
            swipeRefreshLayout = view.findViewById(R.id.swipe_player);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            matchesAdapter = new MatchesAdapter(getContext(), hero_matches);
            recyclerView.setAdapter(matchesAdapter);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/heroes", handler, HEROES);
            swipeRefreshLayout.setRefreshing(true);
            matchesAdapter.setHasfoot(false);
            swipeRefreshLayout.setColorSchemeResources(R.color.lose);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/heroes", handler, HEROES);
                }
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        Fresco.getImagePipeline().resume();
                    } else Fresco.getImagePipeline().pause();
                }
            });
        }
        return view;
    }
}
