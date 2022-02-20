package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

public class GeneralModel {

    @SerializedName("user")
    public User user;
    @SerializedName("user_id")
    public int user_id;
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public boolean result;
    @SerializedName("otp")
    public int otp;


    public static class User {
        @SerializedName("name")
        public String name;
        @SerializedName("mobile")
        public String mobile;
        @SerializedName("is_distributor")
        public String is_distributor;
        @SerializedName("distributor_id")
        public String distributor_id;
        @SerializedName("type")
        public String type;
    }
}
