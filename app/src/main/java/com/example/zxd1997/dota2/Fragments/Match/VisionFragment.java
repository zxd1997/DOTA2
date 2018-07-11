package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.WardsAdapter;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Beans.Wards;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.Tools;
import com.example.zxd1997.dota2.Views.WardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisionFragment extends Fragment {

    private final int OBSERVER = 0;

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy: ");
        System.gc();
        System.runFinalization();
    }
    public VisionFragment() {
        // Required empty public constructor
    }

    public static VisionFragment newInstance() {
        return new VisionFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_vision, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        final Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
//            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            final List<Wards> wards = new ArrayList<>();
            final List<Wards> current_wards = new ArrayList<>();
            new Thread(() -> {
                for (Match.PPlayer p : match.getPlayers()) {
                    for (Match.PPlayer.Ward ward : p.getObs_log()) {
                        boolean f = false;
                        for (Match.PPlayer.Ward ward_left : p.getObs_left_log()) {
                            if (ward.getKey().equals(ward_left.getKey()) && ward.getEhandle() == ward_left.getEhandle()) {
                                wards.add(new Wards(OBSERVER, ward, ward_left, p.getPersonaname(), p.getHero_id()));
                                f = true;
                                break;
                            }
                        }
                        if (!f) {
                            wards.add(new Wards(OBSERVER, ward, null, p.getPersonaname(), p.getHero_id()));
                        }
                    }
                }
                for (Match.PPlayer p : match.getPlayers()) {
                    for (Match.PPlayer.Ward ward : p.getSen_log()) {
                        boolean f = false;
                        int SENTRY = 1;
                        for (Match.PPlayer.Ward ward_left : p.getSen_left_log()) {
                            if (ward.getKey().equals(ward_left.getKey()) && ward.getEhandle() == ward_left.getEhandle()) {
                                wards.add(new Wards(SENTRY, ward, ward_left, p.getPersonaname(), p.getHero_id()));
                                f = true;
                                break;
                            }
                        }
                        if (!f) {
                            wards.add(new Wards(SENTRY, ward, null, p.getPersonaname(), p.getHero_id()));
                        }
                    }
                }
                Collections.sort(wards, (o1, o2) -> o1.getWard().getTime() - o2.getWard().getTime());
                current_wards.addAll(wards);
                view.post(() -> {
                    final WardsAdapter wardsAdapter = new WardsAdapter(getContext(), current_wards);
                    SeekBar seekBar = view.findViewById(R.id.seekBar);
                    final WardView map = view.findViewById(R.id.map);
                    final TextView time = view.findViewById(R.id.wards_time);
                    final RecyclerView recyclerView = view.findViewById(R.id.wards);
                    seekBar.setMax(match.getDuration());
                    time.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.time, getContext().getResources().getString(R.string.zero)));
                    map.setWards(current_wards);
                    map.invalidate();
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                                    Log.d(TAG, "onProgressChanged: ");
                            if (progress != 0) {
                                time.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.time, Tools.getTime(progress)));
                                current_wards.clear();
                                for (Wards ward : wards) {
                                    if (ward.getWard().getTime() <= progress) {
                                        if (ward.getWard_left() == null) {
                                            current_wards.add(ward);
                                        } else if (ward.getWard_left().getTime() >= progress)
                                            current_wards.add(ward);
                                    }
                                }
                            } else {
                                current_wards.clear();
                                current_wards.addAll(wards);
                                time.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.time, getContext().getResources().getString(R.string.zero)));
                            }
                            recyclerView.smoothScrollToPosition(0);
                            wardsAdapter.notifyDataSetChanged();
                            map.setWards(current_wards);
                            map.invalidate();
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(wardsAdapter);
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()));
                    Intent intent = new Intent("loaded");
                    localBroadcastManager.sendBroadcast(intent);
//                    Log.d("send", "run: send");

                });
            }).start();
        }
        return view;
    }

}
