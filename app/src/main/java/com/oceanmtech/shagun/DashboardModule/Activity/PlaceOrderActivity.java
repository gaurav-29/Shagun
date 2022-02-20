package com.oceanmtech.shagun.DashboardModule.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityPlaceOrderBinding;

public class PlaceOrderActivity extends AppCompatActivity {

    PlaceOrderActivity mContext = PlaceOrderActivity.this;
    ActivityPlaceOrderBinding mBinding;
    RadioButton selectedRadioButton;

    String[] country = {"India", "France", "Germany", "USA", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_place_order);

        setCountrySpinner();
        onClickListeners();
    }

    private void onClickListeners() {
        mBinding.tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = mBinding.rdGroup.getCheckedRadioButtonId();
                selectedRadioButton = findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(mContext, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBinding.tvContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = mBinding.rdGroup2.getCheckedRadioButtonId();
                selectedRadioButton = findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(mContext, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBinding.tvContinue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = mBinding.rdGroup3.getCheckedRadioButtonId();
                selectedRadioButton = findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(mContext, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBinding.tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, OrderAcceptedActivity.class);
                startActivity(i);
            }
        });
    }

    private void setCountrySpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item2, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spCountry.setAdapter(aa);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}