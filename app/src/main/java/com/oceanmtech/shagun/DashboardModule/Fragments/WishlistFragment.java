package com.oceanmtech.shagun.DashboardModule.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oceanmtech.shagun.DashboardModule.Adapters.WishlistAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.AddToWishlistModel;
import com.oceanmtech.shagun.DashboardModule.Models.GetWishlistModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.DashboardModule.Utils.RecyclerViewOnClickInterface;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.FragmentWishlistBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment implements RecyclerViewOnClickInterface {

    FragmentWishlistBinding mBinding;
    ArrayList<GetWishlistModel.Data> wishList = new ArrayList<>();
    WishlistAdapter mAdapter;

    String productNames[] = {"Product1", "Product2", "Product3", "Product4", "Product5", "Product6"};
    int imageIds[] = {R.drawable.img_shirts, R.drawable.img_shirts, R.drawable.img_shirts, R.drawable.img_shirts,
            R.drawable.img_shirts, R.drawable.img_shirts};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false);

        mBinding.toolbar.tvTitle.setText("Wishlist");
        mBinding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        getDataFromWishlist();
        //setRecyclerViewData();
        return mBinding.getRoot();
    }

    private void getDataFromWishlist() {
        ProgressHubKt.showLoader(getActivity());

        String WishlistUrl = "https://shagun-ent.eshopamb.com/api/v1/wishlists/" + PreferenceHelper.getString(Constants.ID, "");

        if (Internet.isInternetConnected(getActivity())) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    getWishlist(WishlistUrl).enqueue(new Callback<GetWishlistModel>() {
                @Override
                public void onResponse(Call<GetWishlistModel> call, Response<GetWishlistModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        wishList = response.body().data;
                        if (response.body().data.size() > 0) {
                            Log.e("response", response.body() + "");
                            setRecyclerViewData(response.body().data);
                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                        }
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<GetWishlistModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setRecyclerViewData(ArrayList<GetWishlistModel.Data> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        mBinding.recProducts.setLayoutManager(layoutManager);
        mAdapter = new WishlistAdapter(data, getActivity(), this);
        mBinding.recProducts.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {

        String WishlistUrl = "https://shagun-ent.eshopamb.com/api/v1/wishlists/" + wishList.get(position).id;

        if (Internet.isInternetConnected(getActivity())) {
            ProgressHubKt.showLoader(getActivity());
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    removeFromWishlist(WishlistUrl).enqueue(new Callback<AddToWishlistModel>() {
                @Override
                public void onResponse(Call<AddToWishlistModel> call, Response<AddToWishlistModel> response) {
                    if (response.code() == 200) {

                        wishList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemRangeRemoved(position, wishList.size());
                        Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<AddToWishlistModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}
