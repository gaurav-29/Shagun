package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetWishlistModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data {
        @SerializedName("product")
        public Product product;
        @SerializedName("id")
        public int id;
    }

    public static class Product {
        @SerializedName("links")
        public Links links;
        @SerializedName("rating")
        public int rating;
        @SerializedName("unit")
        public String unit;
        @SerializedName("base_discounted_price")
        public int base_discounted_price;
        @SerializedName("base_price")
        public int base_price;
        @SerializedName("thumbnail_image")
        public String thumbnail_image;
        @SerializedName("name")
        public String name;

        @Override
        public String toString() {
            return "Product{" +
                    "links=" + links +
                    ", rating=" + rating +
                    ", unit='" + unit + '\'' +
                    ", base_discounted_price=" + base_discounted_price +
                    ", base_price=" + base_price +
                    ", thumbnail_image='" + thumbnail_image + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Links {
        @SerializedName("top_from_seller")
        public String top_from_seller;
        @SerializedName("related")
        public String related;
        @SerializedName("reviews")
        public String reviews;
        @SerializedName("details")
        public String details;
    }
}
