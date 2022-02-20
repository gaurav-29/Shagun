package com.oceanmtech.shagun.DashboardModule.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.oceanmtech.shagun.DashboardModule.Adapters.OrderDetailAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.OrderDetailModel;
import com.oceanmtech.shagun.DashboardModule.Models.OrderListModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    OrderDetailActivity mContext = OrderDetailActivity.this;
    ActivityOrderDetailBinding mBinding;
    ArrayList<OrderDetailModel.Data> orderDetailList = new ArrayList<>();
    ArrayList<OrderListModel.Data> orderList = new ArrayList<>();
    OrderDetailAdapter mAdapter;
    String UrlFromList, OrderDetailUrl, Name, Email, Address, City, Country, PostalCode, Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_order_detail);

        //ArrayList<OrderListModel.Data> orderList = (ArrayList<OrderListModel.Data>) getIntent().getSerializableExtra("mylist");
        // Log.d("DetailsUrl", String.valueOf(orderList));
        Bundle extras = getIntent().getExtras();
        OrderDetailUrl = extras.getString("OrderDetailUrl");
        Address = extras.getString("Address");
        Name = extras.getString("Name");
        Email = extras.getString("Email");
        City = extras.getString("City");
        Country = extras.getString("Country");
        PostalCode = extras.getString("PostalCode");
        Phone = extras.getString("Phone");

        mBinding.toolbar.tvTitle.setText("Order Detail");
        mBinding.tvName.setText(Name);
        mBinding.tvEmail.setText(Email);
        mBinding.tvAddress.setText(Address);
        mBinding.tvCity.setText(City);
        mBinding.tvPostalCode.setText(PostalCode);
        mBinding.tvCountry.setText(Country);
        mBinding.tvPhone.setText(Phone);
        getOrderList();
        getOrderDetail(OrderDetailUrl);
        onClickListeners();
    }

    private void getOrderDetail(String OrderDetailUrl) {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    getOrderDetailData(OrderDetailUrl).enqueue(new Callback<OrderDetailModel>() {
                @Override
                public void onResponse(Call<OrderDetailModel> call, Response<OrderDetailModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            Log.e("response", response.body() + "");

                            orderDetailList = response.body().data;

                            // mBinding.tvCartTotalPrice.setText(String.valueOf(orderDetailList.));

                            setDataInRecyclerView(orderDetailList);
                        } else {
                            //mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                        }
                    }
                    ProgressHubKt.dismissLoader();
                    //mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<OrderDetailModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setDataInRecyclerView(ArrayList<OrderDetailModel.Data> orderDetailList) {
        mAdapter = new OrderDetailAdapter(orderDetailList, mContext);
        mBinding.recOrderDetail.setLayoutManager(new GridLayoutManager(mContext, 1));
        mBinding.recOrderDetail.setItemAnimator(new DefaultItemAnimator());
        mBinding.recOrderDetail.setAdapter(mAdapter);
    }

    private void getOrderList() {

        ProgressHubKt.showLoader(mContext);
        String OrderListUrl = "https://shagun-ent.eshopamb.com/api/v1/purchase-history/" + PreferenceHelper.getString(Constants.ID, "");

        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    getOrderListData(OrderListUrl).enqueue(new Callback<OrderListModel>() {
                @Override
                public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            Log.e("response", response.body() + "");
                            orderList = response.body().data;
                            for (int i = 0; i < orderList.size(); i++) {
                                UrlFromList = orderList.get(i).links.details;
                                if (UrlFromList.equalsIgnoreCase(OrderDetailUrl)) {
                                    Log.d("UrlFromList", UrlFromList);
                                    mBinding.tvCartTotalPrice.setText(String.valueOf(orderList.get(i).subtotal));
                                    mBinding.tvDeliveryChargesPrice.setText((String.valueOf(orderList.get(i).shipping_cost)));
                                    mBinding.tvTotalPrice.setText(String.valueOf(orderList.get(i).grand_total));
                                    mBinding.tvCashOnDelivery.setText(orderList.get(i).payment_type);
                                    break;
                                }
                            }
                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                            ProgressHubKt.dismissLoader();
                        }
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<OrderListModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void onClickListeners() {
        mBinding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}