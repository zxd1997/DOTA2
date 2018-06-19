package com.example.zxd1997.dota2.Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Match {
    private long replay_salt;
    private long match_id;
    private int barracks_status_dire;
    private int barracks_status_radiant;
    private int dire_score;
    private int duration;
    private int first_blood_time;
    private int game_mode;
    private int lobby_type;
    private List<Integer> radiant_gold_adv = new ArrayList<>();
    private int radiant_score;
    private boolean radiant_win;
    private List<Integer> radiant_xp_adv = new ArrayList<>();
    private int skill;
    private long start_time;
    private List<TeamFight> teamfights = new ArrayList<>();
    private int tower_status_dire;
    private int tower_status_radiant;
    private int patch;
    private int region;
    private List<PPlayer> players = new ArrayList<>();
    private List<Objective> chat;
    private List<Objective> objectives = new ArrayList<>();

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public List<TeamFight> getTeamfights() {
        return teamfights;
    }

    public void setTeamfights(List<TeamFight> teamfights) {
        this.teamfights = teamfights;
    }

    public long getReplay_salt() {
        return replay_salt;
    }

    public void setReplay_salt(long replay_salt) {
        this.replay_salt = replay_salt;
    }

    public long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(long match_id) {
        this.match_id = match_id;
    }

    public int getBarracks_status_dire() {
        return barracks_status_dire;
    }

    public void setBarracks_status_dire(int barracks_status_dire) {
        this.barracks_status_dire = barracks_status_dire;
    }

    public int getBarracks_status_radiant() {
        return barracks_status_radiant;
    }

    public void setBarracks_status_radiant(int barracks_status_radiant) {
        this.barracks_status_radiant = barracks_status_radiant;
    }

    public int getDire_score() {
        return dire_score;
    }

    public void setDire_score(int dire_score) {
        this.dire_score = dire_score;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFirst_blood_time() {
        return first_blood_time;
    }

    public void setFirst_blood_time(int first_blood_time) {
        this.first_blood_time = first_blood_time;
    }

    public int getGame_mode() {
        return game_mode;
    }

    public void setGame_mode(int game_mode) {
        this.game_mode = game_mode;
    }

    public int getLobby_type() {
        return lobby_type;
    }

    public void setLobby_type(int lobby_type) {
        this.lobby_type = lobby_type;
    }

    public List<Integer> getRadiant_gold_adv() {
        return radiant_gold_adv;
    }

    public void setRadiant_gold_adv(List<Integer> radiant_gold_adv) {
        this.radiant_gold_adv = radiant_gold_adv;
    }

    public int getRadiant_score() {
        return radiant_score;
    }

    public void setRadiant_score(int radiant_score) {
        this.radiant_score = radiant_score;
    }

    public boolean isRadiant_win() {
        return radiant_win;
    }

    public void setRadiant_win(boolean radiant_win) {
        this.radiant_win = radiant_win;
    }

    public List<Integer> getRadiant_xp_adv() {
        return radiant_xp_adv;
    }

    public void setRadiant_xp_adv(List<Integer> radiant_xp_adv) {
        this.radiant_xp_adv = radiant_xp_adv;
    }

    public List<Objective> getChat() {
        return chat;
    }

    public void setChat(List<Objective> chat) {
        this.chat = chat;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public int getTower_status_dire() {
        return tower_status_dire;
    }

    public void setTower_status_dire(int tower_status_dire) {
        this.tower_status_dire = tower_status_dire;
    }

    public int getTower_status_radiant() {
        return tower_status_radiant;
    }

    public void setTower_status_radiant(int tower_status_radiant) {
        this.tower_status_radiant = tower_status_radiant;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public List<PPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<PPlayer> players) {
        this.players = players;
    }

    public class Objective {
        private int time;
        private String type;
        private int slot;
        private String unit;
        private String key;
        private int player_slot;
        private int team;
        private String hero;
        private String name;

        public String getHero_id() {
            return hero;
        }

        public void setHero_id(String hero_id) {
            this.hero = hero_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getPlayer_slot() {
            return player_slot;
        }

        public void setPlayer_slot(int player_slot) {
            this.player_slot = player_slot;
        }

        public int getTeam() {
            return team;
        }

        public void setTeam(int team) {
            this.team = team;
        }
    }

    public class TeamFight extends Objective {
        private int start;
        private int end;
        private int last_death;
        private int deaths;
        private List<TeamFightPlayer> players = new ArrayList<>();


        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getLast_death() {
            return last_death;
        }

        public void setLast_death(int last_death) {
            this.last_death = last_death;
        }

        public int getDeaths() {
            return deaths;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        public List<TeamFightPlayer> getPlayers() {
            return players;
        }

        public void setPlayers(List<TeamFightPlayer> players) {
            this.players = players;
        }

        public class TeamFightPlayer {
            Map<Integer, Map<Integer, Integer>> deaths_pos;
            Map<String, Integer> ability_uses;
            Map<String, Integer> item_uses;
            Map<String, Integer> killed;
            private int deaths;
            private int buybacks;
            private int damage;
            private int healing;
            private int gold_delta;
            private int xp_delta;
            private int player_slot;
            private String personaname;
            private String hero_id;

            public int getPlayer_slot() {
                return player_slot;
            }

            public void setPlayer_slot(int player_slot) {
                this.player_slot = player_slot;
            }

            public String getPersonaname() {
                return personaname;
            }

            public void setPersonaname(String personaname) {
                this.personaname = personaname;
            }

            public String getHero_id() {
                return hero_id;
            }

            public void setHero_id(String hero_id) {
                this.hero_id = hero_id;
            }

            public int getDeaths() {
                return deaths;
            }

            public void setDeaths(int deaths) {
                this.deaths = deaths;
            }

            public int getBuybacks() {
                return buybacks;
            }

            public void setBuybacks(int buybacks) {
                this.buybacks = buybacks;
            }

            public int getDamage() {
                return damage;
            }

            public void setDamage(int damage) {
                this.damage = damage;
            }

            public int getHealing() {
                return healing;
            }

            public void setHealing(int healing) {
                this.healing = healing;
            }

            public int getGold_delta() {
                return gold_delta;
            }

            public void setGold_delta(int gold_delta) {
                this.gold_delta = gold_delta;
            }

            public int getXp_delta() {
                return xp_delta;
            }

            public void setXp_delta(int xp_delta) {
                this.xp_delta = xp_delta;
            }

            public Map<String, Integer> getAbility_uses() {
                return ability_uses;
            }

            public void setAbility_uses(Map<String, Integer> ability_uses) {
                this.ability_uses = ability_uses;
            }

            public Map<String, Integer> getItem_uses() {
                return item_uses;
            }

            public void setItem_uses(Map<String, Integer> item_uses) {
                this.item_uses = item_uses;
            }

            public Map<String, Integer> getKilled() {
                return killed;
            }

            public void setKilled(Map<String, Integer> killed) {
                this.killed = killed;
            }

            public Map<Integer, Map<Integer, Integer>> getDeaths_pos() {
                return deaths_pos;
            }

            public void setDeaths_pos(Map<Integer, Map<Integer, Integer>> deaths_pos) {
                this.deaths_pos = deaths_pos;
            }

        }
    }

    public class PPlayer {
        private int player_slot;
        private String name;
        private List<Integer> ability_upgrades_arr = new ArrayList<>();
        private long account_id;
        private int assists;
        private int backpack_0;
        private int backpack_1;
        private int backpack_2;
        private int deaths;
        private int denies;
        private int gold_per_min;
        private int gold_spent;
        private int hero_damage;
        private int hero_healing;
        private int hero_id;
        private int item_0;
        private int item_1;
        private int item_2;
        private int item_3;
        private int item_4;
        private int item_5;
        private int kills;
        private int last_hits;
        private int leaver_status;
        private int level;
        private int tower_damage;
        private int xp_per_min;
        private int total_gold;
        private double kills_per_min;
        private int abandons;
        private int kda;
        private String personaname;
        private Benchmark benchmarks;
        private Map<String, Integer> damage_inflictor;
        private Map<String, Map<String, Integer>> damage_targets;
        private Map<String, Integer> ability_uses;
        private Map<String, Map<String, Integer>> ability_targets;
        private Map<String, Integer> damage_inflictor_received;
        private List<Integer> gold_t = new ArrayList<>();
        private List<Integer> xp_t = new ArrayList<>();
        private Map<String, Integer> hero_hits;
        private Map<String, Integer> item_uses;
        private Map<String, Integer> killed;
        private Map<String, Integer> killed_by;
        private Map<String, Integer> damage;
        private Map<String, Integer> damage_taken;
        private List<Objective> purchase_log = new ArrayList<>();
        private Map<String, Integer> gold_reasons;
        private double stuns;
        private double teamfight_participation;
        private boolean isRadiant;
        private List<Unit> additional_units = new ArrayList<>();
        private int lane_role;
        private boolean is_roaming;
        private List<Ward> obs_left_log = new ArrayList<>();
        private List<Ward> obs_log = new ArrayList<>();
        private List<Ward> sen_left_log = new ArrayList<>();
        private List<Ward> sen_log = new ArrayList<>();
        private List<Objective> buyback_log;
        private List<Objective> kills_log;
        private List<Objective> runes_log;
        private List<Buff> permanent_buffs = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGold_spent() {
            return gold_spent;
        }

        public void setGold_spent(int gold_spent) {
            this.gold_spent = gold_spent;
        }

        public List<Buff> getPermanent_buffs() {
            return permanent_buffs;
        }

        public void setPermanent_buffs(List<Buff> permanent_buffs) {
            this.permanent_buffs = permanent_buffs;
        }

        public List<Objective> getBuyback_log() {
            return buyback_log;
        }

        public void setBuyback_log(List<Objective> buyback_log) {
            this.buyback_log = buyback_log;
        }

        public List<Objective> getKills_log() {
            return kills_log;
        }

        public void setKills_log(List<Objective> kills_log) {
            this.kills_log = kills_log;
        }

        public List<Objective> getRunes_log() {
            return runes_log;
        }

        public void setRunes_log(List<Objective> runes_log) {
            this.runes_log = runes_log;
        }

        public List<Ward> getSen_left_log() {
            return sen_left_log;
        }

        public void setSen_left_log(List<Ward> sen_left_log) {
            this.sen_left_log = sen_left_log;
        }

        public List<Ward> getSen_log() {
            return sen_log;
        }

        public void setSen_log(List<Ward> sen_log) {
            this.sen_log = sen_log;
        }

        public List<Ward> getObs_left_log() {
            return obs_left_log;
        }

        public void setObs_left_log(List<Ward> obs_left_log) {
            this.obs_left_log = obs_left_log;
        }

        public List<Ward> getObs_log() {
            return obs_log;
        }

        public void setObs_log(List<Ward> obs_log) {
            this.obs_log = obs_log;
        }

        public int getLane_role() {
            return lane_role;
        }

        public void setLane_role(int lane_role) {
            this.lane_role = lane_role;
        }

        public boolean isIs_roaming() {
            return is_roaming;
        }

        public void setIs_roaming(boolean is_roaming) {
            this.is_roaming = is_roaming;
        }

        public List<Unit> getAdditional_units() {
            return additional_units;
        }

        public void setAdditional_units(List<Unit> additional_units) {
            this.additional_units = additional_units;
        }

        public boolean isRadiant() {
            return isRadiant;
        }

        public void setRadiant(boolean radiant) {
            isRadiant = radiant;
        }

        public List<Objective> getPurchase_log() {
            return purchase_log;
        }

        public void setPurchase_log(List<Objective> purchase_log) {
            this.purchase_log = purchase_log;
        }

        public Map<String, Integer> getGold_reasons() {
            return gold_reasons;
        }

        public void setGold_reasons(Map<String, Integer> gold_reasons) {
            this.gold_reasons = gold_reasons;
        }

        public Map<String, Map<String, Integer>> getDamage_targets() {
            return damage_targets;
        }

        public void setDamage_targets(Map<String, Map<String, Integer>> damage_targets) {
            this.damage_targets = damage_targets;
        }

        public Map<String, Integer> getAbility_uses() {
            return ability_uses;
        }

        public void setAbility_uses(Map<String, Integer> ability_uses) {
            this.ability_uses = ability_uses;
        }

        public Map<String, Map<String, Integer>> getAbility_targets() {
            return ability_targets;
        }

        public void setAbility_targets(Map<String, Map<String, Integer>> ability_targets) {
            this.ability_targets = ability_targets;
        }

        public Map<String, Integer> getDamage_inflictor_received() {
            return damage_inflictor_received;
        }

        public void setDamage_inflictor_received(Map<String, Integer> damage_inflictor_received) {
            this.damage_inflictor_received = damage_inflictor_received;
        }

        public List<Integer> getGold_t() {
            return gold_t;
        }

        public void setGold_t(List<Integer> gold_t) {
            this.gold_t = gold_t;
        }

        public List<Integer> getXp_t() {
            return xp_t;
        }

        public void setXp_t(List<Integer> xp_t) {
            this.xp_t = xp_t;
        }

        public Map<String, Integer> getHero_hits() {
            return hero_hits;
        }

        public void setHero_hits(Map<String, Integer> hero_hits) {
            this.hero_hits = hero_hits;
        }

        public Map<String, Integer> getItem_uses() {
            return item_uses;
        }

        public void setItem_uses(Map<String, Integer> item_uses) {
            this.item_uses = item_uses;
        }

        public Map<String, Integer> getKilled() {
            return killed;
        }

        public void setKilled(Map<String, Integer> killed) {
            this.killed = killed;
        }

        public Map<String, Integer> getKilled_by() {
            return killed_by;
        }

        public void setKilled_by(Map<String, Integer> killed_by) {
            this.killed_by = killed_by;
        }

        public Map<String, Integer> getDamage() {
            return damage;
        }

        public void setDamage(Map<String, Integer> damage) {
            this.damage = damage;
        }

        public Map<String, Integer> getDamage_taken() {
            return damage_taken;
        }

        public void setDamage_taken(Map<String, Integer> damage_taken) {
            this.damage_taken = damage_taken;
        }

        public double getStuns() {
            return stuns;
        }

        public void setStuns(double stuns) {
            this.stuns = stuns;
        }

        public double getTeamfight_participation() {
            return teamfight_participation;
        }

        public void setTeamfight_participation(double teamfight_participation) {
            this.teamfight_participation = teamfight_participation;
        }

        public Map<String, Integer> getDamage_inflictor() {
            return damage_inflictor;
        }

        public void setDamage_inflictor(Map<String, Integer> damage_inflictor) {
            this.damage_inflictor = damage_inflictor;
        }

        public int getPlayer_slot() {
            return player_slot;
        }

        public void setPlayer_slot(int player_slot) {
            this.player_slot = player_slot;
        }

        public List<Integer> getAbility_upgrades_arr() {
            return ability_upgrades_arr;
        }

        public void setAbility_upgrades_arr(List<Integer> ability_upgrades_arr) {
            this.ability_upgrades_arr = ability_upgrades_arr;
        }

        public long getAccount_id() {
            return account_id;
        }

        public void setAccount_id(long account_id) {
            this.account_id = account_id;
        }

        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }

        public int getBackpack_0() {
            return backpack_0;
        }

        public void setBackpack_0(int backpack_0) {
            this.backpack_0 = backpack_0;
        }

        public int getBackpack_1() {
            return backpack_1;
        }

        public void setBackpack_1(int backpack_1) {
            this.backpack_1 = backpack_1;
        }

        public int getBackpack_2() {
            return backpack_2;
        }

        public void setBackpack_2(int backpack_2) {
            this.backpack_2 = backpack_2;
        }

        public int getDeaths() {
            return deaths;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        public int getDenies() {
            return denies;
        }

        public void setDenies(int denies) {
            this.denies = denies;
        }

        public int getGold_per_min() {
            return gold_per_min;
        }

        public void setGold_per_min(int gold_per_min) {
            this.gold_per_min = gold_per_min;
        }

        public int getHero_damage() {
            return hero_damage;
        }

        public void setHero_damage(int hero_damage) {
            this.hero_damage = hero_damage;
        }

        public int getHero_healing() {
            return hero_healing;
        }

        public void setHero_healing(int hero_healing) {
            this.hero_healing = hero_healing;
        }

        public int getHero_id() {
            return hero_id;
        }

        public void setHero_id(int hero_id) {
            this.hero_id = hero_id;
        }

        public int getItem_0() {
            return item_0;
        }

        public void setItem_0(int item_0) {
            this.item_0 = item_0;
        }

        public int getItem_1() {
            return item_1;
        }

        public void setItem_1(int item_1) {
            this.item_1 = item_1;
        }

        public int getItem_2() {
            return item_2;
        }

        public void setItem_2(int item_2) {
            this.item_2 = item_2;
        }

        public int getItem_3() {
            return item_3;
        }

        public void setItem_3(int item_3) {
            this.item_3 = item_3;
        }

        public int getItem_4() {
            return item_4;
        }

        public void setItem_4(int item_4) {
            this.item_4 = item_4;
        }

        public int getItem_5() {
            return item_5;
        }

        public void setItem_5(int item_5) {
            this.item_5 = item_5;
        }

        public int getKills() {
            return kills;
        }

        public void setKills(int kills) {
            this.kills = kills;
        }

        public int getLast_hits() {
            return last_hits;
        }

        public void setLast_hits(int last_hits) {
            this.last_hits = last_hits;
        }

        public int getLeaver_status() {
            return leaver_status;
        }

        public void setLeaver_status(int leaver_status) {
            this.leaver_status = leaver_status;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getTower_damage() {
            return tower_damage;
        }

        public void setTower_damage(int tower_damage) {
            this.tower_damage = tower_damage;
        }

        public int getXp_per_min() {
            return xp_per_min;
        }

        public void setXp_per_min(int xp_per_min) {
            this.xp_per_min = xp_per_min;
        }

        public int getTotal_gold() {
            return total_gold;
        }

        public void setTotal_gold(int total_gold) {
            this.total_gold = total_gold;
        }

        public double getKills_per_min() {
            return kills_per_min;
        }

        public void setKills_per_min(double kills_per_min) {
            this.kills_per_min = kills_per_min;
        }

        public int getAbandons() {
            return abandons;
        }

        public void setAbandons(int abandons) {
            this.abandons = abandons;
        }

        public int getKda() {
            return kda;
        }

        public void setKda(int kda) {
            this.kda = kda;
        }

        public String getPersonaname() {
            return personaname;
        }

        public void setPersonaname(String personaname) {
            this.personaname = personaname;
        }

        public Benchmark getBenchmarks() {
            return benchmarks;
        }

        public void setBenchmarks(Benchmark benchmarks) {
            this.benchmarks = benchmarks;
        }

        public class Buff {
            private int permanent_buff;
            private int stack_count;

            public int getPermanent_buff() {
                return permanent_buff;
            }

            public void setPermanent_buff(int permanent_buff) {
                this.permanent_buff = permanent_buff;
            }

            public int getStack_count() {
                return stack_count;
            }

            public void setStack_count(int stack_count) {
                this.stack_count = stack_count;
            }
        }

        public class Ward {
            private int time;
            private String type;
            private String key;
            private int slot;
            private int x;
            private int y;
            private int z;
            private boolean entityleft;
            private long ehandle;
            private int player_slot;

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public int getSlot() {
                return slot;
            }

            public void setSlot(int slot) {
                this.slot = slot;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getZ() {
                return z;
            }

            public void setZ(int z) {
                this.z = z;
            }

            public boolean isEntityleft() {
                return entityleft;
            }

            public void setEntityleft(boolean entityleft) {
                this.entityleft = entityleft;
            }

            public long getEhandle() {
                return ehandle;
            }

            public void setEhandle(long ehandle) {
                this.ehandle = ehandle;
            }

            public int getPlayer_slot() {
                return player_slot;
            }

            public void setPlayer_slot(int player_slot) {
                this.player_slot = player_slot;
            }
        }

        public class Unit {
            private String unitname;
            private int item_0;
            private int item_1;
            private int item_2;
            private int item_3;
            private int item_4;
            private int item_5;
            private int backpack_0;
            private int backpack_1;
            private int backpack_2;

            public String getUnitname() {
                return unitname;
            }

            public void setUnitname(String unitname) {
                this.unitname = unitname;
            }

            public int getItem_0() {
                return item_0;
            }

            public void setItem_0(int item_0) {
                this.item_0 = item_0;
            }

            public int getItem_1() {
                return item_1;
            }

            public void setItem_1(int item_1) {
                this.item_1 = item_1;
            }

            public int getItem_2() {
                return item_2;
            }

            public void setItem_2(int item_2) {
                this.item_2 = item_2;
            }

            public int getItem_3() {
                return item_3;
            }

            public void setItem_3(int item_3) {
                this.item_3 = item_3;
            }

            public int getItem_4() {
                return item_4;
            }

            public void setItem_4(int item_4) {
                this.item_4 = item_4;
            }

            public int getItem_5() {
                return item_5;
            }

            public void setItem_5(int item_5) {
                this.item_5 = item_5;
            }

            public int getBackpack_0() {
                return backpack_0;
            }

            public void setBackpack_0(int backpack_0) {
                this.backpack_0 = backpack_0;
            }

            public int getBackpack_1() {
                return backpack_1;
            }

            public void setBackpack_1(int backpack_1) {
                this.backpack_1 = backpack_1;
            }

            public int getBackpack_2() {
                return backpack_2;
            }

            public void setBackpack_2(int backpack_2) {
                this.backpack_2 = backpack_2;
            }
        }

        public class Benchmark {
            private GPM gold_per_min;
            private XPM xp_per_min;
            private KPM kills_per_min;
            private LHPM last_hits_per_min;
            private HDPM hero_damage_per_min;
            private HHPM hero_healing_per_min;
            private TD tower_damage;

            public GPM getGold_per_min() {
                return gold_per_min;
            }

            public void setGold_per_min(GPM gold_per_min) {
                this.gold_per_min = gold_per_min;
            }

            public XPM getXp_per_min() {
                return xp_per_min;
            }

            public void setXp_per_min(XPM xp_per_min) {
                this.xp_per_min = xp_per_min;
            }

            public KPM getKills_per_min() {
                return kills_per_min;
            }

            public void setKills_per_min(KPM kills_per_min) {
                this.kills_per_min = kills_per_min;
            }

            public LHPM getLast_hits_per_min() {
                return last_hits_per_min;
            }

            public void setLast_hits_per_min(LHPM last_hits_per_min) {
                this.last_hits_per_min = last_hits_per_min;
            }

            public HDPM getHero_damage_per_min() {
                return hero_damage_per_min;
            }

            public void setHero_damage_per_min(HDPM hero_damage_per_min) {
                this.hero_damage_per_min = hero_damage_per_min;
            }

            public HHPM getHero_healing_per_min() {
                return hero_healing_per_min;
            }

            public void setHero_healing_per_min(HHPM hero_healing_per_min) {
                this.hero_healing_per_min = hero_healing_per_min;
            }

            public TD getTower_damage() {
                return tower_damage;
            }

            public void setTower_damage(TD tower_damage) {
                this.tower_damage = tower_damage;
            }

            public class GPM {
                private int raw;
                private double pct;

                public int getRaw() {
                    return raw;
                }

                public void setRaw(int raw) {
                    this.raw = raw;
                }

                public double getPct() {
                    return pct;
                }

                public void setPct(double pct) {
                    this.pct = pct;
                }
            }

            public class XPM {
                private int raw;
                private double pct;

                public int getRaw() {
                    return raw;
                }

                public void setRaw(int raw) {
                    this.raw = raw;
                }

                public double getPct() {
                    return pct;
                }

                public void setPct(double pct) {
                    this.pct = pct;
                }
            }

            public class KPM {
                private double raw;
                private double pct;

                public double getRaw() {
                    return raw;
                }

                public void setRaw(double raw) {
                    this.raw = raw;
                }

                public double getPct() {
                    return pct;
                }

                public void setPct(double pct) {
                    this.pct = pct;
                }
            }

            public class LHPM {
                private double raw;
                private double pct;

                public double getPct() {
                    return pct;
                }

                public void setPct(double pct) {
                    this.pct = pct;
                }

                public double getRaw() {
                    return raw;
                }

                public void setRaw(double raw) {
                    this.raw = raw;
                }
            }

            public class HDPM {
                private double raw;
                private double pct;

                public double getPct() {
                    return pct;
                }

                public void setPct(double pct) {
                    this.pct = pct;
                }

                public double getRaw() {
                    return raw;
                }

                public void setRaw(double raw) {
                    this.raw = raw;
                }
            }

            public class HHPM {
                private double raw;
                private double pct;

                public double getPct() {
                    return pct;
                }

                public void setPct(double pct) {
                    this.pct = pct;
                }

                public double getRaw() {
                    return raw;
                }

                public void setRaw(double raw) {
                    this.raw = raw;
                }
            }

            public class TD {
                private int raw;
                private double pct;

                public int getRaw() {
                    return raw;
                }

                public void setRaw(int raw) {
                    this.raw = raw;
                }

                public double getPct() {
                    return pct;
                }

                public void setPct(double pct) {
                    this.pct = pct;
                }
            }
        }
    }
}
