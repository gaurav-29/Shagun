package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

public class AddToCartModel {

    @SerializedName("message")
    public String message;
    @SerializedName("variant")
    public String variant;
    @SerializedName("id")
    public String id;
    @SerializedName("user_id")
    public String user_id;
}
