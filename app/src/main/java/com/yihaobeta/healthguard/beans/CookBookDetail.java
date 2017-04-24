package com.yihaobeta.healthguard.beans;

import com.yihaobeta.healthguard.base.BaseDetailBean;

/**
 * Created by yihaobeta on 2017/3/17.
 * <p>
 * 菜谱详情实体类
 */

public class CookBookDetail extends BaseDetailBean {
    private int count;
    private String description;
    private int fcount;
    private String food;
    private int id;
    private String images;
    private String img;
    private String keywords;
    private String message;
    private String name;
    private int rcount;
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

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public int getRcount() {
        return rcount;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "CookBookDetail{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", fcount=" + fcount +
                ", food='" + food + '\'' +
                ", id=" + id +
                ", images='" + images + '\'' +
                ", img='" + img + '\'' +
                ", keywords='" + keywords + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", rcount=" + rcount +
                ", url='" + url + '\'' +
                '}';
    }
}
