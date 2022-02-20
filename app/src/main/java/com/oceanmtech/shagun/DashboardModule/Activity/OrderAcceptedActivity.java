package com.oceanmtech.shagun.DashboardModule.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityOrderAcceptedBinding;

public class OrderAcceptedActivity extends AppCompatActivity {

    OrderAcceptedActivity mContext = OrderAcceptedActivity.this;
    ActivityOrderAcceptedBinding mBinding;
    String Address, City, Postal_Code, Country, Phone, CartTotal, Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_order_accepted);

        CartTotal = getIntent().getStringExtra("CartTotal");
        Total = getIntent().getStringExtra("Total");
        Address = getIntent().getStringExtra("Address");
        City = getIntent().getStringExtra("City");
        Postal_Code = getIntent().getStringExtra("PostalCode");
        Country = getIntent().getStringExtra("Country");
        Phone = getIntent().getStringExtra("Phone");

        mBinding.tvCartTotalPrice.setText(CartTotal);
        mBinding.tvTotalPrice.setText(Total);
        mBinding.tvAddress.setText(Address);
        mBinding.tvCity.setText(City);
        mBinding.tvPostalCode.setText(Postal_Code);
        mBinding.tvCountry.setText(Country);
        mBinding.tvPhone.setText(Phone);

        onClickListeners();
    }

    private void onClickListeners() {

        mBinding.tvBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MainActivity.class);
                startActivity(i);
            }
        });
        mBinding.tvOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(mContext, PlaceOrderActivity2.class);
                startActivity(i2);
            }
        });
    }
}