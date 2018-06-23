package com.example.zxd1997.dota2.Beans;

public class Peer extends PlayedHero {
    private long account_id;
    private String personaname;
    private String avatar;
    private String avatarfull;

    public String getAvatar_full() {
        return avatarfull;
    }

    public void setAvatar_full(String avatarfull) {
        this.avatarfull = avatarfull;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
