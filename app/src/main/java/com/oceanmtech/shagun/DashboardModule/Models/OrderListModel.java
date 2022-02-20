package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderListModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data {
        @SerializedName("links")
        public Links links;
        @SerializedName("date")
        public String date;
        @SerializedName("tax")
        public float tax;
        @SerializedName("subtotal")
        public float subtotal;
        @SerializedName("shipping_cost")
        public double shipping_cost;
        @SerializedName("coupon_discount")
        public float coupon_discount;
        @SerializedName("grand_total")
        public double grand_total;
        @SerializedName("payment_status")
        public String payment_status;
        @SerializedName("payment_type")
        public String payment_type;
        @SerializedName("shipping_address")
        public Shipping_address shipping_address;
        @SerializedName("user")
        public User user;
        @SerializedName("code")
        public String code;
    }

    public static class Links {
        @SerializedName("details")
        public String details;

        @Override
        public String toString() {
            return "Links{" +
                    "details='" + details + '\'' +
                    '}';
        }
    }

    public static class Shipping_address {
        @SerializedName("checkout_type")
        public String checkout_type;
        @SerializedName("phone")
        public String phone;
        @SerializedName("postal_code")
        public String postal_code;
        @SerializedName("city")
        public String city;
        @SerializedName("country")
        public String country;
        @SerializedName("address")
        public String address;
        @SerializedName("email")
        public String email;
        @SerializedName("name")
        public String name;
    }

    public static class User {
        @SerializedName("avatar_original")
        public String avatar_original;
        @SerializedName("email")
        public String email;
        @SerializedName("name")
        public String name;
    }
}
