package com.example.zxd1997.dota2.Beans;

public class Attribute {
    private String key;
    private String header;
    private String value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "key='" + key + '\'' +
                ", header='" + header + '\'' +
                ", value='" + value + '\'' +
                ", footer='" + footer + '\'' +
                '}';
    }
}