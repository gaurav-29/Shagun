package com.oceanmtech.shagun.DashboardModule.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityTrackOrderBinding;

public class TrackOrderActivity extends AppCompatActivity {

    TrackOrderActivity mContext = TrackOrderActivity.this;
    ActivityTrackOrderBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_track_order);

        onClickListeners();
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