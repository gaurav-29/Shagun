package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductListModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("meta")
    public Meta meta;
    @SerializedName("links")
    public Links links;
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Meta {
        @SerializedName("total")
        public int total;
        @SerializedName("to")
        public int to;
        @SerializedName("per_page")
        public int per_page;
        @SerializedName("path")
        public String path;
        @SerializedName("last_page")
        public int last_page;
        @SerializedName("from")
        public int from;
        @SerializedName("current_page")
        public int current_page;
    }

    public static class Links {
        @SerializedName("next")
        public String next;
        @SerializedName("last")
        public String last;
        @SerializedName("first")
        public String first;
    }

    public static class Data {
        @SerializedName("links")
        public Links2 links;
        @SerializedName("sales")
        public int sales;
        @SerializedName("rating")
        public int rating;
        @SerializedName("discount_type")
        public String discount_type;
        @SerializedName("discount")
        public int discount;
        @SerializedName("unit")
        public String unit;
        @SerializedName("featured")
        public int featured;
        @SerializedName("todays_deal")
        public int todays_deal;
        @SerializedName("base_discounted_price")
        public int base_discounted_price;
        @SerializedName("base_price")
        public int base_price;
        @SerializedName("thumbnail_image")
        public String thumbnail_image;
        @SerializedName("photos")
        public ArrayList<String> photos;
        @SerializedName("name")
        public String name;
    }

    public static class Links2 {
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
