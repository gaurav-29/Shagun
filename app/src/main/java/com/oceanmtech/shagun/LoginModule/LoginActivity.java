package com.oceanmtech.shagun.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.oceanmtech.shagun.DashboardModule.Activity.MainActivity;
import com.oceanmtech.shagun.DashboardModule.Models.LoginModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Note : email- test@gmail.com & test1@gmail.com and password- 123456 are working as login input parameters. No other parameters are working.

public class LoginActivity extends AppCompatActivity {

    LoginActivity mContext = LoginActivity.this;
    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  // To hide the status bar.

        onClickListeners();
    }

    private void onClickListeners() {

        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Patterns.EMAIL_ADDRESS.matcher(mBinding.etEmail.getText().toString().trim()).matches() ||
                        mBinding.etEmail.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter valid email", Toast.LENGTH_SHORT).show();
                else if (mBinding.etPassword.getText().toString().trim().equalsIgnoreCase("") ||
                        (mBinding.etPassword.getText().toString().trim().length() < 6))
                    Toast.makeText(mContext, "The password must be at least 6 characters/numbers.", Toast.LENGTH_SHORT).show();
                else
                    Login();
            }
        });

        mBinding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(mContext, RegisterActivity.class);
                startActivity(signup);
            }
        });
    }

    private void Login() {

        ProgressHubKt.showLoader(mContext);

        if (Internet.isInternetConnected(mContext)) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", mBinding.etEmail.getText().toString());
            jsonObject.addProperty("password", mBinding.etPassword.getText().toString());
            jsonObject.addProperty("remember_me", true);

            PreferenceHelper.putString(Constants.PASSWORD, mBinding.etPassword.getText().toString());
            PreferenceHelper.putString(Constants.REMEMBER_ME2, "false");

            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).Login(jsonObject)
                    .enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            if (response.code() == 200) {

                                PreferenceHelper.clearPreference();
                                PreferenceHelper.putBoolean(Constants.IS_LOGIN, true);
                                PreferenceHelper.putString(Constants.ACCESS_TOKEN, response.body().access_token);
                                PreferenceHelper.putString(Constants.TOKEN_TYPE, response.body().token_type);
                                PreferenceHelper.putString(Constants.EXPIRES_AT, response.body().expires_at);
                                PreferenceHelper.putString(Constants.ID, String.valueOf(response.body().user.id));
                                PreferenceHelper.putString(Constants.TYPE, response.body().user.type);
                                PreferenceHelper.putString(Constants.NAME, response.body().user.name);
                                PreferenceHelper.putString(Constants.EMAIL, response.body().user.email);

                                Toast.makeText(mContext, "Login successful.", Toast.LENGTH_SHORT).show();
                                Intent main = new Intent(mContext, MainActivity.class);
                                startActivity(main);
                                Log.d("TOKEN", response.body().access_token);
                            } else {
                                Toast.makeText(mContext, "You are not registered. Please click on Sign Up to continue.", Toast.LENGTH_LONG).show();
                            }
                            ProgressHubKt.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<LoginModel> call, Throwable t) {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                            Log.d("ERROR", t.getMessage());
                            ProgressHubKt.dismissLoader();
                        }
                    });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    //    private void GenerateOTP() {
//
//        mBinding.progressBar.getIndeterminateDrawable();
//        mBinding.progressBar.setVisibility(View.VISIBLE);
//
//        if(Internet.isInternetConnected(mContext)) {
//
//            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).Otp(paramOtp())
//                    .enqueue(new Callback<RegisterModel>() {
//                        @Override
//                        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
//                            if (response.code() == 200) {
//                                if (response.body().result == true) {
//                                    Toast.makeText(mContext, "Your OTP is : " + response.body().otp, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(mContext, OTPActivity.class);
//                                    intent.putExtra("MobileNumber", mBinding.etMobile.getText().toString());
//                                    intent.putExtra("OTP", response.body().otp);
//                                    startActivity(intent);
//                                    Log.d("RESPONSE", response.body().message);
//                                } else {
//                                    Toast.makeText(mContext, response.body().message, Toast.LENGTH_LONG).show();
//                                }
//                            }else{
//                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_LONG).show();
//                            }
//                            ProgressHubKt.dismissLoader();
//                        }
//
//                        @Override
//                        public void onFailure(Call<RegisterModel> call, Throwable t) {
//                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
//                            Log.d("ERROR", t.getMessage());
//                            ProgressHubKt.dismissLoader();
//                        }
//                    });
//        }
//        else {
//            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
//        }
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}