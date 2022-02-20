package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderDetailModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data {
        @SerializedName("thumbnail_image")
        public String thumbnail_image;
        @SerializedName("delivery_status")
        public String delivery_status;
        @SerializedName("payment_status")
        public String payment_status;
        @SerializedName("quantity")
        public int quantity;
        @SerializedName("shipping_cost")
        public double shipping_cost;
        @SerializedName("tax")
        public int tax;
        @SerializedName("price")
        public int price;
        @SerializedName("variation")
        public String variation;
        @SerializedName("product")
        public String product;
    }
}
