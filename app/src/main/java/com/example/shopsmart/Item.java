package com.example.shopsmart;

public class Item {
    private int thumbnail;
    private Class<?> activityClass;

    public Item(int thumbnail, Class<?> activityClass) {
        this.thumbnail = thumbnail;
        this.activityClass = activityClass;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }
}
