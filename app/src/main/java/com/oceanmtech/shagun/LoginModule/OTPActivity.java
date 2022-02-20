package com.oceanmtech.shagun.LoginModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.oceanmtech.shagun.DashboardModule.Activity.MainActivity;
import com.oceanmtech.shagun.DashboardModule.Models.LoginModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityOtpactivityBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    OTPActivity mContext = OTPActivity.this;
    ActivityOtpactivityBinding mBinding;

    String MobileNumber = "";
    int OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_otpactivity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileNumber = getIntent().getStringExtra("MobileNumber");
        OTP = getIntent().getIntExtra("OTP", 0);

        EditText[] edit = {mBinding.etOtp1, mBinding.etOtp2, mBinding.etOtp3,
                mBinding.etOtp4, mBinding.etOtp5, mBinding.etOtp6};

        mBinding.etOtp1.addTextChangedListener(new GenericTextWatcher(mBinding.etOtp1, edit));
        mBinding.etOtp2.addTextChangedListener(new GenericTextWatcher(mBinding.etOtp2, edit));
        mBinding.etOtp3.addTextChangedListener(new GenericTextWatcher(mBinding.etOtp3, edit));
        mBinding.etOtp4.addTextChangedListener(new GenericTextWatcher(mBinding.etOtp4, edit));
        mBinding.etOtp5.addTextChangedListener(new GenericTextWatcher(mBinding.etOtp5, edit));
        mBinding.etOtp6.addTextChangedListener(new GenericTextWatcher(mBinding.etOtp6, edit));

        onClickListeners();
    }

    private void onClickListeners() {
        mBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.etOtp1.getText().toString().equalsIgnoreCase("") ||
                        mBinding.etOtp2.getText().toString().equalsIgnoreCase("") ||
                        mBinding.etOtp3.getText().toString().equalsIgnoreCase("") ||
                        mBinding.etOtp4.getText().toString().equalsIgnoreCase("") ||
                        mBinding.etOtp5.getText().toString().equalsIgnoreCase("") ||
                        mBinding.etOtp6.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter OTP", Toast.LENGTH_LONG).show();
                } else if (!(mBinding.etOtp1.getText().toString().trim() + mBinding.etOtp2.getText().toString().trim() +
                        mBinding.etOtp3.getText().toString().trim() + mBinding.etOtp4.getText().toString().trim() +
                        mBinding.etOtp5.getText().toString().trim() + mBinding.etOtp6.getText().toString().trim()).equalsIgnoreCase(String.valueOf(OTP))) {
                    Toast.makeText(mContext, "Your OTP did not match", Toast.LENGTH_LONG).show();
                } else {
                    //Login();
                }
            }
        });
    }

//    private void Login() {
//
//        mBinding.progressBar.getIndeterminateDrawable();
//        mBinding.progressBar.setVisibility(View.VISIBLE);
//
//        if (Internet.isInternetConnected(mContext)) {
//
//            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).Login(passParameters())
//                    .enqueue(new Callback<LoginModel>() {
//                        @Override
//                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
//                            if (response.code() == 200) {
//                                if (response.body().result == true) {
//
//                                    PreferenceHelper.clearPreference();
//                                    PreferenceHelper.putBoolean(Constants.IS_LOGIN, true);
//                                    PreferenceHelper.putBoolean(Constants.RESULT, response.body().result);
//                                    PreferenceHelper.putString(Constants.MESSAGE, response.body().message);
//                                    PreferenceHelper.putString(Constants.ACCESS_TOKEN, response.body().access_token);
//                                    PreferenceHelper.putString(Constants.TOKEN_TYPE, response.body().token_type);
//                                    PreferenceHelper.putString(Constants.EXPIRES_AT, response.body().expires_at);
//                                    PreferenceHelper.putString(Constants.ID, String.valueOf(response.body().user.id));
//                                    PreferenceHelper.putString(Constants.TYPE, response.body().user.type);
//                                    PreferenceHelper.putString(Constants.NAME, response.body().user.name);
//                                    PreferenceHelper.putString(Constants.EMAIL, response.body().user.email);
//                                    PreferenceHelper.putString(Constants.AVATAR, response.body().user.avatar);
//                                    PreferenceHelper.putString(Constants.AVATAR_ORIGINAL, response.body().user.avatar_original);
//                                    PreferenceHelper.putString(Constants.PHONE, response.body().user.phone);
//
//                                    Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
//                                    Intent main = new Intent(mContext, MainActivity.class);
//                                    startActivity(main);
//                                    Log.d("RESPONSE", response.body().message);
//                                } else {
//                                    Toast.makeText(mContext, response.body().message, Toast.LENGTH_LONG).show();
//                                }
//                            } else {
//                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_LONG).show();
//                            }
//                            ProgressHubKt.dismissLoader();
//                        }
//
//                        @Override
//                        public void onFailure(Call<LoginModel> call, Throwable t) {
//                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
//                            Log.d("ERROR", t.getMessage());
//                            ProgressHubKt.dismissLoader();
//                        }
//                    });
//        } else {
//            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private Map<String, String> passParameters() {
//        Map<String, String> params = new HashMap<>();
//        params.put("mobile", MobileNumber);
//        params.put("otp", String.valueOf(OTP));
//        Log.d("param", params.toString());
//        return params;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}