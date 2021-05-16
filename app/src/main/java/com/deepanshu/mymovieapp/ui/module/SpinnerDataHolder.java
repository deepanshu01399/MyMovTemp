package com.deepanshu.mymovieapp.ui.module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SpinnerDataHolder implements Serializable {


    private int id;
    @SerializedName("sort_order")
    private int sortOrder;
    @SerializedName("key")
    private String key;
    @SerializedName("value")
    private String value;
    @SerializedName("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
