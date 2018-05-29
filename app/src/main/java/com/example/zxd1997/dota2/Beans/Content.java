package com.example.zxd1997.dota2.Beans;

import android.text.SpannableStringBuilder;

public class Content {
    private boolean isHeader;
    private int hero_id;
    private SpannableStringBuilder kd;

    public Content(boolean isHeader, int hero_id) {
        this.isHeader = isHeader;
        this.hero_id = hero_id;
    }

    public Content(boolean isHeader, SpannableStringBuilder kd) {
        this.isHeader = isHeader;
        this.kd = kd;
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