// Domain/Product.java
package com.example.shopsmart.Domain;

import android.content.Context;

import java.io.Serializable;

public class Product implements Serializable {
    private Context context;
    private String id;
    private String title;
    private int number_retailers;
    private float score_rating;
    private String brand;
    private String type;
    private int imageId;
    private int imageIdFromID;
    private double jbhifi_fee;
    private double officework_fee;
    private double goodguys_fee;
    private double bigw_fee;
    private double brand_fee;
    private String releaseDate;
    private String jbhifi_link;
    private String goodguys_link;
    private String officework_link;
    private String bigw_link;
    private String brand_link;
    private String description;
    private String specs;
    private boolean isFavourite;

    // Constructor
    public Product(String id, String title, float score_rating, String brand, String type, double jbhifi_fee,
                   double officework_fee, double goodguys_fee, double bigw_fee, double brand_fee, String releaseDate,
                   String jbhifi_link, String goodguys_link, String officework_link, String bigw_link, String brand_link,
                   String description, String specs) {
        this.id = id;
        this.title = title;
        this.score_rating = score_rating;
        this.brand = brand;
        this.type = type;
        this.jbhifi_fee = jbhifi_fee;
        this.officework_fee = officework_fee;
        this.goodguys_fee = goodguys_fee;
        this.bigw_fee = bigw_fee;
        this.brand_fee = brand_fee;
        this.releaseDate = releaseDate;
        this.jbhifi_link = jbhifi_link;
        this.goodguys_link = goodguys_link;
        this.officework_link = officework_link;
        this.bigw_link = bigw_link;
        this.brand_link = brand_link;
        this.description = description;
        this.specs = specs;
    }

    public Product(String iPhone, String apple, String phone) {
    }

    // Getters and setters
    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getJbhifi_link() {
        return jbhifi_link;
    }

    public void setJbhifi_link(String jbhifi_link) {
        this.jbhifi_link = jbhifi_link;
    }

    public String getGoodguys_link() {
        return goodguys_link;
    }

    public void setGoodguys_link(String goodguys_link) {
        this.goodguys_link = goodguys_link;
    }

    public String getOfficework_link() {
        return officework_link;
    }

    public void setOfficework_link(String officework_link) {
        this.officework_link = officework_link;
    }

    public String getBigw_link() {
        return bigw_link;
    }

    public void setBigw_link(String bigw_link) {
        this.bigw_link = bigw_link;
    }

    public String getBrand_link() {
        return brand_link;
    }

    public void setBrand_link(String brand_link) {
        this.brand_link = brand_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getImageIdFromID() {
        return imageIdFromID;
    }

    public void setImageIdFromID(int imageIdFromID) {
        this.imageIdFromID = imageIdFromID;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber_retailers() {
        return number_retailers;
    }

    public void setNumber_retailers(int number_retailers) {
        this.number_retailers = number_retailers;
    }

    public float getScore_rating() {
        return score_rating;
    }

    public void setScore_rating(float score_rating) {
        this.score_rating = score_rating;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getJbhifi_fee() {
        return jbhifi_fee;
    }

    public void setJbhifi_fee(double jbhifi_fee) {
        this.jbhifi_fee = jbhifi_fee;
    }

    public double getOfficework_fee() {
        return officework_fee;
    }

    public void setOfficework_fee(double officework_fee) {
        this.officework_fee = officework_fee;
    }

    public double getGoodguys_fee() {
        return goodguys_fee;
    }

    public void setGoodguys_fee(double goodguys_fee) {
        this.goodguys_fee = goodguys_fee;
    }

    public double getBigw_fee() {
        return bigw_fee;
    }

    public void setBigw_fee(double bigw_fee) {
        this.bigw_fee = bigw_fee;
    }

    public double getBrand_fee() {
        return brand_fee;
    }

    public void setBrand_fee(double brand_fee) {
        this.brand_fee = brand_fee;
    }

    // Method to calculate the minimum fee among all fee options
    public double getMinFee() {
        double minFee = Double.MAX_VALUE;
        if (jbhifi_fee > 0) minFee = Math.min(minFee, jbhifi_fee);
        if (officework_fee > 0) minFee = Math.min(minFee, officework_fee);
        if (goodguys_fee > 0) minFee = Math.min(minFee, goodguys_fee);
        if (bigw_fee > 0) minFee = Math.min(minFee, bigw_fee);
        if (brand_fee > 0) minFee = Math.min(minFee, brand_fee);
        return minFee;
    }

    public String getImageFolder() {
        return "gs://shopsmart-29cfe.appspot.com/" + id;
    }

    public static String getImageResourceName(String id) {
        return id;
    }
}
