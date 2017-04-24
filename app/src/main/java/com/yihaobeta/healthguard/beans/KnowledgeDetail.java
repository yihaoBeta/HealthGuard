package com.yihaobeta.healthguard.beans;

import com.yihaobeta.healthguard.base.BaseDetailBean;

/**
 * Created by yihaobeta on 2017/3/11.
 * 养生知识实体类
 */

public class KnowledgeDetail extends BaseDetailBean {
    private int count;
    private String description;
    private int fcount;
    private int id;
    private String img;
    private String keywords;
    private int loreclass;
    private String message;
    private int rcount;
    private long time;
    private String title;
    private String url;

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

    public String getMessage() {
        return message;
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

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "KnowledgeDetail{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", fcount=" + fcount +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", keywords='" + keywords + '\'' +
                ", loreclass=" + loreclass +
                ", message='" + message + '\'' +
                ", rcount=" + rcount +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
