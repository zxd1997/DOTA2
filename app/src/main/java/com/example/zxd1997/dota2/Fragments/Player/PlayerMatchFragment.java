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
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.RecentMatch;
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
 * Use the {@link PlayerMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerMatchFragment extends Fragment {
    final int NUMBER = 20;
    final int MATCHES = 20;
    final int UPDATE = 21;
    int offset = 0;
    RecyclerView recyclerView;
    Player player;
    SwipeRefreshLayout swipeRefreshLayout;
    MatchesAdapter matchesAdapter;
    List<MatchHero> matches = new ArrayList<>();

    public PlayerMatchFragment() {
        // Required empty public constructor
    }

    public static PlayerMatchFragment newInstance() {
        return new PlayerMatchFragment();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (getContext() != null) {
                switch (msg.what) {
                    case MATCHES: {
                        matches.clear();
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                        for (JsonElement e : jsonArray) {
                            RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                            recentMatch.setType(1);
//                        if (recentMatch.getGame_mode() != 19)
                            matches.add(recentMatch);
                        }
                        matchesAdapter.setHasfoot(true);
                        matchesAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    }
                    case UPDATE: {
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                        if (jsonArray.size() == 0) {
                            matchesAdapter.setHasfoot(false);
                        }
                        int t = matches.size();
                        for (JsonElement e : jsonArray) {
                            RecentMatch recentMatch = new Gson().fromJson(e, RecentMatch.class);
                            recentMatch.setType(1);
//                        if (recentMatch.getGame_mode() != 19)
                            matches.add(recentMatch);
                        }
                        matchesAdapter.notifyItemRangeChanged(t, matchesAdapter.getItemCount());
                    }
                }
            }
            return true;
        }
    });

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
            matchesAdapter = new MatchesAdapter(getContext(), matches);
            recyclerView.setAdapter(matchesAdapter);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?offset=" + offset + "&limit=" + (offset + NUMBER), handler, MATCHES);
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setColorSchemeResources(R.color.lose);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    matchesAdapter.setHasfoot(false);
                    swipeRefreshLayout.setRefreshing(true);
                    offset = 0;
                    OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?offset=" + offset + "&limit=" + NUMBER, handler, MATCHES);
                }
            });
            matchesAdapter.setHasfoot(false);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && matchesAdapter.isHasfoot()) {
                        Fresco.getImagePipeline().resume();
                        if (linearLayoutManager.findLastVisibleItemPosition() == matchesAdapter.getItemCount() - 1) {
                            offset += NUMBER;
                            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/matches" + "?offset=" + offset + "&limit=" + NUMBER, handler, UPDATE);
                        }
                    } else Fresco.getImagePipeline().pause();
                }
            });
        }
        return view;
    }

}
