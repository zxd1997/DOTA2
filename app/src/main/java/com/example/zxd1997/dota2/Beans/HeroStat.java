package com.example.zxd1997.dota2.Beans;

public class HeroStat {
    private int id;
    private int pick;
    private double winrate;
    private int rank;
    private String text;
    private int ban;

    public HeroStat(int id, int pick, double winrate, int rank) {
        this.id = id;
        this.pick = pick;
        this.winrate = winrate;
        this.rank = rank;
    }

    public HeroStat(int id, int pick, int ban, double winrate, int rank) {
        this.id = id;
        this.pick = pick;
        this.ban = ban;
        this.winrate = winrate;
        this.rank = rank;
    }

    public HeroStat(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getBan() {
        return ban;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }

    public double getWinrate() {
        return winrate;
    }

    public void setWinrate(double winrate) {
        this.winrate = winrate;
    }
}
