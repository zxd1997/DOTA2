package com.example.zxd1997.dota2.Beans;

public class Search {
    private long account_id;
    private String personaname;
    private String avatarfull;
    private String last_match_time;

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

    public String getAvatarfull() {
        return avatarfull;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public String getLast_match_time() {
        return last_match_time;
    }

    public void setLast_match_time(String last_match_time) {
        this.last_match_time = last_match_time;
    }
}
