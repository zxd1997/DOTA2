package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.Beans.CastHeader;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.GridItemDecoration;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchaseAndCastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseAndCastFragment extends Fragment {
    private final int ABILITY = 0;
    private final int ITEM = 1;
    private final int PURCHASE = 2;
    private final int HEADER = -1;
    private final int PLAYER_HEADER = 9;
    private final List<Cast> casts = new ArrayList<>();
    private Match match;
    private RecyclerView recyclerView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    public PurchaseAndCastFragment() {
        // Required empty public constructor
    }

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            final CastAdapter castAdapter = new CastAdapter(getContext(), casts);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 36);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (castAdapter.getItemViewType(position)) {
                        case -1:
                        case 6:
                        case 9:
                        case 10:
                            return 36;
                        case 4:
                            return 2;
                        case 5:
                            return 12;
                        case 13:
                        case 14:
                            return 6;
                        default:
                            return 4;
                    }
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new GridItemDecoration());
            recyclerView.setAdapter(castAdapter);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()));
            Intent intent = new Intent("loaded");
            localBroadcastManager.sendBroadcast(intent);
            return true;
        }
    });

    public static PurchaseAndCastFragment newInstance() {
        return new PurchaseAndCastFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_and_cast, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
//            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            recyclerView = view.findViewById(R.id.p_c);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        Fresco.getImagePipeline().resume();
                    else Fresco.getImagePipeline().pause();
                }
            });
            new Thread(() -> {
                casts.add(new Cast(Color.BLACK, Objects.requireNonNull(getContext()).getResources().getString(R.string.item_cast), HEADER));
                for (Match.PPlayer p : match.getPlayers()) {
                    casts.add(new CastHeader(Objects.requireNonNull(getContext()).getResources().getColor(
                            Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class)),
                            p.getPersonaname(), PLAYER_HEADER, p.getHero_id(), p.getTotal_gold(), p.getGold_spent()));
                    casts.add(new Cast(getContext().getResources().getColor(R.color.win), getContext().getResources().getString(R.string.item_purchase), HEADER));
                    for (Match.Objective purchase : p.getPurchase_log()) {
                        if (purchase.getKey().equals("tpscroll") || purchase.getKey().equals("ward_observer") || purchase.getKey().equals("ward_sentry"))
                            continue;
                        Item item = MainActivity.items.get(purchase.getKey());
                        casts.add(new Cast(purchase.getTime(), item.getId() + "", PURCHASE));
                    }
                    casts.add(new Cast(getContext().getResources().getColor(R.color.lose), getContext().getResources().getString(R.string.cast), HEADER));
                    List<Cast> tmp = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : p.getAbility_uses().entrySet()) {
                        for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                            if (entry1.getValue().equals(entry.getKey())) {
                                if (p.getAbility_targets().get(entry.getKey()) != null) {
                                    casts.add(new Cast(0, "", 6));
                                    casts.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                                    casts.add(new Cast(0, "", 4));
                                    for (Map.Entry<String, Integer> entry2 : p.getAbility_targets().get(entry.getKey()).entrySet()) {
                                        casts.add(new Cast(entry2.getValue(), "hero_" + MainActivity.heroes.get(entry2.getKey()).getId() + "_icon", 7));
                                    }
                                    casts.add(new Cast(0, "", 6));
                                } else
                                    tmp.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                                break;
                            }
                        }
                    }
                    casts.addAll(tmp);
                    tmp.clear();
                    casts.add(new Cast(0, "", 6));
                    casts.add(new Cast(getContext().getResources().getColor(R.color.lose), getContext().getResources().getString(R.string.item), HEADER));
                    for (Map.Entry<String, Integer> entry : p.getItem_uses().entrySet()) {
                        Item item = MainActivity.items.get(entry.getKey());
                        casts.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                    }
                    casts.add(new Cast(getContext().getResources().getColor(R.color.lose), getContext().getResources().getString(R.string.hero_hits), HEADER));
                    for (Map.Entry<String, Integer> entry : p.getHero_hits().entrySet()) {
                        if (entry.getKey().equals("null")) {
                            casts.add(new Cast(entry.getValue(), "default_attack", ABILITY));
                        } else {
                            boolean f = false;
                            for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                                if (entry1.getValue().equals(entry.getKey())) {
                                    casts.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                Item item = MainActivity.items.get(entry.getKey());
                                casts.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                            }
                        }
                    }
                }
                handler.sendMessage(new Message());
            }).start();
        }
        return view;
    }
}
