package com.oceanmtech.shagun.DashboardModule.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.oceanmtech.shagun.DashboardModule.Adapters.SelectAddressAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.ManageAddressModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.DashboardModule.Utils.SelectAddressInterface;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivitySelectAddressBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressActivity extends AppCompatActivity implements SelectAddressInterface {

    ActivitySelectAddressBinding mBinding;
    SelectAddressActivity mContext = SelectAddressActivity.this;
    SelectAddressAdapter mAdapter;
    ArrayList<ManageAddressModel.Data> addressList = new ArrayList<>();
    String Address, City, Postal_Code, Country, Phone, DefaultAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_select_address);
        mBinding.toolbar.tvTitle.setText("Select Address");
        onCLickListener();
        getAddressList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 2) {
            getAddressList();
        }
    }

    private void getAddressList() {
        ProgressHubKt.showLoader(mContext);
        String addressListUrl = "https://shagun-ent.eshopamb.com/api/v1/user/shipping/address/" + PreferenceHelper.getString(Constants.ID, "");

        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    getAddressList(addressListUrl).enqueue(new Callback<ManageAddressModel>() {
                @Override
                public void onResponse(Call<ManageAddressModel> call, Response<ManageAddressModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            addressList = response.body().data;
                            setDataInRecyclerView(addressList);
                            mBinding.tvNoDataFound.setVisibility(View.GONE);
                            mBinding.recAddress.setVisibility(View.VISIBLE);
                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                            mBinding.recAddress.setVisibility(View.GONE);
                        }
                        ProgressHubKt.dismissLoader();
                    }
                }

                @Override
                public void onFailure(Call<ManageAddressModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setDataInRecyclerView(ArrayList<ManageAddressModel.Data> addressList) {
        mBinding.recAddress.setLayoutManager(new GridLayoutManager(mContext, 1));
        mAdapter = new SelectAddressAdapter(addressList, mContext, this);
        mBinding.recAddress.setAdapter(mAdapter);
    }

    private void onCLickListener() {
        mBinding.ivAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddAddressActivity.class);
            intent.putExtra("Request_Code", 2);
            startActivityForResult(intent, 2);
        });
        mBinding.toolbar.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
        mBinding.tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Address == null || Address.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please select address", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(mContext, PlaceOrderActivity2.class);
                    intent.putExtra("ADDRESS", Address);
                    intent.putExtra("CITY", City);
                    intent.putExtra("POSTAL_CODE", Postal_Code);
                    intent.putExtra("COUNTRY", Country);
                    intent.putExtra("PHONE", Phone);
                    intent.putExtra("addressSelectionModel", new Gson().toJson(addressSelectionModel));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    ManageAddressModel.Data addressSelectionModel = new ManageAddressModel.Data();

    @Override
    public void selectedAddress(String address, String city, String postal_code, String country, String phone, ManageAddressModel.Data data) {
        Address = address;
        City = city;
        Postal_Code = postal_code;
        Country = country;
        Phone = phone;
        addressSelectionModel = data;
    }
}