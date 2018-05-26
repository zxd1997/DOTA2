package com.example.zxd1997.dota2.Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Match {
    private long replay_salt;
    private long match_id;
    private int barracks_status_dire;
    private int barracks_status_radiant;
    //    }
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
    private List<TeamFight> TeamFights;
    private int tower_status_dire;
    private int tower_status_radiant;
    private int patch;
    private int region;
    private List<PPlayer> players = new ArrayList<>();

    public List<TeamFight> getTeamFights() {
        return TeamFights;
    }

    public void setTeamFights(List<TeamFight> teamFights) {
        TeamFights = teamFights;
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
//    private Chat chat;
//    class Chat{

    public void setRadiant_xp_adv(List<Integer> radiant_xp_adv) {
        this.radiant_xp_adv = radiant_xp_adv;
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

    class TeamFight {

    }

    public class PPlayer {
        private int player_slot;
        private List<Integer> ability_upgrades_arr = new ArrayList<>();
        private long account_id;
        private int assists;
        private int backpack_0;
        private int backpack_1;
        private int backpack_2;
        private int deaths;
        private int denies;
        private int gold_per_min;
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

        public class Benchmark implements Serializable {
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

            public class GPM implements Serializable {
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

            public class XPM implements Serializable {
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

            public class KPM implements Serializable {
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

            public class LHPM implements Serializable {
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

            public class HDPM implements Serializable {
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

            public class HHPM implements Serializable {
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

            public class TD implements Serializable {
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
