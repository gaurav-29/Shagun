package com.oceanmtech.shagun.LoginModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.oceanmtech.shagun.DashboardModule.Activity.MainActivity;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    SplashActivity mContext = SplashActivity.this;
    ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  // To hide the status bar.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (PreferenceHelper.getBoolean(Constants.IS_LOGIN,false)) {
                    Intent main = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);
                    finish();
                } else {
                    Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                }
            }
        }, 3000);
    }
}