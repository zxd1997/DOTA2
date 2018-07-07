package com.example.zxd1997.dota2.Beans;

import java.util.List;

public class MyHero extends MatchHero {
    private WL wl;
    private List<Total> total;

    public WL getWl() {
        return wl;
    }

    public void setWl(WL wl) {
        this.wl = wl;
    }

    public List<Total> getTotal() {
        return total;
    }

    public void setTotal(List<Total> total) {
        this.total = total;
    }
}
