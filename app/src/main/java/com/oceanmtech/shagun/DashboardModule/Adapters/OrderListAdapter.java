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
import com.oceanmtech.shagun.DashboardModule.Models.OrderListModel;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowOrdersBinding;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowOrdersBinding mBinding;
    ArrayList<OrderListModel.Data> orderList;
    Context mContext;
    String orderDetailUrl;

    public OrderListAdapter(ArrayList<OrderListModel.Data> orderList, Context mContext) {
        this.orderList = orderList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_orders, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder mViewHolder = (ItemViewHolder) holder;
        //OrderListModel.Data currentOrder = orderList.get(position);
        mViewHolder.mBinding.tvId.setText(orderList.get(position).code);
        mViewHolder.mBinding.tvAmt.setText(String.valueOf(orderList.get(position).grand_total));
        mViewHolder.mBinding.tvDate.setText(orderList.get(position).date);
        mViewHolder.mBinding.tvStatus.setText(orderList.get(position).payment_status);

        mViewHolder.mBinding.tvViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDetailUrl = orderList.get(position).links.details;
                Intent i = new Intent(mContext, OrderDetailActivity.class);
                i.putExtra("OrderDetailUrl", orderDetailUrl);
                i.putExtra("Name", orderList.get(position).shipping_address.name);
                i.putExtra("Email", orderList.get(position).shipping_address.email);
                i.putExtra("Address", orderList.get(position).shipping_address.address);
                i.putExtra("Country", orderList.get(position).shipping_address.country);
                i.putExtra("City", orderList.get(position).shipping_address.city);
                i.putExtra("PostalCode", orderList.get(position).shipping_address.postal_code);
                i.putExtra("Phone", orderList.get(position).shipping_address.phone);
                mContext.startActivity(i);
            }
        });
        mViewHolder.mBinding.tvTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TrackOrderActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        RowOrdersBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowOrdersBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
