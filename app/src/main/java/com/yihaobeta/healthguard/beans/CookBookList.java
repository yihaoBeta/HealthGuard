package com.yihaobeta.healthguard.beans;

import java.util.List;

/**
 * Created by yihaobeta on 2017/3/14.
 * 菜单列表实体类
 */

public class CookBookList {
    private boolean status;
    private int total;
    private List<CookBookSummary> tngou;

    public boolean isStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public List<CookBookSummary> getTngou() {
        return tngou;
    }

    public class CookBookSummary {
        private int count;
        private String description;
        private int fcount;
        private String food;
        private int id;
        private String images;
        private String img;
        private String keywords;
        private String name;
        private int rcount;

        public int getCount() {
            return count;
        }

        public String getDescription() {
            return description;
        }

        public int getFcount() {
            return fcount;
        }

        public String getFood() {
            return food;
        }

        public int getId() {
            return id;
        }

        public String getImages() {
            return images;
        }

        public String getImg() {
            return img;
        }

        public String getKeywords() {
            return keywords;
        }

        public String getName() {
            return name;
        }

        public int getRcount() {
            return rcount;
        }
    }
}
