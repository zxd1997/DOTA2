package com.example.zxd1997.dota2.Beans;

import java.util.Map;

public class Counts {
    private Map<String, WL> lobby_type;
    private Map<String, WL> is_radiant;
    private Map<String, WL> patch;

    public Map<String, WL> getLobby_type() {
        return lobby_type;
    }

    public void setLobby_type(Map<String, WL> lobby_type) {
        this.lobby_type = lobby_type;
    }

    public Map<String, WL> getIs_radiant() {
        return is_radiant;
    }

    public void setIs_radiant(Map<String, WL> is_radiant) {
        this.is_radiant = is_radiant;
    }

    public Map<String, WL> getPatch() {
        return patch;
    }

    public void setPatch(Map<String, WL> patch) {
        this.patch = patch;
    }
}
