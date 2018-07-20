package com.example.zxd1997.dota2.Beans;

import java.util.List;

public class Item {
    private int id;
    private int pos;
    private String dname;
    private String qual;
    private int cost;
    private String desc;
    private String notes;
    private String lore;
    private List<Attribute> attrib;
    private List<String> components;
    private String mc;
    private String cd;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {

        this.cd = cd;
    }

    public String getMc() {

        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public List<Attribute> getAttrib() {
        return attrib;
    }

    public void setAttrib(List<Attribute> attrib) {
        this.attrib = attrib;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public class Attribute {
        private String key;
        private String header;
        //        private String value;
        private String footer;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

//        public List<String> getValue() {
//            return value;
//        }
//
//        public void setValue(List<String> value) {
//            this.value = value;
//        }

//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }

        public String getFooter() {
            return footer;
        }

        public void setFooter(String footer) {
            this.footer = footer;
        }
    }
}
