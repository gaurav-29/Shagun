package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oceanmtech.shagun.DashboardModule.Models.GetWishlistModel;
import com.oceanmtech.shagun.DashboardModule.Utils.RecyclerViewOnClickInterface;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowWishlistBinding;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowWishlistBinding mBinding;
    ArrayList<GetWishlistModel.Data> wishList;
    Context mContext;
    private RecyclerViewOnClickInterface recyclerViewOnClickInterface;

    public WishlistAdapter(ArrayList<GetWishlistModel.Data> wishList, Context mContext, RecyclerViewOnClickInterface recyclerViewOnClickInterface) {
        this.wishList = wishList;
        this.mContext = mContext;
        this.recyclerViewOnClickInterface = recyclerViewOnClickInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_wishlist, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GetWishlistModel.Data currentWishlist = wishList.get(position);
        mBinding.tvProductName.setText(currentWishlist.product.name);
        mBinding.tvPrice.setText(String.valueOf(currentWishlist.product.base_price));
        String path = "https://www.nihareeka.com/public/" + currentWishlist.product.thumbnail_image;
        Log.d("PATH", path);
        Glide.with(mContext).load(path)
                .placeholder(mContext.getDrawable(R.drawable.app_icon5))
                .into(mBinding.ivProduct);
        mBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewOnClickInterface.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        RowWishlistBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowWishlistBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;


        }
    }
}
