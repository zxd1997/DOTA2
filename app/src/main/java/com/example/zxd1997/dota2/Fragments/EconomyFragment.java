package com.example.zxd1997.dota2.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Chart.MyColumnChartRenderer;
import com.example.zxd1997.dota2.Chart.MyLineChartRenderer;
import com.example.zxd1997.dota2.Chart.MyLineChartRenderer1;
import com.example.zxd1997.dota2.Chart.MyLineChartView;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EconomyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EconomyFragment extends Fragment {
    Match match;
    ColumnChartView subview;

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
                    .setPointRadius(1);
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
                    .setPointRadius(1)
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
            chart.setLineChartData(data);
            subview = view.findViewById(R.id.subchart);
            subview.setChartRenderer(new MyColumnChartRenderer(getContext(), subview, subview));
            subview.setZoomEnabled(false);
            subview.setInteractive(false);
            ColumnChartView mianview = view.findViewById(R.id.mainchart);
            mianview.setChartRenderer(new MyColumnChartRenderer(getContext(), mianview, mianview));
            List<AxisValue> axisValues = new ArrayList<>();
            List<Column> columns = new ArrayList<>();
            final List<List<Column>> tmpColumns = new ArrayList<>();
            final List<List<AxisValue>> tmpAxises = new ArrayList<>();
            MyLineChartView chart1 = view.findViewById(R.id.chart1);
            chart1.setChartRenderer(new MyLineChartRenderer1(getContext(), chart1, chart1));
            lines = new ArrayList<>();
            int l = 0;
            for (Match.PPlayer p : match.getPlayers()) {
                int i = 0;
                String hname = "";
                TextView textView = view.findViewById(getContext().getResources().getIdentifier("name" + l, "id", getContext().getPackageName()));
                View view1 = view.findViewById(getContext().getResources().getIdentifier("color" + l, "id", getContext().getPackageName()));
                for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
                    if (entry.getValue().getId() == p.getHero_id()) {
                        hname = entry.getValue().getLocalized_name();
                        break;
                    }
                }
                pointValues = new ArrayList<>();
                for (int k : p.getGold_t()) {
                    pointValues.add(new PointValue(i++, k).setLabel(hname + ":" + k));
                }
                int color = getContext().getResources().getColor(getContext().getResources().getIdentifier("slot_" + p.getPlayer_slot(), "color", getContext().getPackageName()));
                textView.setText(hname);
                view1.setBackgroundColor(color);
                Line line1 = new Line(pointValues)
                        .setColor(color)
                        .setCubic(true)
                        .setFilled(false)
                        .setHasPoints(true)
                        .setPointRadius(1)
                        .setHasLabelsOnlyForSelected(true)
                        .setStrokeWidth(1);
                lines.add(line1);
                axisValues.add(new AxisValue(l).setLabel(hname.length() > 10 ? hname.substring(0, 8) + ".." : hname));
                List<SubcolumnValue> subcolumnValues = new ArrayList<>();
                subcolumnValues.add(new SubcolumnValue(p.getTotal_gold(), color).setLabel(hname));
                columns.add(new Column(subcolumnValues).setHasLabelsOnlyForSelected(true));
                List<AxisValue> tmpAxis = new ArrayList<>();
                int o = 0;
                List<Column> tmpColumn = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : p.getGold_reasons().entrySet()) {
                    subcolumnValues = new ArrayList<>();
                    Log.d("key", "onCreateView: " + entry.getKey() + " " + entry.getValue());
                    int id = getContext().getResources()
                            .getIdentifier("reason_" + entry.getKey(), "string", getContext().getPackageName());
                    if (id != 0) {
                        String t = getContext().getString(id);
                        int c = getContext().getColor(getContext().getResources()
                                .getIdentifier("reason_" + entry.getKey(), "color", getContext().getPackageName()));
                        tmpAxis.add(new AxisValue(o++).setLabel(t));
                        subcolumnValues.add(new SubcolumnValue(entry.getValue()).setColor(c));
                        tmpColumn.add(new Column(subcolumnValues).setHasLabels(true));
                    }
                }
                tmpColumns.add(tmpColumn);
                tmpAxises.add(tmpAxis);
                l++;
            }
            ColumnChartData columnChartData = new ColumnChartData(columns);
            columnChartData.setAxisXBottom(new Axis(axisValues).setTextSize(10).setTextColor(Color.BLACK));
            columnChartData.setAxisYLeft(new Axis().setHasLines(true).setName("Total Gold").setTextSize(8));
            mianview.setColumnChartData(columnChartData);
            mianview.setValueSelectionEnabled(true);
            mianview.setZoomEnabled(false);
            mianview.setInteractive(true);
            showSub(tmpAxises.get(0), tmpColumns.get(0));
            mianview.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
                @Override
                public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                    Log.d("index", "onValueSelected: " + subcolumnIndex + columnIndex);
                    showSub(tmpAxises.get(columnIndex), tmpColumns.get(columnIndex));
                }

                @Override
                public void onValueDeselected() {

                }
            });
            data = new LineChartData();
            data.setLines(lines);
            axisY = new Axis().setName("Gold").setHasLines(true).setTextSize(9).setFormatter(new SimpleAxisValueFormatter());
            data.setAxisYLeft(axisY);
            axisX = new Axis().setName("Time/min");
            data.setAxisXBottom(axisX);
            chart1.setZoomEnabled(false);
            chart1.setInteractive(true);
            chart1.setLineChartData(data);
        }
        return view;
    }

    private void showSub(List<AxisValue> tmp, List<Column> column) {
        List<Column> columns = new ArrayList<>();
        Log.d("size", "showSub: " + tmp.size() + " " + column.size());
        ColumnChartData columnChartData = new ColumnChartData(column);
        columnChartData.setAxisXBottom(new Axis(tmp).setTextColor(Color.BLACK));
        columnChartData.setAxisYLeft(new Axis().setHasLines(true).setName("Gold"));
        subview.setColumnChartData(columnChartData);
        subview.startDataAnimation(300);
    }
}
