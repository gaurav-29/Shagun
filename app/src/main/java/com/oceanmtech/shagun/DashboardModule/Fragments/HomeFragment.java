package com.oceanmtech.shagun.DashboardModule.Fragments;

import android.content.Intent;
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

import com.oceanmtech.shagun.DashboardModule.Activity.CartActivity;
import com.oceanmtech.shagun.DashboardModule.Activity.SearchProductListActivity;
import com.oceanmtech.shagun.DashboardModule.Adapters.CategoryAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.AllCategoryModel;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.FragmentHomeBinding;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    FragmentHomeBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        onClickListeners();
        CategoryList();
        return mBinding.getRoot();
    }

    private void CategoryList() {
        ProgressHubKt.showLoader(getActivity());
        if (Internet.isInternetConnected(getActivity())) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    categoryList("https://shagun-ent.eshopamb.com/api/v1/categories").enqueue(new Callback<AllCategoryModel>() {
                @Override
                public void onResponse(Call<AllCategoryModel> call, Response<AllCategoryModel> response) {
                    if (response.code() == 200) {
                        Log.d("WISHLIST", response.body().toString());
                        setCategory(response.body().data);
                        loadSlider(response.body().data);
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<AllCategoryModel> call, Throwable t) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setCategory(ArrayList<AllCategoryModel.Data> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        mBinding.recCategories.setLayoutManager(layoutManager);
        CategoryAdapter mAdapter = new CategoryAdapter(data, getActivity());
        mBinding.recCategories.setAdapter(mAdapter);
    }

    private void onClickListeners() {
        mBinding.toolbar.ivNull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CartActivity.class);
                startActivity(i);
            }
        });
        mBinding.llSearchTag.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchProductListActivity.class);
            startActivity(intent);
        });
    }

    private void loadSlider(ArrayList<AllCategoryModel.Data> sliderImages) {
        mBinding.slider1.registerLifecycle(getViewLifecycleOwner());
        List<CarouselItem> list = new ArrayList<>();
        for (int i = 0; i < sliderImages.size(); i++) {
            list.add(new CarouselItem("https://www.nihareeka.com/public/" + sliderImages.get(i).banner));
        }
        Log.d("SLIDER", list.toString());
        mBinding.slider1.setData(list);
    }
}