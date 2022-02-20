package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    //For Login Input and Logout
    @SerializedName("remember_me")
    public boolean remember_me;
    @SerializedName("password")
    public String password;
    @SerializedName("message")
    public String message;
    @SerializedName("email")
    public String email;

    // From Login response
    @SerializedName("user")
    public User user;
    @SerializedName("expires_at")
    public String expires_at;
    @SerializedName("token_type")
    public String token_type;
    @SerializedName("access_token")
    public String access_token;

    public static class User {
        @SerializedName("email")
        public String email;
        @SerializedName("name")
        public String name;
        @SerializedName("type")
        public String type;
        @SerializedName("id")
        public int id;
    }
}
