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
import android.widget.Toast;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.PlayedHero;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.Ranking;
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
    private final int RANKING = 23;
    private final int HEROES = 22;
    private final List<MatchHero> hero_matches = new ArrayList<>();
    private Player player;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MatchesAdapter matchesAdapter;

    public PlayerHeroFragment() {
        // Required empty public constructor
    }

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj.toString().contains("rate limit exceeded")) {
                Toast.makeText(getContext(), R.string.api_rate, Toast.LENGTH_LONG).show();
            } else if (getContext() != null) {
                switch (msg.what) {
                    case HEROES: {
                        MatchHero m = new MatchHero();
                        m.setTitle(getString(R.string.h_p));
                        m.setType(5);
                        hero_matches.add(m);
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                        for (JsonElement e : jsonArray) {
                            PlayedHero playedHero = new Gson().fromJson(e, PlayedHero.class);
                            playedHero.setType(2);
                            hero_matches.add(playedHero);
                            matchesAdapter.notifyItemInserted(matchesAdapter.getItemCount() - 1);
                        }
                        matchesAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    }
                    case RANKING: {
                        hero_matches.clear();
                        MatchHero m = new MatchHero();
                        m.setTitle(getString(R.string.h_r));
                        m.setType(5);
                        hero_matches.add(m);
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                        int i = 0;
                        for (JsonElement e : jsonArray) {
                            if (i < 10) {
                                Ranking rankings = new Gson().fromJson(e, Ranking.class);
                                rankings.setType(4);
                                hero_matches.add(rankings);
                            } else break;
                            i++;
                        }
                        matchesAdapter.notifyDataSetChanged();
                        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/heroes", handler, HEROES);
                        break;
                    }
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
            matchesAdapter = new MatchesAdapter(getActivity(), hero_matches);
            recyclerView.setAdapter(matchesAdapter);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/rankings?limit=10", handler, RANKING);
            swipeRefreshLayout.setRefreshing(true);
            matchesAdapter.setHasfoot(false);
            swipeRefreshLayout.setColorSchemeResources(R.color.lose);
            swipeRefreshLayout.setOnRefreshListener(() -> {
                swipeRefreshLayout.setRefreshing(true);
                OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/rankings?limit=10", handler, RANKING);
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
