package com.example.zxd1997.dota2.Beans;

import android.text.SpannableStringBuilder;

public class Cast {
    private final int time;
    private String id;
    private final int type;
    private SpannableStringBuilder t;
    public Cast(int time, String id, int type) {
        this.type = type;
        this.id = id;
        this.time = time;
    }

    public Cast(int time, SpannableStringBuilder t, int type) {
        this.type = type;
        this.t = t;
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SpannableStringBuilder getT() {
        return t;
    }

    public void setT(SpannableStringBuilder t) {
        this.t = t;
    }

    public int getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }
}
