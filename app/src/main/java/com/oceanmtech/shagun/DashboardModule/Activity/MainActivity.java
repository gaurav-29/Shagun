package com.oceanmtech.shagun.DashboardModule.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.oceanmtech.shagun.DashboardModule.Fragments.AccountFragment;
import com.oceanmtech.shagun.DashboardModule.Fragments.HomeFragment;
import com.oceanmtech.shagun.DashboardModule.Fragments.OrdersFragment;
import com.oceanmtech.shagun.DashboardModule.Fragments.WishlistFragment;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    MainActivity mContext = MainActivity.this;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedWishlist();
        } else {
            selectedHome();
        }

        onClickListeners();
    }

    private void onClickListeners() {
        mBinding.llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedHome();
            }
        });
        mBinding.llOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOrders();
            }
        });
        mBinding.llWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedWishlist();
            }
        });
        mBinding.llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAccount();
            }
        });
    }

    private void selectedHome() {
        mBinding.ivHome.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBinding.tvHome.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBinding.ivOrders.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvOrders.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivWishlist.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvWishlist.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivAccount.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvAccount.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.llContainer, fragment).commit();
    }

    private void selectedOrders() {
        mBinding.ivHome.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvHome.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivOrders.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBinding.tvOrders.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBinding.ivWishlist.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvWishlist.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivAccount.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvAccount.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new OrdersFragment();
        fragmentManager.beginTransaction().replace(R.id.llContainer, fragment).commit();
    }

    private void selectedWishlist() {
        mBinding.ivHome.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvHome.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivOrders.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvOrders.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivWishlist.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBinding.tvWishlist.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBinding.ivAccount.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvAccount.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new WishlistFragment();
        fragmentManager.beginTransaction().replace(R.id.llContainer, fragment).commit();
    }

    private void selectedAccount() {
        mBinding.ivHome.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvHome.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivOrders.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvOrders.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivWishlist.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.tvWishlist.setTextColor(ContextCompat.getColor(mContext, R.color.colorSecondary));
        mBinding.ivAccount.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBinding.tvAccount.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new AccountFragment();
        fragmentManager.beginTransaction().replace(R.id.llContainer, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}