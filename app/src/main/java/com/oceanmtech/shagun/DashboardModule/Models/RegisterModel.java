package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class RegisterModel {

    @SerializedName("errors")
    public Errors errors;
    @SerializedName("message")
    public String message;
    @SerializedName("passowrd_confirmation")
    public String passowrd_confirmation;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;

    public static class Errors {
        @SerializedName("email")
        public List<String> email;
    }

}
