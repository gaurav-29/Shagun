package com.oceanmtech.shagun.DashboardModule.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oceanmtech.shagun.DashboardModule.Models.AddAddressModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityAddAddressBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {

    AddAddressActivity mContext = AddAddressActivity.this;
    ActivityAddAddressBinding mBinding;
    int RequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_add_address);
        mBinding.toolbar.tvTitle.setText("Add Address");
        onClickListener();

        RequestCode = getIntent().getIntExtra("Request_Code", 0);
    }

    private void saveAddressOfUser() {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).saveAddress
                    (param()).enqueue(new Callback<AddAddressModel>() {
                @Override
                public void onResponse(Call<AddAddressModel> call, Response<AddAddressModel> response) {
                    Log.e("Response :", response.message() + "");
                    ProgressHubKt.dismissLoader();
                    if (response.code() == 200) {
                        Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                        if (RequestCode == 2) {
                            Intent selectAddressIntent = new Intent();
                            setResult(2, selectAddressIntent);
                            finish();//finishing activity
                        }
                        if (RequestCode == 3) {
                            Intent manageAddressIntent = new Intent();
                            setResult(3, manageAddressIntent);
                            finish();//finishing activity
                        }

                    } else {
                        Toast.makeText(mContext, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddAddressModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public JsonObject param() {
        JsonObject paramObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_id", PreferenceHelper.getString(Constants.ID, ""));
            jsonObj_.put("address", mBinding.etAddress.getText().toString().trim());
            jsonObj_.put("country", mBinding.etCountry.getText().toString().trim());
            jsonObj_.put("city", mBinding.etCity.getText().toString().trim());
            jsonObj_.put("postal_code", mBinding.etPostcode.getText().toString().trim());
            jsonObj_.put("phone", mBinding.etMobile.getText().toString().trim());
            JsonParser jsonParser = new JsonParser();
            paramObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramObject;
    }

    private void onClickListener() {
        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.etAddress.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter address", Toast.LENGTH_SHORT).show();
                else if (mBinding.etMobile.getText().toString().trim().equalsIgnoreCase("") ||
                        (mBinding.etMobile.getText().toString().trim().length() < 10))
                    Toast.makeText(mContext, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                else if (mBinding.etCity.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter city", Toast.LENGTH_SHORT).show();
                else if (mBinding.etState.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter state", Toast.LENGTH_SHORT).show();
                else if (mBinding.etPostcode.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter post code", Toast.LENGTH_SHORT).show();
                else if (mBinding.etCountry.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter country", Toast.LENGTH_SHORT).show();
                else {
                    saveAddressOfUser();

//                    Intent manageAddress = new Intent(mContext, ManageAddressActivity.class);
//                    startActivity(manageAddress);
//                    Intent intent3=new Intent();
//                    intent3.putExtra("MESSAGE3","callGetAddressList");
//                    setResult(3,intent3);
//                    finish();//finishing activity
                }
            }
        });
        mBinding.toolbar.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}