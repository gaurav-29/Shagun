package com.oceanmtech.shagun.DashboardModule.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oceanmtech.shagun.DashboardModule.Models.PlaceOrderModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityContactUsBinding;
import com.oceanmtech.shagun.databinding.ActivityProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {

    ContactUsActivity mContext = ContactUsActivity.this;
    ActivityContactUsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_contact_us);
        onClickListeners();
    }

    private void onClickListeners() {
        mBinding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}