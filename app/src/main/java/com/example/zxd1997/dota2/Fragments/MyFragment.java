package com.example.zxd1997.dota2.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxd1997.dota2.Adapters.MatchesAdapter;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Okhttp;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {
    LinearLayout linearLayout;
    LinearLayout my;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String id="";
    String tmp;
    Button button1;
    Button button;
    static final int VERIFY=0;
    static final int PLAYER_INFO=1;
    static final int WL=2;
    static final int RECENT_MATCHES=3;
    Player player;
    final String DISCONNECT = "disconnect from id";
    SimpleDraweeView header;
    SimpleDraweeView tier;
    SimpleDraweeView stars;
    TextView textView;
    TextView personaname;
    TextView loccountrycode;
    TextView account_id;
    TextView win;
    TextView lose;
    TextView winrate;
    TextView ranks;
    RecyclerView recyclerView;
    MatchesAdapter matchesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextInputLayout textInputLayout;
    com.example.zxd1997.dota2.Beans.WL wl;
    List<RecentMatch> recentMatches=new ArrayList<>();
    Receiver receiver;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message) {
            Log.d("id", "handleMessage: "+message.obj.toString());
            switch (message.what){
                case VERIFY:{
                    Player t=new Gson().fromJson(message.obj.toString(),Player.class);
                    if (t == null) {
                        Toast.makeText(getContext(),"No Such Player",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        button1.setVisibility(View.VISIBLE);
                    }else{
                        linearLayout.setVisibility(View.GONE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        id=tmp;
                        editor.putString("id",id);
                        editor.commit();
                        Okhttp.getFromService(getString(R.string.api)+getString(R.string.players)+id,handler,PLAYER_INFO);
                    }
                    break;
                }
                case PLAYER_INFO:{
                    player=new Gson().fromJson(message.obj.toString(),Player.class);
                    personaname.setText(player.getPersonaname());
                    loccountrycode.setText(player.getLoccountrycode());
                    account_id.setText(String.valueOf(player.getAccount_id()));
                    header.setImageURI(player.getAvatarfull());
                    int t=(int)Math.floor((double)player.getRank_tier()/10);
                    if (player.getRank_tier()!=0){
                        int star=player.getRank_tier()%10;
                        int rank=player.getLeaderboard_rank();
                        Log.d("rank", "handleMessage: "+rank);
                        if (rank>0){
                            if (rank<=1000){
                                t=8;star=0;
                            }
                            if (rank<=100){
                                t=9;star=0;
                            }
                            if (rank<=10){
                                t=10;star=0;
                            }
                            ranks.setText(rank+"");
                        }
                        if (star>0){
                            TypedArray typedArray=getContext().getResources().obtainTypedArray(R.array.stars);
                            stars.setImageResource(typedArray.getResourceId(star-1,0));
                        }
                    }
                    Log.d("rank", "handleMessage: "+t);
                    TypedArray typedArray=getContext().getResources().obtainTypedArray(R.array.tiers);
                    tier.setImageResource(typedArray.getResourceId(t,0));
                    Okhttp.getFromService(getString(R.string.api)+getString(R.string.players)+id+"/wl",handler,WL);
                    break;
                }
                case WL:{
                    wl=new Gson().fromJson(message.obj.toString(),WL.class);
                    wl.setWinrate();
                    java.text.DecimalFormat df =new java.text.DecimalFormat("#.00%");
                    String t=df.format(wl.getWinrate());
                    win.setText(getString(R.string.win)+":"+wl.getWin());
                    lose.setText(getString(R.string.lose)+":"+wl.getLose());
                    winrate.setText(getString(R.string.winrate)+":"+t);
                    Okhttp.getFromService(getString(R.string.api)+getString(R.string.players)+id+"/recentMatches",handler,RECENT_MATCHES);
                    my.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    break;
                }
                case RECENT_MATCHES:{
                    recentMatches.clear();
                    JsonParser parser=new JsonParser();
                    JsonArray jsonArray=parser.parse(message.obj.toString()).getAsJsonArray();
                    for (JsonElement e:jsonArray){
                        RecentMatch recentMatch=new Gson().fromJson(e,RecentMatch.class);
                        recentMatches.add(recentMatch);
                    }
                    for (RecentMatch i:recentMatches){
                        Log.d("id", "handleMessage: "+i.getMatch_id());
                    }
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    matchesAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                }
            }

        }
    };
    public MyFragment() {
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_my, container, false);
        linearLayout=view.findViewById(R.id.no_binding);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        my=view.findViewById(R.id.my);
        button1=view.findViewById(R.id.verify);
        progressBar=view.findViewById(R.id.progressBar);
        header=view.findViewById(R.id.header);
        personaname=view.findViewById(R.id.personaname);
        loccountrycode=view.findViewById(R.id.loccountrycode);
        account_id=view.findViewById(R.id.account_id);
        win=view.findViewById(R.id.win);
        lose=view.findViewById(R.id.lose);
        winrate=view.findViewById(R.id.win_rate);
        tier=view.findViewById(R.id.tier);
        ranks=view.findViewById(R.id.rank);
        stars=view.findViewById(R.id.star);
        recyclerView=view.findViewById(R.id.recent_matches);
        button = view.findViewById(R.id.connect);
        textInputLayout = view.findViewById(R.id.text_input);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapter=new MatchesAdapter(getContext(),recentMatches);
        recyclerView.setAdapter(matchesAdapter);
        swipeRefreshLayout=view.findViewById(R.id.swipe);
        textView = view.findViewById(R.id.text_notice);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Okhttp.getFromService(getString(R.string.api)+getString(R.string.players)+id,handler,PLAYER_INFO);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText textInputEditText = view.findViewById(R.id.steam_id);
                if (textInputEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please input your Steam 32 ID!", Toast.LENGTH_LONG).show();
                } else {
                    tmp = textInputEditText.getText().toString();
                    Okhttp.getFromService(getString(R.string.api) + getString(R.string.players) + tmp, handler, VERIFY);
                    button1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        if (sharedPreferences.getString("id","").equals("")){
            linearLayout.setVisibility(View.VISIBLE);
            my.setVisibility(View.GONE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setVisibility(View.INVISIBLE);
                    textInputLayout.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                    button1.setVisibility(View.VISIBLE);
                }
            });
        }else{
            id=sharedPreferences.getString("id","");
        }
        if (!id.equals("")){
            progressBar.setVisibility(View.VISIBLE);
            Okhttp.getFromService(getString(R.string.api)+getString(R.string.players)+id,handler,PLAYER_INFO);
        }
        IntentFilter intentFilter = new IntentFilter(DISCONNECT);
        receiver = new Receiver();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, intentFilter);
        return view;
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            my.setVisibility(View.GONE);
            id = "";
            linearLayout.setVisibility(View.VISIBLE);
            textInputLayout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
            button.setVisibility(View.GONE);
            button1.setVisibility(View.VISIBLE);
            recentMatches.clear();
        }
    }
}
