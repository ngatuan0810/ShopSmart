package com.example.shopsmart;

public class Item {
    private int thumbnail;
    private Class<?> targetActivity; // Activity to start when the item is clicked

    public Item(int thumbnail, Class<?> targetActivity) {
        this.thumbnail = thumbnail;
        this.targetActivity = targetActivity;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Class<?> getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(Class<?> targetActivity) {
        this.targetActivity = targetActivity;
    }
}
