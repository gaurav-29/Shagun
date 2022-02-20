package com.oceanmtech.shagun.DashboardModule.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.oceanmtech.shagun.DashboardModule.Activity.ContactUsActivity;
import com.oceanmtech.shagun.DashboardModule.Activity.ManageAddressActivity;
import com.oceanmtech.shagun.DashboardModule.Activity.ProfileActivity;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.LoginModule.LoginActivity;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    FragmentAccountBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        onClickListeners();
        return mBinding.getRoot();
    }

    public void WhatsappWaME(Context mContext, String mobileNum) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://wa.me/+91" + mobileNum));
        mContext.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.tvTitle.setText(PreferenceHelper.getString(Constants.NAME, ""));
        mBinding.tvEmail.setText(PreferenceHelper.getString(Constants.EMAIL, ""));
    }

    private void onClickListeners() {
        mBinding.llHelp.setOnClickListener(v -> {
            WhatsappWaME(getActivity(), "9099814643");
        });
        mBinding.llContactUs.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            startActivity(intent);
        });
        mBinding.llMyProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });
        mBinding.llManageAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent address = new Intent(getActivity(), ManageAddressActivity.class);
                startActivity(address);
            }
        });
        mBinding.llShare.setOnClickListener(v -> {
            shareApp();
        });
        mBinding.llRate.setOnClickListener(v -> {
            final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
//            try {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        });
        mBinding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mBinding.progressBar.getIndeterminateDrawable();
//                mBinding.progressBar.setVisibility(View.VISIBLE);
                PreferenceHelper.clearPreference();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finishAffinity();
              /*  if (Internet.isInternetConnected(mContext)) {

                    String token = PreferenceHelper.getString(Constants.ACCESS_TOKEN, "");
                    String email = PreferenceHelper.getString(Constants.EMAIL, "");
                    String password = PreferenceHelper.getString(Constants.PASSWORD, "");
                    boolean remember_me = Boolean.parseBoolean(PreferenceHelper.getString(Constants.REMEMBER_ME2, ""));
                    Log.d("TOKEN", token);

//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty("email", "test@gmail.com");
//                    jsonObject.addProperty("password", "123456");
//                    jsonObject.addProperty("remember_me", false);

                    GeneralAPIClient.getClient().create(GeneralAPIInterface.class).Logout("Bearer " + token, email, password, remember_me)
                            .enqueue(new Callback<LogoutModel>() {
                                @Override
                                public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                                        PreferenceHelper.putBoolean(Constants.IS_LOGIN, false);
                                        Intent i = new Intent(mContext, LoginActivity.class);
                                        startActivity(i);
                                        getActivity().finishAffinity();
                                        Log.d("RESPONSE", String.valueOf(response.body()));
                                    } else {
                                        Toast.makeText(mContext, response.body().message, Toast.LENGTH_LONG).show();
                                    }
                                    ProgressHubKt.dismissLoader();
                                }

                                @Override
                                public void onFailure(Call<LogoutModel> call, Throwable t) {
                                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                                    Log.d("ERROR", t.getMessage());
                                    ProgressHubKt.dismissLoader();
                                }
                            });
                } else {
                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    private void shareApp() {
        final String appPackageName = getActivity().getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check this " + getResources().getString(R.string.app_name) + " app at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}