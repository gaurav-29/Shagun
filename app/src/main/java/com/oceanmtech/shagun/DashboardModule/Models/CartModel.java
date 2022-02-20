package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data {
        @SerializedName("date")
        public String date;
        @SerializedName("quantity")
        public int quantity;
        @SerializedName("shipping_cost")
        public int shipping_cost;
        @SerializedName("tax")
        public int tax;
        @SerializedName("price")
        public int price;
        @SerializedName("product")
        public Product product;
        @SerializedName("id")
        public int id;
    }

    public static class Product {
        @SerializedName("image")
        public String image;
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public int id;
    }
}
