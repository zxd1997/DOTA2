package com.example.zxd1997.dota2.Beans;

import com.example.zxd1997.dota2.Adapters.LogAdapter;

import java.util.List;

public class TeamFightCast extends Cast {
    private Match.TeamFight.TeamFightPlayer p;
    private CharSequence win;
    private CharSequence radiant_death;
    private CharSequence radiant_gold;
    private CharSequence dire_death;
    private CharSequence dire_gold;
    private CharSequence time1;
    private List<LogAdapter.Point> points;

    public TeamFightCast(int time, String id, int type) {
        super(time, id, type);
    }

    public TeamFightCast(int time, String id, int type, List<LogAdapter.Point> points) {
        super(time, id, type);
        this.points = points;
    }

    public TeamFightCast(int time, String id, int type, Match.TeamFight.TeamFightPlayer p) {
        super(time, id, type);
        this.p = p;
    }

    public Match.TeamFight.TeamFightPlayer getP() {
        return p;
    }

    public void setP(Match.TeamFight.TeamFightPlayer p) {
        this.p = p;
    }

    public CharSequence getWin() {
        return win;
    }

    public void setWin(CharSequence win) {
        this.win = win;
    }

    public CharSequence getRadiant_death() {
        return radiant_death;
    }

    public void setRadiant_death(CharSequence radiant_death) {
        this.radiant_death = radiant_death;
    }

    public CharSequence getRadiant_gold() {
        return radiant_gold;
    }

    public void setRadiant_gold(CharSequence radiant_gold) {
        this.radiant_gold = radiant_gold;
    }

    public CharSequence getDire_death() {
        return dire_death;
    }

    public void setDire_death(CharSequence dire_death) {
        this.dire_death = dire_death;
    }

    public CharSequence getDire_gold() {
        return dire_gold;
    }

    public void setDire_gold(CharSequence dire_gold) {
        this.dire_gold = dire_gold;
    }

    public CharSequence getTime1() {
        return time1;
    }

    public void setTime1(CharSequence time1) {
        this.time1 = time1;
    }

    public List<LogAdapter.Point> getPoints() {
        return points;
    }

    public void setPoints(List<LogAdapter.Point> points) {
        this.points = points;
    }
}