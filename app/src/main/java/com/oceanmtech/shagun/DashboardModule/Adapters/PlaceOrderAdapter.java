package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oceanmtech.shagun.DashboardModule.Activity.PlaceOrderActivity2;
import com.oceanmtech.shagun.DashboardModule.Models.CartModel;
import com.oceanmtech.shagun.DashboardModule.Models.PlaceOrderModel;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowOrderdetailBinding;
import com.oceanmtech.shagun.databinding.RowProductDetailBinding;

import java.util.ArrayList;

public class PlaceOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowProductDetailBinding mBinding;
    ArrayList<CartModel.Data> productDetailList;
    Context mContext;

    public PlaceOrderAdapter(ArrayList<CartModel.Data> productDetailList, PlaceOrderActivity2 mContext) {
        this.productDetailList = productDetailList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_product_detail, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder mViewHolder = (ItemViewHolder) holder;
        mViewHolder.mBinding.tvProductName.setText(productDetailList.get(position).product.name);
        mViewHolder.mBinding.tvPrice.setText(String.valueOf(productDetailList.get(position).price));
        mViewHolder.mBinding.tvQty.setText(String.valueOf(productDetailList.get(position).quantity));
        mViewHolder.mBinding.tvTotalPrice.setText(String.valueOf(productDetailList.get(position).price * productDetailList.get(position).quantity));
        Glide.with(mContext).load("https://www.nihareeka.com/public/" + productDetailList.get(position).product.image)
                .placeholder(mContext.getDrawable(R.drawable.app_icon5))
                .into(mViewHolder.mBinding.ivProduct);

    }

    @Override
    public int getItemCount() {
        return productDetailList.size();
    }
    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        RowProductDetailBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowProductDetailBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
