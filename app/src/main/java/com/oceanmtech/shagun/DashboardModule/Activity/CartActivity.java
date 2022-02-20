package com.oceanmtech.shagun.DashboardModule.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oceanmtech.shagun.DashboardModule.Adapters.CartAdapter;
import com.oceanmtech.shagun.DashboardModule.Models.AddToCartModel;
import com.oceanmtech.shagun.DashboardModule.Models.CartModel;
import com.oceanmtech.shagun.DashboardModule.Models.ChangeQtyModel;
import com.oceanmtech.shagun.DashboardModule.Utils.CartAdapterOnClickInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityCartBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartAdapterOnClickInterface {

    CartActivity mContext = CartActivity.this;
    ActivityCartBinding mBinding;
    ArrayList<CartModel.Data> cartList = new ArrayList<>();
    CartAdapter mAdapter;
    String variantPrize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_cart);

        mBinding.toolbar.tvTitle.setText("Cart");

        getCartList();
        onClickListeners();
    }

    private void getCartList() {
        ProgressHubKt.showLoader(mContext);
        String CartUrl = "https://shagun-ent.eshopamb.com/api/v1/carts/" + PreferenceHelper.getString(Constants.ID, "");

        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    getCartList(CartUrl).enqueue(new Callback<CartModel>() {
                @Override
                public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                    Log.e("Response :", response.message() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            Log.e("response", response.body() + "");
                            cartList = response.body().data;
                            totalAmountOfAllItemsInCart(response.body().data);
                            if (cartList.size() > 0) {
                                mBinding.tvNoDataFound.setVisibility(View.GONE);
                                mBinding.recCart.setVisibility(View.VISIBLE);
                                mBinding.rlPlaceOrder.setVisibility(View.VISIBLE);
                            } else {
                                mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                                mBinding.recCart.setVisibility(View.GONE);
                                mBinding.rlPlaceOrder.setVisibility(View.GONE);
                            }
                            setDataInRecyclerView();
                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                            mBinding.recCart.setVisibility(View.GONE);
                            mBinding.rlPlaceOrder.setVisibility(View.GONE);
                        }
                    } else {
                        mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                        mBinding.recCart.setVisibility(View.GONE);
                        mBinding.rlPlaceOrder.setVisibility(View.GONE);
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<CartModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void totalAmountOfAllItemsInCart(ArrayList<CartModel.Data> data) {
        int totalAmount = 0;
        for (int i = 0; i < data.size(); i++) {
            totalAmount = totalAmount + (data.get(i).price * data.get(i).quantity);
        }
        mBinding.tvTotalAmount.setText(String.valueOf(totalAmount));
    }

    private void onClickListeners() {
        mBinding.toolbar.ivWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("Wishlist", "Test");
                startActivity(intent);
            }
        });
        mBinding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.llPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.size() > 0) {
                    Intent i = new Intent(mContext, SelectAddressActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    boolean isChanged = false;

    private void setDataInRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mBinding.recCart.setLayoutManager(layoutManager);
        mAdapter = new CartAdapter(cartList, mContext, this);
        mBinding.recCart.setAdapter(mAdapter);
    }

    private void changeQuantity(int cart_id, int total_qty, int position) {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).changeQty(param(cart_id, total_qty))
                    .enqueue(new Callback<ChangeQtyModel>() {
                        @Override
                        public void onResponse(Call<ChangeQtyModel> call, Response<ChangeQtyModel> response) {
                            if (response.code() == 200) {
                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                                if (response.body().message.equalsIgnoreCase("Cart updated")) {
                                    mAdapter.notifyItemChanged(position);
                                    mAdapter.notifyItemRangeChanged(position, cartList.size());
                                    getCartList();
                                }
                            }
                            ProgressHubKt.dismissLoader();

                        }

                        @Override
                        public void onFailure(Call<ChangeQtyModel> call, Throwable t) {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                            Log.d("ERROR", t.getMessage());
                            ProgressHubKt.dismissLoader();
                        }
                    });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public JsonObject param(int cart_id, int total_qty) {
        JsonObject paramObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("id", String.valueOf(cart_id));
            jsonObj_.put("quantity", String.valueOf(total_qty));
            JsonParser jsonParser = new JsonParser();
            paramObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramObject;
    }

    @Override
    public void onBackPressed() { // This code is for : When we plus or minus quantity of item in CartList and come back to
        if (isChanged) {          // ProductDetailActivity, the Quantity will be updated in this activity. Result will be passed
            Intent intent = new Intent();  // through this code and received in ProductDetailActivity in onAvtivityResult().
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else
            super.onBackPressed();
    }

    @Override
    public void onRemoveClick(int position) {
        String CartUrl = "https://shagun-ent.eshopamb.com/api/v1/carts/" + cartList.get(position).id;

        if (Internet.isInternetConnected(mContext)) {
            ProgressHubKt.showLoader(mContext);
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    removeFromCartlist(CartUrl).enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    if (response.code() == 200) {

                        // OnClickAddToCart.OnClickAddToCart(cartList.get(position).id, cartList.get(position).quantity - 1, position, "remove");
                        cartList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemRangeRemoved(position, cartList.size());
                        if (cartList.size() > 0) {
                            mBinding.tvNoDataFound.setVisibility(View.GONE);
                            mBinding.recCart.setVisibility(View.VISIBLE);
                            mBinding.rlPlaceOrder.setVisibility(View.VISIBLE);
                        } else {
                            mBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                            mBinding.recCart.setVisibility(View.GONE);
                            mBinding.rlPlaceOrder.setVisibility(View.GONE);
                        }
                        //getCartList();
                        Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPlusClick(int position) {
        isChanged = true;
        changeQuantity(cartList.get(position).id, cartList.get(position).quantity + 1, position);
    }

    @Override
    public void onMinusClick(int position) {
        isChanged = true;
        if (cartList.get(position).quantity - 1 == 0) {

        } else
            changeQuantity(cartList.get(position).id, cartList.get(position).quantity - 1, position);
    }
}