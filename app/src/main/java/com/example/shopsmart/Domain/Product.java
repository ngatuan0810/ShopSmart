// Domain/Product.java
package com.example.shopsmart.Domain;

import android.content.Context;
import java.util.Date;


public class Product {
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

    private boolean isFavourite;

    // Constructor, getters, and setters for other attributes

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }



    public Product(Context context, String id, String title, int number_retailers, float score_rating, String brand, String type, double jbhifi_fee, double officework_fee, double goodguys_fee, double bigw_fee, double brand_fee, String releaseDate) {
        this.context = context;
        this.id = id;
        this.title = title;
        this.number_retailers = number_retailers;
        this.score_rating = score_rating;
        this.brand = brand;
        this.type = type;
//        this.imageId = getImageIdFromBrand(brand);
        this.jbhifi_fee = jbhifi_fee;
        this.officework_fee = officework_fee;
        this.goodguys_fee = goodguys_fee;
        this.bigw_fee = bigw_fee;
        this.brand_fee = brand_fee;
        this.releaseDate = releaseDate;
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

//    private int getImageIdFromBrand(String brand) {
//        String brandLowercase = brand.toLowerCase();
//        String resourceName = "drawable/" + brandLowercase;
//        return context.getResources().getIdentifier(resourceName, null, context.getPackageName());
//    }
    public static String getImageResourceName(String id) {
        return id;
    }
}
