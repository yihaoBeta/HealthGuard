package com.yihaobeta.healthguard.beans;

import com.google.gson.annotations.SerializedName;
import com.yihaobeta.healthguard.base.BaseDetailBean;

/**
 * Created by yihaobeta on 2017/3/12.
 * 药品详情实体类
 */

public class MedicineDetail extends BaseDetailBean {


    private float price;
    private String tag;
    private String type;
    private String description;
    private int fcount;
    private int id;
    private String img;
    private String keywords;
    private String message;
    @SerializedName("name")
    private String title;

    @Override
    public String toString() {
        return "MedicineDetail{" +
                "price=" + price +
                ", tag='" + tag + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", fcount=" + fcount +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", keywords='" + keywords + '\'' +
                ", message='" + message + '\'' +
                ", name='" + title + '\'' +
                '}';
    }

    public String getName() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public String getTag() {
        return tag;
    }

    public String getType() {
        return type;
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

    public String getMessage() {
        return message;
    }
}
