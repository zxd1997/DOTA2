package com.example.zxd1997.dota2.Beans;

public class Ability {
    private String dname;
    //    private String behavior;
    private String dmg_type;
    //    private String bkbpierce;
    private String desc;
    private int ability_id;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDmg_type() {
        return dmg_type;
    }

    public void setDmg_type(String dmg_type) {
        this.dmg_type = dmg_type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAbility_id() {
        return ability_id;
    }

    public void setAbility_id(int ability_id) {
        this.ability_id = ability_id;
    }
}
