package com.oceanmtech.shagun.DashboardModule.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.oceanmtech.shagun.DashboardModule.Adapters.OrderListAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.OrderListModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.FragmentOrdersBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {

    FragmentOrdersBinding mBinding;
    OrderListAdapter mAdapter;
    ArrayList<OrderListModel.Data> orderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        mBinding.toolbar.tvTitle.setText("My Orders");

        getOrderList();
        return mBinding.getRoot();
    }

    private void getOrderList() {

        ProgressHubKt.showLoader(getActivity());

        String OrderListUrl = "https://shagun-ent.eshopamb.com/api/v1/purchase-history/" + PreferenceHelper.getString(Constants.ID, "");

        if (Internet.isInternetConnected(getActivity())) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    getOrderListData(OrderListUrl).enqueue(new Callback<OrderListModel>() {
                @Override
                public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            Log.e("response", response.body() + "");
                            orderList = response.body().data;
                            setDataInRecyclerView(orderList);
//                            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
//                            intent.putExtra("mylist", orderList);
//                            startActivity(intent);
                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                        }
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<OrderListModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setDataInRecyclerView(ArrayList<OrderListModel.Data> orderList) {
        mAdapter = new OrderListAdapter(orderList, getActivity());
        mBinding.recOrders.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mBinding.recOrders.setItemAnimator(new DefaultItemAnimator());
        mBinding.recOrders.setAdapter(mAdapter);
    }
}
