package com.example.zxd1997.dota2.Beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private int id;
    private String name;
    private String localized_name;
    private String primary_attr;
    private String attack_type;
    private List<String> roles = new ArrayList<>();
    private int legs;
    private int base_health;
    private double base_health_regen;
    private int base_mana;
    private double base_mana_regen;
    private double base_armor;
    private int base_mr;
    private int base_attack_min;
    private int base_attack_max;
    private int base_str;
    private int base_agi;
    private int base_int;
    private double str_gain;
    private double agi_gain;
    private double int_gain;
    private int attack_range;
    private int projectile_speed;
    private double attack_rate;
    private int move_speed;
    private double turn_rate;
    private boolean cm_enabled;
    private int pro_win;
    private int pro_pick;
    private int pro_ban;
    @SerializedName("1_win")
    private int herald_wins;
    @SerializedName("1_pick")
    private int herald_picks;
    @SerializedName("2_win")
    private int guardian_wins;
    @SerializedName("2_pick")
    private int guardian_picks;
    @SerializedName("3_win")
    private int crusader_wins;
    @SerializedName("3_pick")
    private int crusader_picks;
    @SerializedName("4_win")
    private int archon_wins;
    @SerializedName("4_pick")
    private int archon_picks;
    @SerializedName("5_win")
    private int legend_wins;
    @SerializedName("5_pick")
    private int legend_picks;
    @SerializedName("6_win")
    private int ancient_wins;
    @SerializedName("6_pick")
    private int ancient_picks;
    @SerializedName("7_win")
    private int divine_wins;
    @SerializedName("7_pick")
    private int divine_picks;
    @SerializedName("8_win")
    private int immortal_wins;
    @SerializedName("8_pick")
    private int immortal_picks;

    private int total_picks;
    private int total_wins;

    public int getTotal_picks() {
        return total_picks;
    }

    public void setTotal_picks(int total_picks) {
        this.total_picks = total_picks;
    }


    public void setTotal_picks() {
        this.total_picks = herald_picks + guardian_picks + crusader_picks + archon_picks + legend_picks + divine_picks + ancient_picks + immortal_picks;
    }

    public int getTotal_wins() {
        return total_wins;
    }

    public void setTotal_wins(int total_wins) {
        this.total_wins = total_wins;
    }

    public void setTotal_wins() {
        this.total_wins = herald_wins + guardian_wins + crusader_wins + ancient_wins + legend_wins + divine_wins + archon_wins + immortal_wins;
    }

    public int getPro_win() {
        return pro_win;
    }

    public void setPro_win(int pro_win) {
        this.pro_win = pro_win;
    }

    public int getPro_pick() {
        return pro_pick;
    }

    public void setPro_pick(int pro_pick) {
        this.pro_pick = pro_pick;
    }

    public int getPro_ban() {
        return pro_ban;
    }

    public void setPro_ban(int pro_ban) {
        this.pro_ban = pro_ban;
    }

    public int getHerald_wins() {
        return herald_wins;
    }

    public void setHerald_wins(int herald_wins) {
        this.herald_wins = herald_wins;
    }

    public int getHerald_picks() {
        return herald_picks;
    }

    public void setHerald_picks(int herald_picks) {
        this.herald_picks = herald_picks;
    }

    public int getGuardian_wins() {
        return guardian_wins;
    }

    public void setGuardian_wins(int guardian_wins) {
        this.guardian_wins = guardian_wins;
    }

    public int getGuardian_picks() {
        return guardian_picks;
    }

    public void setGuardian_picks(int guardian_picks) {
        this.guardian_picks = guardian_picks;
    }

    public int getCrusader_wins() {
        return crusader_wins;
    }

    public void setCrusader_wins(int crusader_wins) {
        this.crusader_wins = crusader_wins;
    }

    public int getCrusader_picks() {
        return crusader_picks;
    }

    public void setCrusader_picks(int crusader_picks) {
        this.crusader_picks = crusader_picks;
    }

    public int getArchon_wins() {
        return archon_wins;
    }

    public void setArchon_wins(int archon_wins) {
        this.archon_wins = archon_wins;
    }

    public int getArchon_picks() {
        return archon_picks;
    }

    public void setArchon_picks(int archon_picks) {
        this.archon_picks = archon_picks;
    }

    public int getLegend_wins() {
        return legend_wins;
    }

    public void setLegend_wins(int legend_wins) {
        this.legend_wins = legend_wins;
    }

    public int getLegend_picks() {
        return legend_picks;
    }

    public void setLegend_picks(int legend_picks) {
        this.legend_picks = legend_picks;
    }

    public int getAncient_wins() {
        return ancient_wins;
    }

    public void setAncient_wins(int ancient_wins) {
        this.ancient_wins = ancient_wins;
    }

    public int getAncient_picks() {
        return ancient_picks;
    }

    public void setAncient_picks(int ancient_picks) {
        this.ancient_picks = ancient_picks;
    }

    public int getDivine_wins() {
        return divine_wins;
    }

    public void setDivine_wins(int divine_wins) {
        this.divine_wins = divine_wins;
    }

    public int getDivine_picks() {
        return divine_picks;
    }

    public void setDivine_picks(int divine_picks) {
        this.divine_picks = divine_picks;
    }

    public int getImmortal_wins() {
        return immortal_wins;
    }

    public void setImmortal_wins(int immortal_wins) {
        this.immortal_wins = immortal_wins;
    }

    public int getImmortal_picks() {
        return immortal_picks;
    }

    public void setImmortal_picks(int immortal_picks) {
        this.immortal_picks = immortal_picks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalized_name() {
        return localized_name;
    }

    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }

    public String getPrimary_attr() {
        return primary_attr;
    }

    public void setPrimary_attr(String primary_attr) {
        this.primary_attr = primary_attr;
    }

    public String getAttack_type() {
        return attack_type;
    }

    public void setAttack_type(String attack_type) {
        this.attack_type = attack_type;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public int getBase_health() {
        return base_health;
    }

    public void setBase_health(int base_health) {
        this.base_health = base_health;
    }

    public double getBase_health_regen() {
        return base_health_regen;
    }

    public void setBase_health_regen(double base_health_regen) {
        this.base_health_regen = base_health_regen;
    }

    public int getBase_mana() {
        return base_mana;
    }

    public void setBase_mana(int base_mana) {
        this.base_mana = base_mana;
    }

    public double getBase_mana_regen() {
        return base_mana_regen;
    }

    public void setBase_mana_regen(double base_mana_regen) {
        this.base_mana_regen = base_mana_regen;
    }

    public double getBase_armor() {
        return base_armor;
    }

    public void setBase_armor(double base_armor) {
        this.base_armor = base_armor;
    }

    public int getBase_mr() {
        return base_mr;
    }

    public void setBase_mr(int base_mr) {
        this.base_mr = base_mr;
    }

    public int getBase_attack_min() {
        return base_attack_min;
    }

    public void setBase_attack_min(int base_attack_min) {
        this.base_attack_min = base_attack_min;
    }

    public int getBase_attack_max() {
        return base_attack_max;
    }

    public void setBase_attack_max(int base_attack_max) {
        this.base_attack_max = base_attack_max;
    }

    public int getBase_str() {
        return base_str;
    }

    public void setBase_str(int base_str) {
        this.base_str = base_str;
    }

    public int getBase_agi() {
        return base_agi;
    }

    public void setBase_agi(int base_agi) {
        this.base_agi = base_agi;
    }

    public int getBase_int() {
        return base_int;
    }

    public void setBase_int(int base_int) {
        this.base_int = base_int;
    }

    public double getStr_gain() {
        return str_gain;
    }

    public void setStr_gain(double str_gain) {
        this.str_gain = str_gain;
    }

    public double getAgi_gain() {
        return agi_gain;
    }

    public void setAgi_gain(double agi_gain) {
        this.agi_gain = agi_gain;
    }

    public double getInt_gain() {
        return int_gain;
    }

    public void setInt_gain(double int_gain) {
        this.int_gain = int_gain;
    }

    public int getAttack_range() {
        return attack_range;
    }

    public void setAttack_range(int attack_range) {
        this.attack_range = attack_range;
    }

    public int getProjectile_speed() {
        return projectile_speed;
    }

    public void setProjectile_speed(int projectile_speed) {
        this.projectile_speed = projectile_speed;
    }

    public double getAttack_rate() {
        return attack_rate;
    }

    public void setAttack_rate(double attack_rate) {
        this.attack_rate = attack_rate;
    }

    public int getMove_speed() {
        return move_speed;
    }

    public void setMove_speed(int move_speed) {
        this.move_speed = move_speed;
    }

    public double getTurn_rate() {
        return turn_rate;
    }

    public void setTurn_rate(double turn_rate) {
        this.turn_rate = turn_rate;
    }

    public boolean isCm_enabled() {
        return cm_enabled;
    }

    public void setCm_enabled(boolean cm_enabled) {
        this.cm_enabled = cm_enabled;
    }
}
