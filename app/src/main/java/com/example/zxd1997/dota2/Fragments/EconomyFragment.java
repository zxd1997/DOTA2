package com.example.zxd1997.dota2.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Chart.MyLineChartRenderer;
import com.example.zxd1997.dota2.Chart.MyLineChartView;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EconomyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EconomyFragment extends Fragment {
    Match match;

    public EconomyFragment() {
        // Required empty public constructor
    }

    public static EconomyFragment newInstance() {
        EconomyFragment fragment = new EconomyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MatchActivity activity = (MatchActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_economy, container, false);
        match = activity.getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
            getActivity().finish(); 
        } else {
            MyLineChartView chart = view.findViewById(R.id.chart);
            chart.setChartRenderer(new MyLineChartRenderer(getContext(), chart, chart));
            List<Line> lines = new ArrayList<>();
            List<PointValue> pointValues = new ArrayList<>();
            int j = 0;
            for (int i : match.getRadiant_gold_adv()) {
                pointValues.add(new PointValue(j++, i).setLabel(i >= 0 ? "Radiant Gold Advantage:" + i : "Dire Gold Advantage:" + Math.abs(i)));
            }
            Line line = new Line(pointValues)
                    .setColor(getContext().getResources().getColor(R.color.very_high))
                    .setCubic(true)
                    .setFilled(false)
                    .setStrokeWidth(1)
                    .setHasPoints(true)
                    .setHasLabelsOnlyForSelected(true)
                    .setPointRadius(2);
            lines.add(line);
            pointValues = new ArrayList<>();
            j = 0;
            for (int i : match.getRadiant_xp_adv()) {
                pointValues.add(new PointValue(j++, i).setLabel(i >= 0 ? "Radiant XP Advantage:" + i : "Dire XP Advantage:" + Math.abs(i)));
            }
            line = new Line(pointValues)
                    .setColor(getContext().getResources().getColor(R.color.win))
                    .setCubic(true)
                    .setFilled(false)
                    .setHasPoints(true)
                    .setHasLabelsOnlyForSelected(true)
                    .setPointRadius(2)
                    .setStrokeWidth(1);
            lines.add(line);
            LineChartData data = new LineChartData();
            data.setLines(lines);
            Axis axisY = new Axis().setName("Advantage").setHasLines(true);
            data.setAxisYLeft(axisY);
            Axis axisX = new Axis().setName("Time/min");
            data.setAxisXBottom(axisX);
            chart.setZoomEnabled(false);
            chart.setInteractive(true);
            chart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
                @Override
                public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {

                }

                @Override
                public void onValueDeselected() {

                }
            });
            chart.setLineChartData(data);
        }
        return view;
    }

}
