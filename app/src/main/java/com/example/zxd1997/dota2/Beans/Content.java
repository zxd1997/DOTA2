package com.example.zxd1997.dota2.Beans;

import android.text.SpannableStringBuilder;

public class Content {
    private boolean isHeader;
    private int hero_id;
    private SpannableStringBuilder kd;
    private int color;

    public Content(boolean isHeader, int hero_id, int color) {
        this.isHeader = isHeader;
        this.hero_id = hero_id;
        this.color = color;
    }

    public Content(boolean isHeader, SpannableStringBuilder kd) {
        this.isHeader = isHeader;
        this.kd = kd;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public int getHero_id() {
        return hero_id;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public SpannableStringBuilder getKd() {
        return kd;
    }

    public void setKd(SpannableStringBuilder kd) {
        this.kd = kd;
    }
}