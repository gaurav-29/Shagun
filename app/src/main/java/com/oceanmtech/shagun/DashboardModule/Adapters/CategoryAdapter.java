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
import com.oceanmtech.shagun.DashboardModule.Activity.ProductListActivity;
import com.oceanmtech.shagun.DashboardModule.Models.AllCategoryModel;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.RowCategoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowCategoryBinding mBinding;
    ArrayList<AllCategoryModel.Data> categoryList;
    Context mContext;

    public CategoryAdapter(ArrayList<AllCategoryModel.Data> categoryList, Context mContext) {
        this.categoryList = categoryList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_category, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        AllCategoryModel.Data currentCategory = categoryList.get(position);
        mBinding.tvCategoryName.setText(currentCategory.name);
        String path = "https://www.nihareeka.com/public/" + currentCategory.icon;
        Log.d("PATH", path);
        Glide.with(mContext).load(path)
                .placeholder(mContext.getDrawable(R.drawable.app_icon5))
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
                .into(mBinding.ivCategory);
        mBinding.llCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductListActivity.class);
                i.putExtra("LIST_URL", categoryList.get(position).links.products);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        RowCategoryBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowCategoryBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
