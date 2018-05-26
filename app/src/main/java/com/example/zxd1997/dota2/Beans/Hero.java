package com.example.zxd1997.dota2.Beans;

public class Hero {
    private int id;
    private String name;
    private String localized_name;
    private String primary_attr;
    private String attack_type;
    //    private List<String> roles = new ArrayList<>();
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
    private double attack_range;
    private int projectile_speed;
    private double attack_rate;
    private int move_speed;
    private double turn_rate;
    private boolean cm_enabled;

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

//    public List<String> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<String> roles) {
//        this.roles = roles;
//    }

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

    public double getAttack_range() {
        return attack_range;
    }

    public void setAttack_range(double attack_range) {
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
