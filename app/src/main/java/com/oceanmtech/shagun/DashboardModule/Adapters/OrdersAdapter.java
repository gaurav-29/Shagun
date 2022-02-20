package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oceanmtech.shagun.DashboardModule.Activity.OrderDetailActivity;
import com.oceanmtech.shagun.DashboardModule.Activity.TrackOrderActivity;
import com.oceanmtech.shagun.DashboardModule.Models.OrdersModel;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowOrdersBinding;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowOrdersBinding mBinding;
    ArrayList<OrdersModel> orderList;
    Context mContext;

    public OrdersAdapter(ArrayList<OrdersModel> orderList, Context mContext) {
        this.orderList = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_orders, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // MyOrdersModel currentOrder = ordersList.get(position);
        mBinding.tvViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, OrderDetailActivity.class);
                mContext.startActivity(i);
            }
        });
        mBinding.tvTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TrackOrderActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        RowOrdersBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowOrdersBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
