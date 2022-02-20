package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oceanmtech.shagun.DashboardModule.Models.OrderDetailModel;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowOrderdetailBinding;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowOrderdetailBinding mBinding;
    ArrayList<OrderDetailModel.Data> orderDetailList;
    Context mContext;

    public OrderDetailAdapter(ArrayList<OrderDetailModel.Data> orderDetailList, Context mContext) {
        this.orderDetailList = orderDetailList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_orderdetail, parent, false);
        return new OrderDetailAdapter.ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder mViewHolder = (ItemViewHolder) holder;
        //OrderDetailModel.Data currentOrderDetail = orderDetailList.get(position);
        Glide.with(mContext).load("https://www.nihareeka.com/public/" +orderDetailList.get(position).thumbnail_image)
                .placeholder(mContext.getDrawable(R.drawable.app_icon))
                .into(mViewHolder.mBinding.ivProduct);
        mViewHolder.mBinding.tvProductName.setText(orderDetailList.get(position).product);
        mViewHolder.mBinding.tvRs.setText("Rs. " + orderDetailList.get(position).price);
        mViewHolder.mBinding.tvQuantity.setText("Qty : " + orderDetailList.get(position).quantity);
        mViewHolder.mBinding.tvTotal.setText("Total Rs. " + orderDetailList.get(position).price * orderDetailList.get(position).quantity);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        RowOrderdetailBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowOrderdetailBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
