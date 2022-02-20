package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllCategoryModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<Data> data;

    @Override
    public String toString() {
        return "AllCategoryModel{" +
                "status=" + status +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public static class Data {
        @SerializedName("links")
        public Links links;
        @SerializedName("icon")
        public String icon;
        @SerializedName("banner")
        public String banner;
        @SerializedName("name")
        public String name;

        @Override
        public String toString() {
            return "Data{" +
                    "links=" + links +
                    ", icon='" + icon + '\'' +
                    ", banner='" + banner + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Links {
        @SerializedName("sub_categories")
        public String sub_categories;
        @SerializedName("products")
        public String products;

        @Override
        public String toString() {
            return "Links{" +
                    "sub_categories='" + sub_categories + '\'' +
                    ", products='" + products + '\'' +
                    '}';
        }
    }
}
