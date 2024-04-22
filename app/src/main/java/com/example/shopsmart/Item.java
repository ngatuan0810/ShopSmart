package com.example.shopsmart;

public class Item {

    private int Thumbnail;

    public Item() {
    }

    public Item(int thumbnail) {

        Thumbnail = thumbnail;
    }


    public int getThumbnail() {
        return Thumbnail;
    }


    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
