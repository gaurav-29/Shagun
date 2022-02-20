package com.oceanmtech.shagun.DashboardModule.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oceanmtech.shagun.DashboardModule.Adapters.PlaceOrderAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.CartModel;
import com.oceanmtech.shagun.DashboardModule.Models.PlaceOrderModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityPlaceOrder2Binding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity2 extends AppCompatActivity {

    PlaceOrderActivity2 mContext = PlaceOrderActivity2.this;
    ActivityPlaceOrder2Binding mBinding;
    PlaceOrderAdapter mAdapter;
    ArrayList<CartModel.Data> productDetailList = new ArrayList<>();
    String Address, City, Postal_Code, Country, Phone, addressSelectionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_place_order2);

        mBinding.toolbar.tvTitle.setText("Place Order");

        Address = getIntent().getStringExtra("ADDRESS");
        City = getIntent().getStringExtra("CITY");
        Postal_Code = getIntent().getStringExtra("POSTAL_CODE");
        Country = getIntent().getStringExtra("COUNTRY");
        Phone = getIntent().getStringExtra("PHONE");
        addressSelectionModel = getIntent().getStringExtra("addressSelectionModel");

        mBinding.tvAddress.setText(Address);
        mBinding.tvCity.setText(City);
        mBinding.tvPostalCode.setText(Postal_Code);
        mBinding.tvCountry.setText(Country);
        mBinding.tvPhone.setText(Phone);

        getCartList();
        onClickListeners();
        setDataInRecyclerView();
    }

    float total = 0;

    private void getCartList() {
        ProgressHubKt.showLoader(mContext);

        String CartUrl = "https://shagun-ent.eshopamb.com/api/v1/carts/" + PreferenceHelper.getString(Constants.ID, "");

        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    getCartList(CartUrl).enqueue(new Callback<CartModel>() {
                @Override
                public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            Log.e("response", response.body() + "");
                            productDetailList = response.body().data;
                            totalAmountOfAllItemsInCart(response.body().data);
                            for (int i = 0; i < response.body().data.size(); i++) {
                                total = (response.body().data.get(i).price * response.body().data.get(i).quantity) + total;
                            }
                            setDataInRecyclerView();
                        }
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<CartModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void totalAmountOfAllItemsInCart(ArrayList<CartModel.Data> data) {
        int totalAmount = 0;
        for (int i = 0; i < data.size(); i++) {
            totalAmount = totalAmount + (data.get(i).price * data.get(i).quantity);
        }
        mBinding.tvTotalPrice.setText(String.valueOf(totalAmount));
        mBinding.tvCartTotalPrice.setText(String.valueOf(totalAmount));
    }

    private void onClickListeners() {
        mBinding.toolbar.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
        mBinding.tvProceedToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceOrder();
//                Intent i = new Intent(mContext, OrderAcceptedActivity.class);
//                i.putExtra("CartTotal", mBinding.tvCartTotalPrice.getText().toString());
//                i.putExtra("Total", mBinding.tvTotalPrice.getText().toString());
//                i.putExtra("Address", mBinding.tvAddress.getText().toString());
//                i.putExtra("City", mBinding.tvCity.getText().toString());
//                i.putExtra("PostalCode", mBinding.tvPostalCode.getText().toString());
//                i.putExtra("Country", mBinding.tvCountry.getText().toString());
//                i.putExtra("Phone", mBinding.tvPhone.getText().toString());
//                startActivity(i);
            }
        });
    }

    public JsonObject param() {
        JsonObject paramObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_id", PreferenceHelper.getString(Constants.ID, ""));
            jsonObj_.put("shipping_address", addressSelectionModel);
            jsonObj_.put("payment_type", "cod");
            jsonObj_.put("payment_status", "cod");
            jsonObj_.put("grand_total", total);
            jsonObj_.put("coupon_discount", 0);
            jsonObj_.put("coupon_code", "");
            JsonParser jsonParser = new JsonParser();
            paramObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramObject;
    }

    private void PlaceOrder() {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).PlaceOrder(param())
                    .enqueue(new Callback<PlaceOrderModel>() {
                        @Override
                        public void onResponse(Call<PlaceOrderModel> call, Response<PlaceOrderModel> response) {
                            if (response.code() == 200) {
                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                            }
                            ProgressHubKt.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<PlaceOrderModel> call, Throwable t) {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                            Log.d("ERROR", t.getMessage());
                            ProgressHubKt.dismissLoader();
                        }
                    });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setDataInRecyclerView() {
        mAdapter = new PlaceOrderAdapter(productDetailList, mContext);
        mBinding.recProductDetail.setLayoutManager(new GridLayoutManager(mContext, 1));
        mBinding.recProductDetail.setItemAnimator(new DefaultItemAnimator());
        mBinding.recProductDetail.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}