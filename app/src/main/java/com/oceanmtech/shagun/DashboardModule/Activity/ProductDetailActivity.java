package com.oceanmtech.shagun.DashboardModule.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oceanmtech.shagun.DashboardModule.Models.AddToCartModel;
import com.oceanmtech.shagun.DashboardModule.Models.AddToWishlistModel;
import com.oceanmtech.shagun.DashboardModule.Models.CartModel;
import com.oceanmtech.shagun.DashboardModule.Models.ChangeQtyModel;
import com.oceanmtech.shagun.DashboardModule.Models.NameModel;
import com.oceanmtech.shagun.DashboardModule.Models.ProductDetailModel;
import com.oceanmtech.shagun.DashboardModule.Models.VariantResponseModel;
import com.oceanmtech.shagun.DashboardModule.Utils.Constants;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIClient;
import com.oceanmtech.shagun.DashboardModule.Utils.GeneralAPIInterface;
import com.oceanmtech.shagun.DashboardModule.Utils.Internet;
import com.oceanmtech.shagun.DashboardModule.Utils.PreferenceHelper;
import com.oceanmtech.shagun.DashboardModule.Utils.ProgressHubKt;
import com.oceanmtech.shagun.R;
import com.oceanmtech.shagun.databinding.ActivityProductDetailBinding;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProductDetailActivity mContext = ProductDetailActivity.this;
    ActivityProductDetailBinding mBinding;
    String product_detail_url, product_detail_name;
    ProductDetailModel.Data mData = new ProductDetailModel.Data();
    int product_id, product_id_from_list, cart_id, total_qty, wishlist_id;
    ArrayList<CartModel.Data> cartData = new ArrayList<>();
    NameModel nameModel = new NameModel();
    String jsonVariant, jsonVariant2;

    String[] payment = {"Pay On Delivery", "Pay After 30 days (Price rate increase)", "Pay After 60 days (Price rate increase)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_product_detail);
        mBinding.toolbar.tvTitle.setText("Product Detail");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product_detail_url = extras.getString("Product_detail");
        }
        Log.d("Product_Detail_Url", product_detail_url);
        getProductDetail(product_detail_url);
        onClickListeners();
        getCartList(true);
        setPaymentSpinner();
        mBinding.spPayment.setOnItemSelectedListener(this);
    }

    private void getPriceVariant(int productId, String jsonVariant) {
        ProgressHubKt.showLoader(mContext);

        if (Internet.isInternetConnected(mContext)) {

            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).getVariant(productId, jsonVariant)
                    .enqueue(new Callback<VariantResponseModel>() {
                        @Override
                        public void onResponse(Call<VariantResponseModel> call, Response<VariantResponseModel> response) {
                            if (response.code() == 200) {
                                mBinding.tvPrize.setText(String.valueOf(response.body().price));
                            }
                            ProgressHubKt.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<VariantResponseModel> call, Throwable t) {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                            Log.d("ERROR_CHANGE", t.getMessage());
                            ProgressHubKt.dismissLoader();
                        }
                    });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setPaymentSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item2, payment);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spPayment.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int selectedPosition = parent.getSelectedItemPosition();
        if (selectedPosition == 0) {
            nameModel.name = "POD";
            Gson gson = new Gson();
            jsonVariant2 = gson.toJson(nameModel);
            jsonVariant = "[" + jsonVariant2 + "]";
            getPriceVariant(product_id, jsonVariant);
        }
        if (selectedPosition == 1) {
            nameModel.name = "P30";
            Gson gson = new Gson();
            jsonVariant2 = gson.toJson(nameModel);
            jsonVariant = "[" + jsonVariant2 + "]";
            getPriceVariant(product_id, jsonVariant);
        }
        if (selectedPosition == 2) {
            nameModel.name = "P60";
            Gson gson = new Gson();
            jsonVariant2 = gson.toJson(nameModel);
            jsonVariant = "[" + jsonVariant2 + "]";
            getPriceVariant(product_id, jsonVariant);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                getCartList(false);
            }
        }
    }

    private void getCartList(boolean isCall) {
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
                            cartData = response.body().data;
                            total_qty = response.body().data.get(0).quantity;
                            cart_id = response.body().data.get(0).id;

                            mBinding.tvQuantity.setText(String.valueOf(total_qty));
                            boolean isAdded = false;
                            for (int i = 0; i < cartData.size(); i++) {
                                product_id_from_list = cartData.get(i).product.id;
                                if (product_id_from_list == product_id) {
                                    isAdded = true;
                                    break;
                                }
                            }
                            if (isAdded) {
                                mBinding.llAddToCart.setVisibility(View.GONE);
                                mBinding.llAddRemove.setVisibility(View.VISIBLE);
                            } else {
                                mBinding.llAddToCart.setVisibility(View.VISIBLE);
                                mBinding.llAddRemove.setVisibility(View.GONE);
                            }
                            mBinding.tvQuantity.setText(String.valueOf(total_qty));
                        }
                    }
                    ProgressHubKt.dismissLoader();
                    if (isCall)
                        getProductDetail(product_detail_url);
                }

                @Override
                public void onFailure(Call<CartModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    ProgressHubKt.dismissLoader();
                    if (isCall) {
                        getProductDetail(product_detail_url);
                    }
                    Log.d("ERROR", t.getMessage());
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void checkProductInWishlist(int product_id) {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {

            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).checkProductInWishlist(param4(product_id))
                    .enqueue(new Callback<AddToWishlistModel>() {
                        @Override
                        public void onResponse(Call<AddToWishlistModel> call, Response<AddToWishlistModel> response) {
                            if (response.code() == 200) {

                                wishlist_id = response.body().wishlist_id;

                                if (response.body().is_in_wishlist == false) {
                                    mBinding.ivWishlistOutlined.setVisibility(View.VISIBLE);
                                    mBinding.ivWishlistFilled.setVisibility(View.GONE);
                                } else {
                                    mBinding.ivWishlistOutlined.setVisibility(View.GONE);
                                    mBinding.ivWishlistFilled.setVisibility(View.VISIBLE);
                                }
                                //Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_LONG).show();
                            }
                            ProgressHubKt.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<AddToWishlistModel> call, Throwable t) {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                            Log.d("ERROR", t.getMessage());
                            ProgressHubKt.dismissLoader();
                        }
                    });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public JsonObject param4(int product_id) {
        JsonObject paramObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_id", PreferenceHelper.getString(Constants.ID, ""));
            jsonObj_.put("product_id", product_id);
            Log.d("PRODUCTID", String.valueOf(product_id));

            JsonParser jsonParser = new JsonParser();
            paramObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramObject;
    }

    private void getProductDetail(String Url) {
        ProgressHubKt.showLoader(mContext);
        if (Internet.isInternetConnected(mContext)) {
            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                    productDetail(Url).enqueue(new Callback<ProductDetailModel>() {
                @Override
                public void onResponse(Call<ProductDetailModel> call, Response<ProductDetailModel> response) {
                    Log.d("Response :", response.body() + "");
                    if (response.code() == 200) {
                        if (response.body().data.size() > 0) {
                            mData = response.body().data.get(0);
                            product_id = response.body().data.get(0).id;
                            product_detail_name = response.body().data.get(0).name;

                            //getPriceVariant(product_id);

                            checkProductInWishlist(product_id);
                            boolean isAdded = false;
                            for (int i = 0; i < cartData.size(); i++) {
                                product_id_from_list = cartData.get(i).product.id;
                                if (product_id_from_list == product_id) {
                                    isAdded = true;
                                    break;
                                }
                            }
                            if (isAdded) {
                                mBinding.llAddToCart.setVisibility(View.GONE);
                                mBinding.llAddRemove.setVisibility(View.VISIBLE);
                            } else {
                                mBinding.llAddToCart.setVisibility(View.VISIBLE);
                                mBinding.llAddRemove.setVisibility(View.GONE);
                            }
                            setData(response.body().data.get(0));
                            //editQuantity(response.body().data.get(0));
                        }
                    }
                    ProgressHubKt.dismissLoader();
                }

                @Override
                public void onFailure(Call<ProductDetailModel> call, Throwable t) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    ProgressHubKt.dismissLoader();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setData(ProductDetailModel.Data data) {
        loadSlider(data.photos);
        mBinding.tvName.setText(data.name);
        mBinding.tvPrize.setText(String.valueOf(data.price_lower));
        if (data.choice_options.get(0).title.equalsIgnoreCase("Payment Cycle")) {
            mBinding.tvPayment.setVisibility(View.VISIBLE);
            mBinding.llPayment.setVisibility(View.VISIBLE);
        }

        mBinding.tvDescription.setText(HtmlCompat.fromHtml(data.description, HtmlCompat.FROM_HTML_MODE_COMPACT)); // To convert the html text from response into readable text.
    }

    private void loadSlider(ArrayList<String> sliderImages) {
        mBinding.slider1.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();
        for (int i = 0; i < sliderImages.size(); i++) {
            list.add(new CarouselItem("https://www.nihareeka.com/public/" + sliderImages.get(i)));
        }
        Log.d("SLIDER", list.toString());
        mBinding.slider1.setData(list);
    }

    private void onClickListeners() {

        mBinding.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressHubKt.showLoader(mContext);
                if (Internet.isInternetConnected(mContext)) {

                    GeneralAPIClient.getClient().create(GeneralAPIInterface.class).changeQty(param2(cart_id, total_qty + 1))
                            .enqueue(new Callback<ChangeQtyModel>() {
                                @Override
                                public void onResponse(Call<ChangeQtyModel> call, Response<ChangeQtyModel> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                                        if (response.body().message.equalsIgnoreCase("Cart updated")) {
                                            getCartList(false);
                                        }
                                    }
                                    ProgressHubKt.dismissLoader();
                                }

                                @Override
                                public void onFailure(Call<ChangeQtyModel> call, Throwable t) {
                                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                                    Log.d("ERROR_CHANGE", t.getMessage());
                                    ProgressHubKt.dismissLoader();
                                }
                            });
                } else {
                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        mBinding.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total_qty > 1) {
                    ProgressHubKt.showLoader(mContext);
                    if (Internet.isInternetConnected(mContext)) {
                        GeneralAPIClient.getClient().create(GeneralAPIInterface.class).changeQty(param2(cart_id, total_qty - 1))
                                .enqueue(new Callback<ChangeQtyModel>() {
                                    @Override
                                    public void onResponse(Call<ChangeQtyModel> call, Response<ChangeQtyModel> response) {
                                        if (response.code() == 200) {
                                            Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                                            if (response.body().message.equalsIgnoreCase("Cart updated")) {
                                                getCartList(false);
                                            }
                                        }
                                        ProgressHubKt.dismissLoader();
                                    }

                                    @Override
                                    public void onFailure(Call<ChangeQtyModel> call, Throwable t) {
                                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                                        Log.d("ERROR_CHANGE", t.getMessage());
                                        ProgressHubKt.dismissLoader();
                                    }
                                });
                    } else {
                        Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    mBinding.llAddRemove.setVisibility(View.GONE);
                    mBinding.llAddToCart.setVisibility(View.VISIBLE);

                    String CartUrl = "https://shagun-ent.eshopamb.com/api/v1/carts/" + cart_id;

                    if (Internet.isInternetConnected(mContext)) {

                        ProgressHubKt.showLoader(mContext);

                        GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                                removeFromCartlist(CartUrl).enqueue(new Callback<AddToCartModel>() {
                            @Override
                            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                                if (response.code() == 200) {

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
            }
        });

        mBinding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.toolbar.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, CartActivity.class);
                i.putExtra("VariantPrize", mBinding.tvPrize.getText().toString());
                startActivityForResult(i, 101);
            }
        });
        mBinding.ivWishlistOutlined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBinding.ivWishlistOutlined.setVisibility(View.GONE);
                mBinding.ivWishlistFilled.setVisibility(View.VISIBLE);

                ProgressHubKt.showLoader(mContext);

                if (Internet.isInternetConnected(mContext)) {

                    GeneralAPIClient.getClient().create(GeneralAPIInterface.class).addToWishlist(param(mData.id))
                            .enqueue(new Callback<AddToWishlistModel>() {
                                @Override
                                public void onResponse(Call<AddToWishlistModel> call, Response<AddToWishlistModel> response) {
                                    if (response.code() == 201) {
                                        Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                                        Log.d("ADD_TO_WISHLIST", response.body().message);
                                    }
                                    ProgressHubKt.dismissLoader();
                                }

                                @Override
                                public void onFailure(Call<AddToWishlistModel> call, Throwable t) {
                                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                                    ProgressHubKt.dismissLoader();
                                }
                            });
                } else {
                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        mBinding.ivWishlistFilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBinding.ivWishlistOutlined.setVisibility(View.VISIBLE);
                mBinding.ivWishlistFilled.setVisibility(View.GONE);

                String WishlistUrl = "https://shagun-ent.eshopamb.com/api/v1/wishlists/" + wishlist_id;

                if (Internet.isInternetConnected(mContext)) {

                    ProgressHubKt.showLoader(mContext);

                    GeneralAPIClient.getClient().create(GeneralAPIInterface.class).
                            removeFromWishlist(WishlistUrl).enqueue(new Callback<AddToWishlistModel>() {
                        @Override
                        public void onResponse(Call<AddToWishlistModel> call, Response<AddToWishlistModel> response) {
                            if (response.code() == 200) {
                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();
                            }
                            ProgressHubKt.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<AddToWishlistModel> call, Throwable t) {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                            Log.d("ERROR", t.getMessage());
                            ProgressHubKt.dismissLoader();
                        }
                    });
                } else {
                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        mBinding.tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBinding.llAddToCart.setVisibility(View.GONE);
                mBinding.llAddRemove.setVisibility(View.VISIBLE);

                AddToCart();
            }
        });
    }

    private void AddToCart() {
        ProgressHubKt.showLoader(mContext);

        if (Internet.isInternetConnected(mContext)) {

            GeneralAPIClient.getClient().create(GeneralAPIInterface.class).addToCart(param3(product_id))
                    .enqueue(new Callback<AddToCartModel>() {
                        @Override
                        public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                            if (response.code() == 200) {
                                Toast.makeText(mContext, response.body().message, Toast.LENGTH_SHORT).show();

                                getCartList(false);
                                Log.d("ADD_TO_CART", response.body().message);
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

    public JsonObject param2(int cart_id, int total_qty) {
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

    public JsonObject param3(int product_id) {
        JsonObject paramObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_id", PreferenceHelper.getString(Constants.ID, ""));
            jsonObj_.put("id", String.valueOf(product_id));
            jsonObj_.put("variant", nameModel.name);
            JsonParser jsonParser = new JsonParser();
            paramObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramObject;
    }

    public JsonObject param(int product_id) {
        JsonObject paramObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_id", PreferenceHelper.getString(Constants.ID, ""));
            jsonObj_.put("product_id", String.valueOf(product_id));
            JsonParser jsonParser = new JsonParser();
            paramObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}