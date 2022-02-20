package com.oceanmtech.shagun.DashboardModule.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oceanmtech.shagun.DashboardModule.Adapters.ProductListAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.ProductListModel;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PaginationScrollListener;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityProductListBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    ProductListActivity mContext = ProductListActivity.this;
    ActivityProductListBinding mBinding;
    String firstPageUrl;
    ProductListModel mModel = new ProductListModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_product_list);

        mBinding.toolbar.tvTitle.setText("Product List");
        mBinding.llSearch.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            firstPageUrl = extras.getString("LIST_URL");
        }
        Log.d("listUrl", firstPageUrl);
        onClickListeners();
        pagination();
    }

    ProductListAdapter mAdapter;
    int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    ArrayList<ProductListModel.Data> productList = new ArrayList<>();

    private void pagination() {
        productList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mBinding.recProductList.setLayoutManager(layoutManager);
        mAdapter = new ProductListAdapter(productList, mContext);
        mBinding.recProductList.setAdapter(mAdapter);

        mBinding.recProductList.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (!isLastPage) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mModel.links.next != null) {
                                Log.d("NEXT_PAGE", mModel.links.next);
                                getProductList(mModel.links.next);
                            }
                        }
                    }, 500);
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        getProductList(firstPageUrl);
    }

    private void getProductList(String linkUrl) {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    productList(linkUrl).enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            mModel = response.body();
                            if (linkUrl.equalsIgnoreCase(mModel.links.last)) {
                                isLastPage = true;
                            }
                            resultAction(response.body());
                        } else {
                            isLastPage = true;
                        }
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void resultAction(ProductListModel productListModel) {
        isLoading = false;
        if (productListModel != null) {
            mAdapter.addItems(productListModel.data);
            if (isLastPage) {
            } else {
                page = page + 1;
            }
        }
    }

    private void onClickListeners() {
        mBinding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}