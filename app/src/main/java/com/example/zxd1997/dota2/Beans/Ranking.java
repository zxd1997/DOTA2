package com.example.zxd1997.dota2.Beans;

public class Ranking extends MatchHero {
    private float score;
    private float percent_rank;
    private int card;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getPercent_rank() {
        return percent_rank;
    }

    public void setPercent_rank(float percent_rank) {
        this.percent_rank = percent_rank;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }
}
