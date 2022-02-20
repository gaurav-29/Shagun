package com.oceanmtech.shagun.DashboardModule.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeneralAPIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        final String BASE_URL = "https://shagun-ent.eshopamb.com/api/v1/";

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder;
            requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer " + PreferenceHelper.getString(Constants.ACCESS_TOKEN, ""))
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("Content-Type", "application/json");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        httpClient.addInterceptor(httpLoggingInterceptor);  // <-- this is the important line!
        httpClient.readTimeout(180, TimeUnit.SECONDS);
        httpClient.connectTimeout(180, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit;
    }
}
