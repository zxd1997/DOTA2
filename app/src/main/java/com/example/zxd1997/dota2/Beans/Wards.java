package com.example.zxd1997.dota2.Beans;

public class Wards {
    private int type;
    private Match.PPlayer.Ward ward;
    private Match.PPlayer.Ward ward_left;
    private String playerName;
    private int playerHero;

    public Wards(int type, Match.PPlayer.Ward ward, Match.PPlayer.Ward ward_left, String playerName, int playerHero) {
        this.type = type;
        this.ward = ward;
        this.ward_left = ward_left;
        this.playerName = playerName;
        this.playerHero = playerHero;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Match.PPlayer.Ward getWard() {
        return ward;
    }

    public void setWard(Match.PPlayer.Ward ward) {
        this.ward = ward;
    }

    public Match.PPlayer.Ward getWard_left() {
        return ward_left;
    }

    public void setWard_left(Match.PPlayer.Ward ward_left) {
        this.ward_left = ward_left;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerHero() {
        return playerHero;
    }

    public void setPlayerHero(int playerHero) {
        this.playerHero = playerHero;
    }
}
