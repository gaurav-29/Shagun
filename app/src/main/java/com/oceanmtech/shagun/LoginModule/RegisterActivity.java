package com.oceanmtech.shagun.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.oceanmtech.shagun.DashboardModule.Models.RegisterModel;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RegisterActivity mContext = RegisterActivity.this;
    ActivityRegisterBinding mBinding;

    // String[] city = {"Ahmedabad", "Surat", "Vadodara", "Rajkot", "Other"};
    // String[] state = {"Gujarat", "Rajasthan", "Maharashtra", "Punjab", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  // To hide the status bar.

        //mBinding.spCity.setOnItemSelectedListener(this);
        //setCitySpinner();
        //setStateSpinner();
        onClickListeners();
    }

    private void onClickListeners() {

        mBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.etName.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter name", Toast.LENGTH_SHORT).show();
                else if (!Patterns.EMAIL_ADDRESS.matcher(mBinding.etEmail.getText().toString().trim()).matches() ||
                        mBinding.etEmail.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter valid email", Toast.LENGTH_SHORT).show();
                else if (mBinding.etPassword.getText().toString().trim().equalsIgnoreCase("") ||
                        (mBinding.etPassword.getText().toString().trim().length() < 6))
                    Toast.makeText(mContext, "The password must be at least 6 characters/numbers.", Toast.LENGTH_SHORT).show();
                else if (mBinding.etConfirm.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                else if (!mBinding.etConfirm.getText().toString().trim()
                        .equalsIgnoreCase(mBinding.etPassword.getText().toString().trim()))
                    Toast.makeText(mContext, "Password not matched", Toast.LENGTH_SHORT).show();
                else
                    Register();
            }
        });
        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(mContext, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    private void Register() {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", mBinding.etName.getText().toString());
            jsonObject.addProperty("email", mBinding.etEmail.getText().toString());
            jsonObject.addProperty("password", mBinding.etPassword.getText().toString());
            jsonObject.addProperty("passowrd_confirmation", mBinding.etConfirm.getText().toString());

            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).Register(jsonObject)
                    .enqueue(new Callback<RegisterModel>() {
                        @Override
                        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                            if (response.code() == 201) {
                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                                Intent login = new Intent(mContext, LoginActivity.class);
                                startActivity(login);
                                Log.d("RESPONSE", response.body().message);
                            } else {
                                Toast.makeText(mContext, "The email has already been taken.", Toast.LENGTH_LONG).show();
                            }
                            ProgressHubKt.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<RegisterModel> call, Throwable t) {
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}