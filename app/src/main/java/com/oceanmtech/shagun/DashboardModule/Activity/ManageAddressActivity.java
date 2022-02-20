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

import com.oceanmtech.shagun.DashboardModule.Adapters.ManageAddressAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.ManageAddressModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityManageAddressBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAddressActivity extends AppCompatActivity {

    ActivityManageAddressBinding mBinding;
    ManageAddressActivity mContext = ManageAddressActivity.this;
    ManageAddressAdapter mAdapter;
    ArrayList<ManageAddressModel.Data> addressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_manage_address);
        mBinding.toolbar.tvTitle.setText("Manage Address");
        onCLickListener();
        getAddressList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == 3) {
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
                            mBinding.tvNoDataFound.setVisibility(View.GONE);
                            mBinding.recAddress.setVisibility(View.VISIBLE);
                            addressList = response.body().data;
                            setDataInRecyclerView(addressList);
                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                            mBinding.recAddress.setVisibility(View.GONE);
                        }
                    } else {
                        mBinding.recAddress.setVisibility(View.GONE);
                        mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                    }
                    ProgressHubKt.dismissLoader();
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
        mAdapter = new ManageAddressAdapter(addressList, mContext, new ManageAddressAdapter.OnClickDeleteAddress() {
            @Override
            public void onClickDeleteAddress(int id, int position) {
                deleteAddress(id, position);
            }
        });
        mBinding.recAddress.setAdapter(mAdapter);
    }

    private void onCLickListener() {
        mBinding.tvAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddAddressActivity.class);
            intent.putExtra("Request_Code", 3);
            startActivityForResult(intent, 3);
        });
        mBinding.toolbar.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void deleteAddress(int id, int position) {

        String deleteAddressUrl = "https://shagun-ent.eshopamb.com/api/v1/user/shipping/delete/" + id;

        if (Internet.isInternetConnected(mContext)) {
            ProgressHubKt.showLoader(mContext);
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    deleteAddressFromList(deleteAddressUrl).enqueue(new Callback<ManageAddressModel>() {
                @Override
                public void onResponse(Call<ManageAddressModel> call, Response<ManageAddressModel> response) {
                    if (response.code() == 200) {

                        addressList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemRangeChanged(position, addressList.size());
                        if (addressList.size() > 0) {

                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                            mBinding.recAddress.setVisibility(View.GONE);
                        }
                        Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}