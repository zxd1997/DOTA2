package com.example.zxd1997.dota2.Utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.example.zxd1997.dota2.R;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

public class Tools {
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static StringBuilder getTime(int time) {
        StringBuilder t = new StringBuilder();
        if (time < 0) t.append("-");
        time = Math.abs(time);
        int h = time / 3600;
        int m = time % 3600 / 60;
        int s = time % 3600 % 60;
        if (h > 0) {
            t.append((h < 10) ? "0" + h + ":" : h + ":");
        }
        t.append((m < 10) ? "0" + m + ":" : m + ":");
        t.append((s < 10) ? "0" + s : s);
        return t;
    }

    public static SpannableString getS(double pct) {
        pct *= 100;
        DecimalFormat df = new DecimalFormat("0.00");
        SpannableString t = new SpannableString(df.format(pct) + "%");
        if (pct >= 75) {
            t.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.win)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (pct >= 50) {
            t.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.blue_light)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (pct >= 25) {
            t.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.very_high)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            t.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.lose)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return t;
    }
    public static StringBuilder getBefore(long time) {
        long now = System.currentTimeMillis() / 1000;
        long year = (now - time) / (3600 * 24 * 30 * 12);
        long month = (now - time) / (3600 * 24 * 30);
        long day = (now - time) / (3600 * 24);
        long hour = (now - time) / 3600;
        long minute = (now - time) / 60;
        StringBuilder tmp = new StringBuilder();
        if (year > 0) {
            tmp.append(year).append(MyApplication.getContext().getString(R.string.year_ago));
        } else if (month > 0) {
            tmp.append(month).append(MyApplication.getContext().getString(R.string.month_ago));
        } else if (day > 0) {
            tmp.append(day).append(MyApplication.getContext().getString(R.string.day_ago));
        } else if (hour > 0) {
            tmp.append(hour).append(MyApplication.getContext().getString(R.string.hour_ago));
        } else if (minute > 3) {
            tmp.append(minute).append(MyApplication.getContext().getString(R.string.minute_ago));
        } else tmp.append(MyApplication.getContext().getString(R.string.just_now));
        return tmp;
    }
}
