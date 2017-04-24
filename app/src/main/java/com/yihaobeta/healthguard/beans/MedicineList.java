package com.yihaobeta.healthguard.beans;

import java.util.List;

/**
 * Created by yihaobeta on 2017/3/12.
 * 药品列表实体类
 */

public class MedicineList {
    private int total;
    private List<MedicineSummary> tngou;

    public int getTotal() {
        return total;
    }

    public List<MedicineSummary> getTngou() {
        return tngou;
    }

    public class MedicineSummary {
        private int count;
        private String description;
        private int fcount;
        private int id;
        private String img;
        private String keywords;
        private String name;
        private float price;
        private int rcount;
        private String tag;
        private String type;

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

        public String getName() {
            return name;
        }

        public float getPrice() {
            return price;
        }

        public int getRcount() {
            return rcount;
        }

        public String getTag() {
            return tag;
        }

        public String getType() {
            return type;
        }
    }
}
