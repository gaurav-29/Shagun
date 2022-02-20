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
import com.oceanmtech.shagun.databinding.ActivityProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    ProfileActivity mContext = ProfileActivity.this;
    ActivityProfileBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_profile);
        mBinding.etName.setText(PreferenceHelper.getString(Constants.NAME, ""));
        mBinding.etEmail.setText(PreferenceHelper.getString(Constants.EMAIL, ""));
        onClickListeners();
    }

    private void onClickListeners() {
        mBinding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
        mBinding.tvUpdateProfile.setOnClickListener(v -> {
            if (mBinding.etName.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(mContext, "Please enter name", Toast.LENGTH_SHORT).show();
            } else {
                UpdateProfile();
            }
        });
    }

    public JsonObject param() {
        JsonObject paramObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_id", PreferenceHelper.getString(Constants.ID, ""));
            jsonObj_.put("name", mBinding.etName.getText().toString());
            JsonParser jsonParser = new JsonParser();
            paramObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramObject;
    }

    private void UpdateProfile() {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).updateProfileInfo(param())
                    .enqueue(new Callback<PlaceOrderModel>() {
                        @Override
                        public void onResponse(Call<PlaceOrderModel> call, Response<PlaceOrderModel> response) {
                            if (response.code() == 200) {
                                PreferenceHelper.putString(Constants.NAME, mBinding.etName.getText().toString());
                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                                onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}