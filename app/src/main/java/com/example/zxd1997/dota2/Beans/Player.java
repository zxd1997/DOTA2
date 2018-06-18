package com.example.zxd1997.dota2.Beans;

import java.io.Serializable;

public class Player implements Serializable {
    private int leaderboard_rank;
    private int rank_tier;
    private Profile profile;

    public int getLeaderboard_rank() {
        return leaderboard_rank;
    }

    public void setLeaderboard_rank(int leaderboard_rank) {
        this.leaderboard_rank = leaderboard_rank;
    }

    public int getRank_tier() {
        return rank_tier;
    }

    public void setRank_tier(int rank_tier) {
        this.rank_tier = rank_tier;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public long getAccount_id() {
        return profile.account_id;
    }

    public String getPersonaname() {
        return profile.personaname;
    }

    public String getSteamid() {
        return profile.steamid;
    }

    public String getAvatarfull() {
        return profile.avatarfull;
    }

    public String getProfileurl() {
        return profile.profileurl;
    }

    public String getLoccountrycode() {
        return profile.loccountrycode;
    }

    public class Profile implements Serializable {
        private long account_id;
        private String personaname;
        private String steamid;
        private String avatarfull;
        private String profileurl;
        private String loccountrycode;

        public long getAccount_id() {
            return account_id;
        }

        public void setAccount_id(long account_id) {
            this.account_id = account_id;
        }

        public String getPersonaname() {
            return personaname;
        }

        public void setPersonaname(String personaname) {
            this.personaname = personaname;
        }

        public String getSteamid() {
            return steamid;
        }

        public void setSteamid(String steamid) {
            this.steamid = steamid;
        }

        public String getAvatarfull() {
            return avatarfull;
        }

        public void setAvatarfull(String avatarfull) {
            this.avatarfull = avatarfull;
        }

        public String getProfileurl() {
            return profileurl;
        }

        public void setProfileurl(String profileurl) {
            this.profileurl = profileurl;
        }

        public String getLoccountrycode() {
            return loccountrycode;
        }

        public void setLoccountrycode(String loccountrycode) {
            this.loccountrycode = loccountrycode;
        }
    }
}
