package com.example.zxd1997.dota2.Beans;

public class Cast {
    private final int time;
    private final String id;
    private final int type;

    public Cast(int time, String id, int type) {
        this.type = type;
        this.id = id;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }
}