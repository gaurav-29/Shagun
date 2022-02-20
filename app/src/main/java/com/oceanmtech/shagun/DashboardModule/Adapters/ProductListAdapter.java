package com.oceanmtech.shagun.DashboardModule.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oceanmtech.shagun.DashboardModule.Activity.ProductDetailActivity;
import com.oceanmtech.shagun.DashboardModule.Models.ProductListModel;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowProductListBinding;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowProductListBinding mBinding;
    ArrayList<ProductListModel.Data> productList;
    Context mContext;

    public ProductListAdapter(ArrayList<ProductListModel.Data> productList, Context mContext) {
        setHasStableIds(true);
        this.productList = productList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_product_list, parent, false);
        return new ProductListAdapter.ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ProductListModel.Data currentProduct = productList.get(position);
        mBinding.tvProductName.setText(currentProduct.name);
        mBinding.tvPrice.setText(String.valueOf(currentProduct.base_price));
        String path = "https://www.nihareeka.com/public/" + currentProduct.thumbnail_image;
        Log.d("PATH2", path);
        Glide.with(mContext).load(path)
                .placeholder(mContext.getDrawable(R.drawable.app_icon5))
                .into(mBinding.ivProduct);
        mBinding.cvProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductDetailActivity.class);
                i.putExtra("Product_detail", String.valueOf(productList.get(position).links.details));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void addItems(ArrayList<ProductListModel.Data> items) {
        int size = productList.size();
        productList.addAll(items);
        notifyItemRangeChanged(size, items.size());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        RowProductListBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowProductListBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
