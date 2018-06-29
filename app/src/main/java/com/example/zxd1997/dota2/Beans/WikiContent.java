package com.example.zxd1997.dota2.Beans;

public class WikiContent {
    private Parse parse;

    public Parse getParse() {
        return parse;
    }

    public void setParse(Parse parse) {
        this.parse = parse;
    }

    public class Parse {
        private String title;
        private int pageid;
        private String text;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPageid() {
            return pageid;
        }

        public void setPageid(int pageid) {
            this.pageid = pageid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
