package com.example.zxd1997.dota2.Beans;

public class Abilities {
    private int id;
    private String what;
    private int color;
    private int type;

    public Abilities(int id, String what, int color, int type) {
        this.id = id;
        this.what = what;
        this.color = color;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
