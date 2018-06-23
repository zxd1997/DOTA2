package com.example.zxd1997.dota2.Fragments.Player;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.Counts;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.Peer;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerOtherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerOtherFragment extends Fragment {
    final static int PEERS = 27;
    final static int COUNTS = 28;
    Player player;
    public static PlayerOtherFragment newInstance() {
        return new PlayerOtherFragment();
    }

    RecyclerView recyclerView;
    MatchesAdapter matchesAdapter;
    List<MatchHero> peers = new ArrayList<>();
    PieChartView pieChartView;
    PieChartData pieChartData;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PEERS: {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                    int i = 0;
                    MatchHero m = new MatchHero();
                    m.setTitle(getString(R.string.palyer_p_w));
                    m.setType(5);
                    peers.add(m);
                    for (JsonElement e : jsonArray) {
                        if (i < 10) {
                            Peer peer = new Gson().fromJson(e, Peer.class);
                            peer.setType(6);
                            peers.add(peer);
                        } else break;
                        i++;
                    }
                    matchesAdapter = new MatchesAdapter(getContext(), peers);
                    matchesAdapter.setHasfoot(false);
                    matchesAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(matchesAdapter);
                    break;
                }
                case COUNTS: {
                    Counts counts = new Gson().fromJson(msg.obj.toString(), Counts.class);
                    List<SliceValue> sliceValues = new ArrayList<>();
                    for (Map.Entry<String, WL> entry : counts.getLobby_type().entrySet()) {
                        WL wl = entry.getValue();
                        SliceValue sliceValue = new SliceValue(wl.getGames());
                        TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.lobby_type_color);
                        sliceValue.setColor(typedArray.getColor(Integer.valueOf(entry.getKey()), 0));
                        typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.lobby_type);
                        sliceValue.setLabel(typedArray.getString(Integer.valueOf(entry.getKey())) + ":" + wl.getGames() + " Matches Winrate:" + Tools.getS((double) wl.getWin() / wl.getGames()));
                        sliceValues.add(sliceValue);
                        typedArray.recycle();
                    }
                    pieChartData = new PieChartData(sliceValues);
                    pieChartData.setHasLabelsOnlyForSelected(true);
                    pieChartView.setPieChartData(pieChartData);
                    pieChartView.setVisibility(View.VISIBLE);
                    break;
                }
            }
            return true;
        }
    });
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_other, container, false);
        PlayerActivity activity = (PlayerActivity) getActivity();
        player = Objects.requireNonNull(activity).getPlayer();
        if (player == null) {
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            recyclerView = view.findViewById(R.id.peers);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && matchesAdapter.isHasfoot()) {
                        Fresco.getImagePipeline().resume();
                    } else Fresco.getImagePipeline().pause();
                }
            });
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/peers", handler, PEERS);
            OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + player.getAccount_id() + "/counts", handler, COUNTS);

            pieChartView = view.findViewById(R.id.pie_chart);
        }
        return view;
    }

}
