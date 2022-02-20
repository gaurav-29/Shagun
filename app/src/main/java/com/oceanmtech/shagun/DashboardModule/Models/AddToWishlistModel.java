package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

public class AddToWishlistModel {

    // For Add To Wishlist API.
    @SerializedName("message")
    public String message;
    @SerializedName("product_id")
    public String product_id;
    @SerializedName("user_id")
    public String user_id;

    // For Check Product In Wishlist API.
    @SerializedName("wishlist_id")
    public int wishlist_id;
    @SerializedName("is_in_wishlist")
    public boolean is_in_wishlist;

    @Override
    public String toString() {
        return "AddToWishlistModel{" +
                "message='" + message + '\'' +
                ", product_id='" + product_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", wishlist_id=" + wishlist_id +
                ", is_in_wishlist=" + is_in_wishlist +
                '}';
    }
}
