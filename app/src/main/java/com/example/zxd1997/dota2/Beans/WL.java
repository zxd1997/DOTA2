package com.example.zxd1997.dota2.Beans;

import java.io.Serializable;

public class WL implements Serializable {
    private int win;
    private int lose;
    private double winrate;

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public double getWinrate() {
        return winrate;
    }

    public void setWinrate() {
        this.winrate = (double) win / (win + lose);
    }
}
