package com.oceanmtech.shagun.DashboardModule.Utils;

import com.google.gson.JsonObject;
import com.oceanmtech.shagun.DashboardModule.Models.AddAddressModel;
import com.oceanmtech.shagun.DashboardModule.Models.AddToCartModel;
import com.oceanmtech.shagun.DashboardModule.Models.AddToWishlistModel;
import com.oceanmtech.shagun.DashboardModule.Models.AllCategoryModel;
import com.oceanmtech.shagun.DashboardModule.Models.CartModel;
import com.oceanmtech.shagun.DashboardModule.Models.ChangeQtyModel;
import com.oceanmtech.shagun.DashboardModule.Models.GetWishlistModel;
import com.oceanmtech.shagun.DashboardModule.Models.LoginModel;
import com.oceanmtech.shagun.DashboardModule.Models.LogoutModel;
import com.oceanmtech.shagun.DashboardModule.Models.ManageAddressModel;
import com.oceanmtech.shagun.DashboardModule.Models.OrderDetailModel;
import com.oceanmtech.shagun.DashboardModule.Models.OrderListModel;
import com.oceanmtech.shagun.DashboardModule.Models.PlaceOrderModel;
import com.oceanmtech.shagun.DashboardModule.Models.ProductDetailModel;
import com.oceanmtech.shagun.DashboardModule.Models.ProductListModel;
import com.oceanmtech.shagun.DashboardModule.Models.RegisterModel;
import com.oceanmtech.shagun.DashboardModule.Models.VariantResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GeneralAPIInterface {

    @POST("auth/signup")
    Call<RegisterModel> Register(@Body JsonObject jsonObject);

//    @FormUrlEncoded
//    @POST("v2/auth/generate_otp")
//    Call<RegisterModel> Otp(@FieldMap Map<String, String> Map);

    @POST("auth/login")
    Call<LoginModel> Login(@Body JsonObject jsonObject);

    @POST("wishlists")
    Call<AddToWishlistModel> addToWishlist(@Body JsonObject jsonObject);

    @POST("carts/add")
    Call<AddToCartModel> addToCart(@Body JsonObject jsonObject);

    @POST("carts/change-quantity")
    Call<ChangeQtyModel> changeQty(@Body JsonObject jsonObject);

    @GET
    Call<GetWishlistModel> getWishlist(@Url String Url);

    @GET
    Call<CartModel> getCartList(@Url String Url);

    @DELETE
    Call<AddToWishlistModel> removeFromWishlist(@Url String Url);

    @DELETE
    Call<AddToCartModel> removeFromCartlist(@Url String Url);

    @POST("wishlists/check-product")
    Call<AddToWishlistModel> checkProductInWishlist(@Body JsonObject jsonObject);

    @GET("auth/logout")
    Call<LogoutModel> Logout(@Header("Authorization") String token, @Query("email") String email, @Query("password") String password,
                             @Query("remember_me") boolean remember_me);

    @GET
    Call<AllCategoryModel> categoryList(@Url String Url);

    @GET
    Call<ProductListModel> productList(@Url String Url);

    @GET
    Call<ProductListModel> SearchProductList(@Url String Url);

    @GET
    Call<ProductDetailModel> productDetail(@Url String Url);

    @POST("user/shipping/create")
    Call<AddAddressModel> saveAddress(@Body JsonObject jsonObject);

    @GET
    Call<ManageAddressModel> getAddressList(@Url String Url);

    @GET
    Call<ManageAddressModel> deleteAddressFromList(@Url String Url);

    @GET
    Call<OrderListModel> getOrderListData(@Url String Url);

    @GET
    Call<OrderDetailModel> getOrderDetailData(@Url String Url);

    @FormUrlEncoded
    @POST("products/variant/price")
    Call<VariantResponseModel> getVariant(@Field("id") int id, @Field("choice") String variant);

    @POST("payments/pay/cod")
    Call<PlaceOrderModel> PlaceOrder(@Body JsonObject jsonObject);

    @POST("user/info/update")
    Call<PlaceOrderModel> updateProfileInfo(@Body JsonObject jsonObject);
}
