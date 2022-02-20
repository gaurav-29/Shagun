package com.oceanmtech.shagun.DashboardModule.Models;

import com.google.gson.annotations.SerializedName;

public class VariantResponseModel {

    @SerializedName("in_stock")
    public boolean in_stock;
    @SerializedName("price")
    public int price;
    @SerializedName("variant")
    public String variant;
    @SerializedName("product_id")
    public int product_id;
}
