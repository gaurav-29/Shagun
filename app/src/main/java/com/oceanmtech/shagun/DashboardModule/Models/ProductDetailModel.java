package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data {
        @SerializedName("links")
        public Links links;
        @SerializedName("description")
        public String description;
        @SerializedName("rating_count")
        public int rating_count;
        @SerializedName("rating")
        public int rating;
        @SerializedName("number_of_sales")
        public int number_of_sales;
        @SerializedName("shipping_cost")
        public int shipping_cost;
        @SerializedName("tax_type")
        public String tax_type;
        @SerializedName("tax")
        public int tax;
        @SerializedName("discount_type")
        public String discount_type;
        @SerializedName("discount")
        public int discount;
        @SerializedName("unit")
        public String unit;
        @SerializedName("current_stock")
        public int current_stock;
        @SerializedName("featured")
        public int featured;
        @SerializedName("todays_deal")
        public int todays_deal;
        @SerializedName("colors")
        public List<String> colors;
        @SerializedName("choice_options")
        public List<Choice_options> choice_options;
        @SerializedName("price_higher")
        public int price_higher;
        @SerializedName("price_lower")
        public int price_lower;
        @SerializedName("tags")
        public List<String> tags;
        @SerializedName("thumbnail_image")
        public String thumbnail_image;
        @SerializedName("photos")
        public ArrayList<String> photos;
        @SerializedName("brand")
        public Brand brand;
        @SerializedName("category")
        public Category category;
        @SerializedName("user")
        public User user;
        @SerializedName("added_by")
        public String added_by;
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public int id;
    }

    public static class Links {
        @SerializedName("related")
        public String related;
        @SerializedName("reviews")
        public String reviews;
    }

    public static class Choice_options {
        @SerializedName("options")
        public List<String> options;
        @SerializedName("title")
        public String title;
        @SerializedName("name")
        public String name;
    }

    public static class Brand {
        @SerializedName("links")
        public Links links;
        @SerializedName("logo")
        public String logo;
        @SerializedName("name")
        public String name;
    }

    public static class Links2 {
        @SerializedName("products")
        public String products;
    }

    public static class Category {
        @SerializedName("links")
        public Links links;
        @SerializedName("icon")
        public String icon;
        @SerializedName("banner")
        public String banner;
        @SerializedName("name")
        public String name;
    }

    public static class Links3 {
        @SerializedName("sub_categories")
        public String sub_categories;
        @SerializedName("products")
        public String products;
    }

    public static class User {
        @SerializedName("shop_link")
        public String shop_link;
        @SerializedName("shop_logo")
        public String shop_logo;
        @SerializedName("shop_name")
        public String shop_name;
        @SerializedName("avatar_original")
        public String avatar_original;
        @SerializedName("email")
        public String email;
        @SerializedName("name")
        public String name;
    }
}
