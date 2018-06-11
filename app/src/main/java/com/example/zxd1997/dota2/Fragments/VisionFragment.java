package com.example.zxd1997.dota2.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.WardsAdapter;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Beans.Wards;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisionFragment extends Fragment {

    private final int OBSERVER = 0;

    public VisionFragment() {
        // Required empty public constructor
    }

    private Bitmap getBitmap(Bitmap map, List<Wards> wards){
        Canvas canvas=new Canvas(map);
        float h=map.getHeight();
        float w=map.getWidth();
        float ww;
        float hh;
        Bitmap tmp;
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        float radius;
        for (Wards ward:wards){
            if (ward.getType()==OBSERVER){
                if (ward.getWard().getPlayer_slot()<5){
                    tmp=BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(),R.drawable.goodguys_observer);
                    hh=tmp.getHeight();
                    ww=tmp.getWidth();
                    paint.setColor(Color.parseColor("#00ff00"));
                    radius=(float) 1600/19000*w;
                }else {
                    tmp=BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(),R.drawable.badguys_observer);
                    hh=tmp.getHeight();
                    ww=tmp.getWidth();
                    paint.setColor(Color.parseColor("#ff0000"));
                    radius=(float) 1600/19000*w;
                }
            }else {
                if (ward.getWard().getPlayer_slot()<5){
                    tmp=BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(),R.drawable.goodguys_sentry);
                    hh=tmp.getHeight();
                    ww=tmp.getWidth();
                    paint.setColor(Color.parseColor("#00ff00"));
                    radius=(float) 850/19000*w;
                }else {
                    tmp=BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(),R.drawable.badguys_sentry);
                    hh=tmp.getHeight();
                    ww=tmp.getWidth();
                    paint.setColor(Color.parseColor("#ff0000"));
                    radius=(float) 850/19000*w;
                }
            }
            paint.setAlpha(60);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((float)(ward.getWard().getX()*2-128)/2*(float) 4/502*w, h-(float) (ward.getWard().getY()*2-130)/2*(float) 4/503*h,radius,paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            paint.setAlpha(150);
            canvas.drawCircle((float)(ward.getWard().getX()*2-128)/2*(float) 4/502*w, h-(float) (ward.getWard().getY()*2-130)/2*(float) 4/503*h,radius,paint);
            canvas.drawBitmap(tmp,(float)(ward.getWard().getX()*2-128)/2*(float)4/502*w-ww/2, h-(float) (ward.getWard().getY()*2-130)/2*(float) 4/503*h-hh/2,new Paint());
            tmp.recycle();
        }
        return map;
    }

    public static VisionFragment newInstance() {
        return new VisionFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vision, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            final List<Wards> wards = new ArrayList<>();
            int tot = 0;
            for (Match.PPlayer p : match.getPlayers()) {
                tot += p.getObs_log().size() + p.getSen_log().size();
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
            Collections.sort(wards, new Comparator<Wards>() {
                @Override
                public int compare(Wards o1, Wards o2) {
                    return o1.getWard().getTime() - o2.getWard().getTime();
                }
            });
            final List<Wards> current_wards = new ArrayList<>(wards);
            Log.d(TAG, "onCreateView: " + tot + " " + current_wards.size());
            final WardsAdapter wardsAdapter = new WardsAdapter(getContext(), current_wards);
            SeekBar seekBar = view.findViewById(R.id.seekBar);
            final ImageView map = view.findViewById(R.id.map);
            final TextView time = view.findViewById(R.id.wards_time);
            final RecyclerView recyclerView = view.findViewById(R.id.wards);
            seekBar.setMax(match.getDuration());
            time.setText(R.string.zero);
            map.setImageBitmap(getBitmap(BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(),R.drawable.map).copy(Bitmap.Config.ARGB_8888, true),current_wards));
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d(TAG, "onProgressChanged: ");
                    if (progress != 0) {
                        int h = progress / 3600;
                        int m = progress % 3600 / 60;
                        int s = progress % 3600 % 60;
                        StringBuilder t = new StringBuilder();
                        if (h > 0) {
                            t.append((h < 10) ? "0" + h + ":" : h + ":");
                        }
                        t.append((m < 10) ? "0" + m + ":" : m + ":");
                        t.append((s < 10) ? "0" + s : s);
                        time.setText(t);
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
                        time.setText(R.string.zero);
                    }
                    recyclerView.smoothScrollToPosition(0);
                    wardsAdapter.notifyDataSetChanged();
                    map.setImageBitmap(getBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.map).copy(Bitmap.Config.ARGB_8888, true),current_wards));
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
            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(wardsAdapter);
        }
        return view;
    }

}
