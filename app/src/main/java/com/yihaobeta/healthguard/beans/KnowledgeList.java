package com.yihaobeta.healthguard.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaobeta on 2017/3/10.
 * 养生知识列表
 */

public class KnowledgeList {

    private int total;
    private List<KnowledgeSummary> tngou = new ArrayList<>();

    public int getTotal() {
        return total;
    }

    public List<KnowledgeSummary> getTngou() {
        return tngou;
    }

    public class KnowledgeSummary {
        private int count;
        private String description;
        private int fcount;
        private int id;
        private String img;
        private String keywords;
        private int loreclass;
        private int rcount;
        private long time;
        private String title;

        public int getCount() {
            return count;
        }

        public String getDescription() {
            return description;
        }

        public int getFcount() {
            return fcount;
        }

        public int getId() {
            return id;
        }

        public String getImg() {
            return img;
        }

        public String getKeywords() {
            return keywords;
        }

        public int getLoreclass() {
            return loreclass;
        }

        public int getRcount() {
            return rcount;
        }

        public long getTime() {
            return time;
        }

        public String getTitle() {
            return title;
        }
    }
}
