package com.example.zxd1997.dota2.Utils;

import java.lang.reflect.Field;

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

    public static StringBuilder getBefore(long time) {
        long now = System.currentTimeMillis() / 1000;
        long year = (now - time) / (3600 * 24 * 30 * 12);
        long month = (now - time) / (3600 * 24 * 30);
        long day = (now - time) / (3600 * 24);
        long hour = (now - time) / 3600;
        long minute = (now - time) / 60;
        StringBuilder tmp = new StringBuilder();
        if (year > 0) {
            tmp.append(year).append(" Years ago");
        } else if (month > 0) {
            tmp.append(month).append(" Months ago");
        } else if (day > 0) {
            tmp.append(day).append(" Days ago");
        } else if (hour > 0) {
            tmp.append(hour).append(" Hours ago");
        } else if (minute > 3) {
            tmp.append(minute).append("Minutes ago");
        } else tmp.append("Just Now");
        return tmp;
    }
}
