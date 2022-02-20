package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ManageAddressModel {

    @SerializedName("status")
    public int status;
    @SerializedName("success")
    public boolean success;
    @SerializedName("message")
    public String message;          // For delete address api.
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data {
        @SerializedName("set_default")
        public int set_default;
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
        @SerializedName("user_id")
        public int user_id;
        @SerializedName("id")
        public int id;
    }
}
