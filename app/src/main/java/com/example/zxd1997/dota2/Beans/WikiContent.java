package com.example.zxd1997.dota2.Beans;

import java.util.ArrayList;
import java.util.List;

public class WikiContent {
    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public class Query {
        List<Page> pages = new ArrayList<>();

        public List<Page> getPages() {
            return pages;
        }

        public void setPages(List<Page> pages) {
            this.pages = pages;
        }

        public class Page {
            int pageid;
            String title;
            List<Revisions> revisions = new ArrayList<>();

            public int getPageid() {
                return pageid;
            }

            public void setPageid(int pageid) {
                this.pageid = pageid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<Revisions> getRevisions() {
                return revisions;
            }

            public void setRevisions(List<Revisions> revisions) {
                this.revisions = revisions;
            }

            public class Revisions {
                String content;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }
    }
}
