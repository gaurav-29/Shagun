package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

public class AddAddressModel {


    @SerializedName("message")
    public String message;
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
    public String user_id;
}
