package com.example.zxd1997.dota2.Beans;

public class CastHeader extends Cast {
    private final int damage_out;
    private final int hero_id;
    private int damage_in;

    public CastHeader(int time, String id, int type, int hero_id, int damage_out, int damage_in) {
        super(time, id, type);
        this.hero_id = hero_id;
        this.damage_out = damage_out;
        this.damage_in = damage_in;
    }

    public int getDamage_out() {
        return damage_out;
    }

    public int getHero_id() {
        return hero_id;
    }

    public int getDamage_in() {
        return damage_in;
    }

    public void setDamage_in(int damage_in) {
        this.damage_in = damage_in;
    }
}
