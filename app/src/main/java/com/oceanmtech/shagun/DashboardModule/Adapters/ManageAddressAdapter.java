package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oceanmtech.shagun.DashboardModule.Activity.ManageAddressActivity;
import com.oceanmtech.shagun.DashboardModule.Models.ManageAddressModel;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowAddressBinding;

import java.util.ArrayList;

public class ManageAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowAddressBinding mBinding;
    ArrayList<ManageAddressModel.Data> addressList;
    Context mContext;
    OnClickDeleteAddress onClickDeleteAddress;

    public ManageAddressAdapter(ArrayList<ManageAddressModel.Data> addressList, ManageAddressActivity mContext,
                                OnClickDeleteAddress onClickDeleteAddress) {
        this.addressList = addressList;
        this.mContext = mContext;
        this.onClickDeleteAddress = onClickDeleteAddress;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_address, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder mViewHolder = (ItemViewHolder) holder;

        mViewHolder.mBinding.tvAddress.setText(addressList.get(position).address);
        mViewHolder.mBinding.tvCity.setText(addressList.get(position).city);
        mViewHolder.mBinding.tvPostalCode.setText(addressList.get(position).postal_code);
        mViewHolder.mBinding.tvCountry.setText(addressList.get(position).country);
        mViewHolder.mBinding.tvPhone.setText(addressList.get(position).phone);

        mViewHolder.mBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeleteAddress.onClickDeleteAddress(addressList.get(position).id, position);
            }
        });
    }
    public interface OnClickDeleteAddress {
        void onClickDeleteAddress(int id, int position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private RowAddressBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowAddressBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
