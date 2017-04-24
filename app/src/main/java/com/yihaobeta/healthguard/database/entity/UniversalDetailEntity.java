package com.yihaobeta.healthguard.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by yihaobeta on 2017/3/27.
 */

@Entity
public class UniversalDetailEntity {
    @Id
    private String key;
    private int id;
    private int type;
    private String imgUrl;
    private String title;
    private String message;
    private String description;
    private String keywords;
    private float price;//药品详情使用
    private String medicineType;//药品类型
    private boolean favorite;
    private boolean history;
    private long timeStamp;//时间戳

    @Generated(hash = 906492909)
    public UniversalDetailEntity(String key, int id, int type, String imgUrl,
                                 String title, String message, String description, String keywords,
                                 float price, String medicineType, boolean favorite, boolean history,
                                 long timeStamp) {
        this.key = key;
        this.id = id;
        this.type = type;
        this.imgUrl = imgUrl;
        this.title = title;
        this.message = message;
        this.description = description;
        this.keywords = keywords;
        this.price = price;
        this.medicineType = medicineType;
        this.favorite = favorite;
        this.history = history;
        this.timeStamp = timeStamp;
    }

    @Generated(hash = 1868208894)
    public UniversalDetailEntity() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getMedicineType() {
        return this.medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public boolean getFavorite() {
        return this.favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean getHistory() {
        return this.history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "UniversalDetailEntity{" +
                "key='" + key + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", favorite=" + favorite +
                ", history=" + history +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
