package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.example.zxd1997.dota2.Utils.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private ColumnChartView subview;

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
    public EconomyFragment() {
        // Required empty public constructor
    }

    public static EconomyFragment newInstance() {
        return new EconomyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MatchActivity activity = (MatchActivity) getActivity();
        final View view = inflater.inflate(R.layout.fragment_economy, container, false);
        final Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
//            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            final MyLineChartView chart = view.findViewById(R.id.chart);
            chart.setChartRenderer(new MyLineChartRenderer(getContext(), chart, chart));
            subview = view.findViewById(R.id.subchart);
            subview.setChartRenderer(new MyColumnChartRenderer(getContext(), subview, subview));
            subview.setZoomEnabled(false);
            subview.setInteractive(false);
            final ColumnChartView main_view = view.findViewById(R.id.mainchart);
            main_view.setChartRenderer(new MyColumnChartRenderer(getContext(), main_view, main_view));
            final MyLineChartView chart1 = view.findViewById(R.id.chart1);
            chart1.setChartRenderer(new MyLineChartRenderer1(getContext(), chart1, chart1));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int j = 0;
                    List<Line> lines = new ArrayList<>();
                    List<PointValue> pointValues = new ArrayList<>();
                    for (int i : match.getRadiant_gold_adv()) {
                        pointValues.add(new PointValue(j++, i).setLabel(i >= 0 ? "Radiant Gold Advantage:" + i : "Dire Gold Advantage:" + Math.abs(i)));
                    }
                    Line line = new Line(pointValues)
                            .setColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.very_high))
                            .setCubic(true)
                            .setFilled(false)
                            .setStrokeWidth(1)
                            .setHasPoints(true)
                            .setHasLabelsOnlyForSelected(true)
                            .setPointRadius(1);
                    lines.add(line);
                    List<PointValue> pointValues1 = new ArrayList<>();
                    j = 0;
                    for (int i : match.getRadiant_xp_adv()) {
                        pointValues1.add(new PointValue(j++, i).setLabel(i >= 0 ? "Radiant XP Advantage:" + i : "Dire XP Advantage:" + Math.abs(i)));
                    }
                    line = new Line(pointValues1)
                            .setColor(getContext().getResources().getColor(R.color.win))
                            .setCubic(true)
                            .setFilled(false)
                            .setHasPoints(true)
                            .setHasLabelsOnlyForSelected(true)
                            .setPointRadius(1)
                            .setStrokeWidth(1);
                    lines.add(line);
                    final LineChartData data = new LineChartData();
                    data.setLines(lines);
                    Axis axisY = new Axis().setName("Advantage").setHasLines(true);
                    data.setAxisYLeft(axisY);
                    Axis axisX = new Axis().setName("Time/min");
                    data.setAxisXBottom(axisX);
                    List<AxisValue> axisValues = new ArrayList<>();
                    List<Column> columns = new ArrayList<>();
                    final List<List<Column>> tmpColumns = new ArrayList<>();
                    final List<List<AxisValue>> tmpAxises = new ArrayList<>();
                    final List<Line> lines1 = new ArrayList<>();
                    int l = 0;
                    for (final Match.PPlayer p : match.getPlayers()) {
                        int i = 0;
                        String h_name = "";
                        final TextView textView = view.findViewById(Tools.getResId("name" + l, R.id.class));
                        final View view1 = view.findViewById(Tools.getResId("color" + l, R.id.class));
                        for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
                            if (entry.getValue().getId() == p.getHero_id()) {
                                h_name = entry.getValue().getLocalized_name();
                                break;
                            }
                        }
                        List<PointValue> pointValues2 = new ArrayList<>();
                        for (int k : p.getGold_t()) {
                            pointValues2.add(new PointValue(i++, k).setLabel(h_name + ":" + k));
                        }
                        final String finalH_name = h_name;
                        final int color = getContext().getResources().getColor(Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class));
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(finalH_name.length() > 12 ? finalH_name.substring(0, 10) + ".." : finalH_name);
                                view1.setBackgroundColor(color);
                            }
                        });
                        Line line1 = new Line(pointValues2)
                                .setColor(color)
                                .setCubic(true)
                                .setFilled(false)
                                .setHasPoints(true)
                                .setPointRadius(1)
                                .setHasLabelsOnlyForSelected(true)
                                .setStrokeWidth(1);
                        lines1.add(line1);
                        axisValues.add(new AxisValue(l).setLabel(h_name.length() > 9 ? h_name.substring(0, 7) + ".." : h_name));
                        List<SubcolumnValue> sub_columnValues = new ArrayList<>();
                        sub_columnValues.add(new SubcolumnValue(p.getTotal_gold(), color).setLabel(h_name));
                        columns.add(new Column(sub_columnValues).setHasLabelsOnlyForSelected(true));
                        List<AxisValue> tmpAxis = new ArrayList<>();
                        int o = 0;
                        List<Column> tmpColumn = new ArrayList<>();
                        for (Map.Entry<String, Integer> entry : p.getGold_reasons().entrySet()) {
                            sub_columnValues = new ArrayList<>();
                            int id = Tools.getResId("reason_" + entry.getKey(), R.string.class);
                            if (id != 0) {
                                String t = getContext().getString(id);
                                int c = getContext().getResources().getColor(Tools.getResId("reason_" + entry.getKey(), R.color.class));
                                tmpAxis.add(new AxisValue(o++).setLabel(t));
                                sub_columnValues.add(new SubcolumnValue(entry.getValue()).setColor(c));
                                tmpColumn.add(new Column(sub_columnValues).setHasLabels(true));
                            }
                        }
                        tmpColumns.add(tmpColumn);
                        tmpAxises.add(tmpAxis);
                        l++;
                    }
                    final ColumnChartData columnChartData = new ColumnChartData(columns);
                    columnChartData.setAxisXBottom(new Axis(axisValues).setTextSize(10).setTextColor(Color.BLACK));
                    columnChartData.setAxisYLeft(new Axis().setHasLines(true).setName("Total Gold").setTextSize(8));
                    final LineChartData data1 = new LineChartData();
                    data1.setLines(lines1);
                    Axis axisY1 = new Axis().setName("Gold").setHasLines(true).setTextSize(9).setFormatter(new SimpleAxisValueFormatter());
                    data1.setAxisYLeft(axisY1);
                    Axis axisX1 = new Axis().setName("Time/min");
                    data1.setAxisXBottom(axisX1);
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            chart.setZoomEnabled(false);
                            chart.setInteractive(true);
                            chart.setLineChartData(data);
                            main_view.setColumnChartData(columnChartData);
                            main_view.setValueSelectionEnabled(true);
                            main_view.setZoomEnabled(false);
                            main_view.setInteractive(true);
                            showSub(tmpAxises.get(0), tmpColumns.get(0));
                            main_view.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
                                @Override
                                public void onValueSelected(final int columnIndex, int sub_columnIndex, SubcolumnValue value) {
                                    showSub(tmpAxises.get(columnIndex), tmpColumns.get(columnIndex));
                                }

                                @Override
                                public void onValueDeselected() {

                                }
                            });
                            chart1.setZoomEnabled(false);
                            chart1.setInteractive(true);
                            chart1.setLineChartData(data1);
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
                            Intent intent = new Intent("loaded");
                            localBroadcastManager.sendBroadcast(intent);
                        }
                    });
                }
            }).start();
        }
        return view;
    }

    private void showSub(List<AxisValue> tmp, List<Column> column) {
        ColumnChartData columnChartData = new ColumnChartData(column);
        columnChartData.setAxisXBottom(new Axis(tmp).setTextColor(Color.BLACK));
        columnChartData.setAxisYLeft(new Axis().setHasLines(true).setName("Gold"));
        subview.setColumnChartData(columnChartData);
        subview.startDataAnimation(300);
    }
}
