package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oceanmtech.shagun.DashboardModule.Models.CartModel;
import com.oceanmtech.shagun.DashboardModule.Utils.CartAdapterOnClickInterface;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowCartBinding mBinding;
    ArrayList<CartModel.Data> cartList;
    Context mContext;
    CartAdapterOnClickInterface cartInterface;

    public CartAdapter(ArrayList<CartModel.Data> cartList, Context mContext, CartAdapterOnClickInterface cartInterface) {
        this.cartList = cartList;
        this.mContext = mContext;
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_cart, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final ItemViewHolder mViewHolder = (ItemViewHolder) holder;
        mViewHolder.mBinding.tvProductName.setText(cartList.get(position).product.name);
        mViewHolder.mBinding.tvPrice.setText(String.valueOf(cartList.get(position).price));
        mViewHolder.mBinding.tvQuantity1.setText(String.valueOf(cartList.get(position).quantity));
        mViewHolder.mBinding.tvQuantity.setText(String.valueOf(cartList.get(position).quantity));
        mViewHolder.mBinding.tvTotalPrice.setText(String.valueOf(cartList.get(position).price * cartList.get(position).quantity));
        Glide.with(mContext).load("https://www.nihareeka.com/public/" + cartList.get(position).product.image)
                .placeholder(mContext.getDrawable(R.drawable.app_icon5))
                .into(mViewHolder.mBinding.ivProduct);
        mViewHolder.mBinding.llRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartInterface.onRemoveClick(position);
            }
        });
        mViewHolder.mBinding.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartInterface.onPlusClick(position);
            }
        });
        mViewHolder.mBinding.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartInterface.onMinusClick(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private RowCartBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowCartBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
