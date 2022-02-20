package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

public class LogoutModel {

    @SerializedName("message")
    public String message;
    @SerializedName("remember_me")
    public boolean remember_me;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;
}
