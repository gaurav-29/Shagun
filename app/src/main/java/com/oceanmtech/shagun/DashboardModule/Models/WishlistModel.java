package com.oceanmtech.shagun.DashboardModule.Models;

public class WishlistModel {

    int imageId;
    String productName;

    public WishlistModel(int imageId, String productName) {
        this.imageId = imageId;
        this.productName = productName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
