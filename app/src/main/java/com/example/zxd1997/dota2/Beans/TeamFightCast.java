package com.example.zxd1997.dota2.Beans;

public class TeamFightCast extends Cast {
    Match.TeamFight.TeamFightPlayer p;

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
}