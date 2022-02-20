package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oceanmtech.shagun.DashboardModule.Activity.SelectAddressActivity;
import com.oceanmtech.shagun.DashboardModule.Models.ManageAddressModel;
import com.oceanmtech.shagun.DashboardModule.Utils.SelectAddressInterface;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowSelectAddressBinding;

import java.util.ArrayList;

public class SelectAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowSelectAddressBinding mBinding;
    ArrayList<ManageAddressModel.Data> addressList;
    Context mContext;
    private SelectAddressInterface selectAddressInterface;
    private int selectedItem;
    String Address, City, Postal_Code, Country, Phone;

    public SelectAddressAdapter(ArrayList<ManageAddressModel.Data> addressList, SelectAddressActivity mContext,
                                SelectAddressInterface selectAddressInterface) {
        this.addressList = addressList;
        this.mContext = mContext;
        this.selectAddressInterface = selectAddressInterface;
        selectedItem = -1;  // if we want the 1st item background as sky blue when we open this activity,
        // set selectedItem = 0;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_select_address, parent, false);
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

        // Selected item's background changed as per below link.(1st method)
        // https://androidnoon.com/highlight-selected-item-in-recyclerview-on-click-android-studio/
        mViewHolder.mBinding.cvAddress.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
        if (selectedItem == position) {
            mViewHolder.mBinding.cvAddress.setCardBackgroundColor(mContext.getResources().getColor(R.color.sky_blue));
        }
        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousItem = selectedItem;
                selectedItem = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);

                Address = mBinding.tvAddress.getText().toString();
                City = mBinding.tvCity.getText().toString();
                Postal_Code = mBinding.tvPostalCode.getText().toString();
                Country = mBinding.tvCountry.getText().toString();
                Phone = mBinding.tvPhone.getText().toString();
                selectAddressInterface.selectedAddress(Address, City, Postal_Code, Country, Phone,addressList.get(position) );

            }
        });
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

        private RowSelectAddressBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowSelectAddressBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;


        }
    }
}
